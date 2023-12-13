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

import io.github.kylinhunter.commons.exception.check.ThrowChecker;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.datasource.DataSourceManager;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-29 00:34
 */
public class AbstractDatabaseVisitor implements DatabaseVisitor {

  @Getter
  protected DbType dbType;
  @Getter
  protected DataSource dataSource;
  @Getter
  protected SqlExecutor sqlExecutor;

  @Setter
  protected DataSourceManager dataSourceManager = new DataSourceManager();

  public AbstractDatabaseVisitor(DataSource dataSource, boolean dbConfigEnabled) {
    if (dataSource != null) {
      this.dataSource = dataSource;
      this.sqlExecutor = new SqlExecutor(dataSource);
    } else {
      if (dbConfigEnabled) {
        dataSourceManager.init();
        this.dataSource = dataSourceManager.get();
        this.sqlExecutor = dataSourceManager.getSqlExecutor();

      }
    }

    if (this.dataSource == null) {
      throw new JdbcException("datasource can't be null");
    }
    DatabaseMeta metaData = this.getDBMetaData();
    this.dbType = metaData.getDbType();
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:41
   */
  public DatabaseMeta getDBMetaData() {
    return this.getDBMetaData(this.getDataSource());
  }

  /**
   * @param dataSource dataSource
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:41
   */
  public DatabaseMeta getDBMetaData(DataSource dataSource) {
    ThrowChecker.checkNotNull(dataSource, "datasource can't be null");

    try (Connection connection = dataSource.getConnection()) {
      return getDBMetaData(connection);
    } catch (Exception e) {
      throw new JdbcException("getDatabaseMetaData error", e);
    }
  }

  /**
   * @param connection connection
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:41
   */
  public DatabaseMeta getDBMetaData(Connection connection) {
    try {

      DatabaseMetaData metaData = connection.getMetaData();
      DatabaseMeta databaseMeta = new DatabaseMeta();
      databaseMeta.setUrl(metaData.getURL());
      databaseMeta.setProductName(metaData.getDatabaseProductName());
      databaseMeta.setVersion(metaData.getDatabaseProductVersion());
      databaseMeta.setDriverName(metaData.getDriverName());

      return databaseMeta;
    } catch (Exception e) {
      throw new JdbcException("getDatabaseMetaData error", e);
    }
  }

  /**
   * @title close
   * @description close
   * @author BiJi'an
   * @date 2023-12-14 00:28
   */
  public void close() {

    this.dataSourceManager.close();
  }
}
