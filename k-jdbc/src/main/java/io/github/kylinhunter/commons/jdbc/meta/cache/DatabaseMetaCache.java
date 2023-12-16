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
package io.github.kylinhunter.commons.jdbc.meta.cache;

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
public class DatabaseMetaCache {

  protected final Map<Long, TableMeta> tableMetas = MapUtils.newHashMap();
  protected final Map<Long, ColumnMetas> tableColumnMetas = MapUtils.newHashMap();
  protected DatabaseMetaReader databaseMetaReader;
  private final TableReader tableReader;
  private final ColumnReader columnReader;

  protected DataSource dataSource;

  public DatabaseMetaCache(DataSource dataSource) {
    this.dataSource = dataSource;
    this.databaseMetaReader = new DatabaseMetaReader(dataSource);
    this.tableReader = this.databaseMetaReader.getTableReader();
    this.columnReader = this.databaseMetaReader.getColumnReader();
  }

  /**
   * @param tableId tableId
   * @param tableName tableName
   * @param forceUpdate forceUpdate
   * @title addTable
   * @description addTable
   * @author BiJi'an
   * @date 2023-12-09 23:27
   */
  public void updateTableCache(
      Long tableId, String database, String tableName, boolean forceUpdate) {

    TableMeta tableMeta = tableMetas.get(tableId);
    if (tableMeta == null || forceUpdate) {
      tableMeta = tableReader.getTableMetaData(this.dataSource, database, tableName);
      if (tableMeta != null) {
        tableMetas.put(tableId, tableMeta);
        log.info("############# updateTableMeta={}", tableMeta);
      }
    }

    ColumnMetas columnMetas = this.tableColumnMetas.get(tableId);
    if (columnMetas == null || forceUpdate) {
      columnMetas = columnReader.getColumnMetaData(this.dataSource, database, tableName);
      if (columnMetas != null) {
        this.tableColumnMetas.put(tableId, columnMetas);
        log.info("############# updateColumnMeta={}", columnMetas);
      }
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
    return this.tableMetas.get(tableId);
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
    return this.tableColumnMetas.get(tableId);
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
