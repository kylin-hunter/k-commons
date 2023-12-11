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

import io.github.kylinhunter.commons.jdbc.dao.DatabaseVisitor;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import java.sql.Connection;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
public interface ColumnReader extends DatabaseVisitor {

  /**
   * @param catalog catalog
   * @param tableName tableName
   * @return java.util.List<io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta>
   * @title getColumnMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:42
   */
  ColumnMetas getColumnMetaData(String catalog, String tableName);

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
  ColumnMetas getColumnMetaData(DataSource dataSource, String catalog, String tableName);

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
  ColumnMetas getColumnMetaData(
      Connection connection, String catalog, String schema, String tableName);
}
