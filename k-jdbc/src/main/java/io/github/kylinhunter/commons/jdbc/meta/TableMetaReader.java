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
package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceUtils;
import io.github.kylinhunter.commons.jdbc.datasource.ExDataSource;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.util.ObjectValues;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Slf4j
@C
@RequiredArgsConstructor
public class TableMetaReader {

  /**
   * @param catalog catalog
   * @param tableName tableName
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta
   * @title getTableMetaData
   * @description
   * @author BiJi'an
   * @date 2023-02-19 01:18
   */
  public TableMeta getTableMetaData(String catalog, String tableName) {
    List<TableMeta> tableMetaDatas =
        getTableMetaDatas(DataSourceUtils.getDefaultDataSource(), catalog, tableName);
    if (tableMetaDatas != null && tableMetaDatas.size() > 0) {
      return tableMetaDatas.get(0);
    }
    return null;
  }

  /**
   * @param catalog catalog
   * @param tableName tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  public List<TableMeta> getTableMetaDatas(String catalog, String tableName) {
    return getTableMetaDatas(DataSourceUtils.getDefaultDataSource(), catalog, tableName);
  }

  /**
   * @param dataSource dataSource
   * @param catalog catalog
   * @param tableName tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  public List<TableMeta> getTableMetaDatas(
      ExDataSource dataSource, String catalog, String tableName) {
    try (Connection connection = dataSource.getConnection()) {
      catalog = catalog != null && catalog.length() > 0 ? catalog : null;
      return getTableMetaDatas(connection, catalog, null, tableName);

    } catch (JdbcException e) {
      throw e;
    } catch (Exception e) {
      throw new JdbcException("getColumnMetaData error", e);
    }
  }

  /**
   * @param connection connection
   * @param catalog catalog
   * @param schema schema
   * @param tableName tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  public List<TableMeta> getTableMetaDatas(
      Connection connection, String catalog, String schema, String tableName) {
    List<TableMeta> columnMetaDatas;
    try {
      columnMetaDatas = ListUtils.newArrayList();
      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet tables = metaData.getTables(catalog, schema, tableName, new String[] {"TABLE"});
      ResultSetMetaData tableMetadata = tables.getMetaData();
      Map<String, Object> rawMetadata = MapUtils.newHashMap();
      while (tables.next()) {

        TableMeta tableMeta = new TableMeta();
        for (int i = 0; i < tableMetadata.getColumnCount(); i++) {
          String col_name = tableMetadata.getColumnName(i + 1);
          Object value = tables.getObject(col_name);
          rawMetadata.put(col_name, value);
          processMetadata(tableMeta, col_name, value);
        }
        tableMeta.setRawMetadatas(rawMetadata);
        columnMetaDatas.add(tableMeta);
      }
    } catch (Exception e) {
      throw new JdbcException("getColumnMetaData error", e);
    }

    return columnMetaDatas;
  }

  /**
   * @param tableMeta tableMeta
   * @param columName columName
   * @param value value
   * @return void
   * @title processMetadata
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:43
   */
  private void processMetadata(TableMeta tableMeta, String columName, Object value) {
    //        log.info(columName + ":" + value);
    switch (columName) {
      case "TABLE_NAME":
        tableMeta.setName(ObjectValues.getString(value));
        break;
      case "REMARKS":
        tableMeta.setRemarks(ObjectValues.getString(value));
        break;
      default:
    }
  }
}
