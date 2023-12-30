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
package io.github.kylinhunter.commons.jdbc.meta.table;

import io.github.kylinhunter.commons.jdbc.dao.DatabaseVisitor;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;

public interface TableReader extends DatabaseVisitor {

  /**
   * @param tableName tableName
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta
   * @title getTableMetaData
   * @description getTableMetaData
   * @author BiJi'an
   * @date 2023-12-17 23:59
   */
  TableMeta getTableMetaData(String tableName);

  /**
   * @param catalog catalog
   * @param tableName tableName
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta
   * @title getTableMetaData
   * @description
   * @author BiJi'an
   * @date 2023-02-19 01:18
   */
  TableMeta getTableMetaData(String catalog, String tableName);

  /**
   * @param dataSource dataSource
   * @param catalog catalog
   * @param tableName tableName
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta
   * @title getTableMetaData
   * @description getTableMetaData
   * @author BiJi'an
   * @date 2023-12-10 00:53
   */
  TableMeta getTableMetaData(DataSource dataSource, String catalog, String tableName);

  /**
   * @param catalog catalog
   * @param tableName tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  List<TableMeta> getTableMetaDatas(String catalog, String tableName);

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
  List<TableMeta> getTableMetaDatas(DataSource dataSource, String catalog, String tableName);

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
  List<TableMeta> getTableMetaDatas(
      Connection connection, String catalog, String schema, String tableName);

  /**
   * @param catalog catalog
   * @param tableName tableName
   * @return boolean
   * @title exist
   * @description exist
   * @author BiJi'an
   * @date 2023-12-10 23:34
   */
  boolean exist(String catalog, String tableName);

  /**
   * @param dataSource dataSource
   * @param catalog catalog
   * @param tableName tableName
   * @return boolean
   * @title exist
   * @description exist
   * @author BiJi'an
   * @date 2023-12-10 23:34
   */
  boolean exist(DataSource dataSource, String catalog, String tableName);
}
