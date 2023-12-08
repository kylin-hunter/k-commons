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

import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-29 00:34
 */
public class AbstractDatabaseManager implements DatabaseManager {

  protected DbType dbType;
  private DataSource dataSource;

  protected SqlExecutor sqlExecutor;

  protected static final DataSourceManager dataSourceManager = new DataSourceManager();

  public AbstractDatabaseManager() {
    this(null);
  }

  public AbstractDatabaseManager(DataSource dataSource) {
    if (dataSource != null) {
      this.dataSource = dataSource;
      this.sqlExecutor = new SqlExecutor(dataSource);
    }
  }

  /***
   * @title getDataSource
   * @description getDataSource
   * @author BiJi'an
   * @date 2023-12-03 15:45
   * @return javax.sql.DataSource
   */
  public DataSource getDataSource() {
    if (dataSource != null) {
      return dataSource;
    }
    return getDefaultDataSource();
  }

  /**
   * @return javax.sql.DataSource
   * @title getDefaultDataSource
   * @description getDefaultDataSource
   * @author BiJi'an
   * @date 2023-12-09 20:08
   */
  public DataSource getDefaultDataSource() {
    return dataSourceManager.getDefaultDataSource();

  }


  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getDefaultDataSource
   * @description getSqlExecutor
   * @author BiJi'an
   * @date 2023-12-03 15:45
   */
  public SqlExecutor getSqlExecutor() {
    if (sqlExecutor != null) {
      return sqlExecutor;
    }
    return getDefaultSqlExecutor();
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getDefaultSqlExecutor
   * @description getDefaultSqlExecutor
   * @author BiJi'an
   * @date 2023-12-09 20:09
   */
  public SqlExecutor getDefaultSqlExecutor() {
    return dataSourceManager.getDefaultSqlExecutor();

  }
}
