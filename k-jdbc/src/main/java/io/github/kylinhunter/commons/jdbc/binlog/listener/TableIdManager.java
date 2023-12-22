/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.jdbc.binlog.listener;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-16 00:06
 */
@Slf4j
public class TableIdManager {

  protected final Map<Long, TableMeta> allTableMetas = MapUtils.newHashMap();

  protected final Map<Long, ColumnMetas> allColumnMetas = MapUtils.newHashMap();

  protected final Map<String, Long> tableIdMaps = MapUtils.newHashMap();

  protected DatabaseMetaReader databaseMetaReader;
  private final TableReader tableReader;
  private final ColumnReader columnReader;

  protected DataSource dataSource;

  public TableIdManager(DataSource dataSource) {
    this.dataSource = dataSource;
    this.databaseMetaReader = new DatabaseMetaReader(dataSource);
    this.tableReader = this.databaseMetaReader.getTableReader();
    this.columnReader = this.databaseMetaReader.getColumnReader();
  }

  /**
   * @param tableId   tableId
   * @param tableName tableName
   * @title addTable
   * @description addTable
   * @author BiJi'an
   * @date 2023-12-09 23:27
   */
  public void update(Long tableId, String database, String tableName) {

    TableMeta oldTableMeta = allTableMetas.get(tableId);
    if (oldTableMeta == null || !oldTableMeta.equals(database, tableName)) {
      TableMeta tableMeta = tableReader.getTableMetaData(this.dataSource, database, tableName);
      if (tableMeta != null) {
        allTableMetas.put(tableId, tableMeta);
        log.info("############# updateTableMeta={}", tableMeta);
      }

      ColumnMetas columnMetas =
          columnReader.getColumnMetaData(this.dataSource, database, tableName);
      if (columnMetas != null) {
        this.allColumnMetas.put(tableId, columnMetas);
        log.info("############# updateColumnMeta={}", columnMetas);
      }

      tableIdMaps.put(database + ":" + tableName, tableId);
    }
  }

  /**
   * @param database  database
   * @param tableName tableName
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-17 14:38
   */
  public void clean(String database, String tableName) {

    Long tableId = this.tableIdMaps.get(database + ":" + tableName);
    if (tableId != null) {
      this.update(tableId, database, tableName);
    }
  }

  /**
   * @param tableId tableId
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta
   * @title getTableMeta
   * @description getTableMeta
   * @author BiJi'an
   * @date 2023-12-16 21:10
   */
  public TableMeta getTableMeta(long tableId) {
    return this.allTableMetas.get(tableId);
  }

  /**
   * @param tableId tableId
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas
   * @title getColumnMetas
   * @description getColumnMetas
   * @author BiJi'an
   * @date 2023-12-16 00:57
   */
  public ColumnMetas getColumnMetas(long tableId) {
    return this.allColumnMetas.get(tableId);
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta
   * @title getPkColumnMeta
   * @description getPkColumnMeta
   * @author BiJi'an
   * @date 2023-12-16 14:24
   */
  public ColumnMeta getPkColumnMeta(
      long tableId, String database, String tableName, String tablePkName) {

    TableMeta tableMeta = this.getTableMeta(tableId);
    if (tableMeta == null) {
      return null;
    }
    if (!tableMeta.equals(database, tableName)) {
      return null;
    }

    ColumnMetas columnMetas = this.getColumnMetas(tableId);
    if (columnMetas != null) {
      return columnMetas.getByName(tablePkName);
    }
    return null;
  }
}
