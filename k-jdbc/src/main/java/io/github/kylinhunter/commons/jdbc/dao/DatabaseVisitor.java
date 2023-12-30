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
package io.github.kylinhunter.commons.jdbc.dao;

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 23:36
 */
public interface DatabaseVisitor {

  /**
   * @return javax.sql.DataSource
   * @title getDataSource
   * @description getDataSource
   * @author BiJi'an
   * @date 2023-12-10 23:37
   */
  DataSource getDataSource();

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getSqlExecutor
   * @description getSqlExecutor
   * @author BiJi'an
   * @date 2023-12-10 23:37
   */
  SqlExecutor getSqlExecutor();

  /**
   * @return io.github.kylinhunter.commons.jdbc.constant.DbType
   * @title getDbType
   * @description getDbType
   * @author BiJi'an
   * @date 2023-12-16 20:17
   */
  DbType getDbType();
}
