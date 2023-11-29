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
package io.github.kylinhunter.commons.jdbc.meta.column;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.meta.AbstractDatabaseManager;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Slf4j
@NoArgsConstructor
public abstract class AbstractColumnReader extends AbstractDatabaseManager implements ColumnReader {

  protected DbType dbType;

  protected ColumnParser columnParser;

  public AbstractColumnReader(DataSource dataSource) {
    super(dataSource);
  }

  /**
   * @param catalog   catalog
   * @param tableName tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  public List<ColumnMeta> getColumnMetaData(String catalog, String tableName) {
    return getColumnMetaData(this.defaultDataSource, catalog, tableName);
  }

  /**
   * @param dataSource dataSource
   * @param catalog    catalog
   * @param tableName  tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  public List<ColumnMeta> getColumnMetaData(
      DataSource dataSource, String catalog, String tableName) {
    try (Connection connection = dataSource.getConnection()) {
      catalog = catalog != null && catalog.length() > 0 ? catalog : null;
      return getColumnMetaData(connection, catalog, null, tableName);

    } catch (JdbcException e) {
      throw e;
    } catch (Exception e) {
      throw new JdbcException("getColumnMetaData error", e);
    }
  }

  /**
   * @param connection connection
   * @param catalog    catalog
   * @param schema     schema
   * @param tableName  tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  public List<ColumnMeta> getColumnMetaData(
      Connection connection, String catalog, String schema, String tableName) {
    List<ColumnMeta> columnMetaDatas;
    try {
      columnMetaDatas = ListUtils.newArrayList();
      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet columns = metaData.getColumns(catalog, schema, tableName, null);
      ResultSetMetaData columnMetadata = columns.getMetaData();
      Map<String, Object> rawMetadata = MapUtils.newHashMap();
      while (columns.next()) {
        ColumnMeta columnMeta = new ColumnMeta();
        for (int i = 0; i < columnMetadata.getColumnCount(); i++) {
          String col_name = columnMetadata.getColumnName(i + 1);
          Object value = columns.getObject(col_name);
          rawMetadata.put(col_name, value);
          processMetadata(columnMeta, col_name, value);
        }
        columnMeta.setRawMetadatas(rawMetadata);
        Class<?> javaClass = columnParser.calJavaClass(columnMeta.getDataType());
        columnMeta.setJavaClass(javaClass);
        columnMetaDatas.add(columnMeta);
      }
    } catch (Exception e) {
      throw new JdbcException("getColumnMetaData error", e);
    }

    return columnMetaDatas;
  }

  protected abstract void processMetadata(ColumnMeta columnMeta, String colName, Object value);
}
