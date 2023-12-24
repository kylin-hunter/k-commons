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
package io.github.kylinhunter.commons.jdbc.execute;

import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-29 00:30
 */
@RequiredArgsConstructor
public class SqlExecutor {

  @Getter private QueryRunner queryRunner;
  private DataSource dataSource;

  public SqlExecutor(DataSource dataSource) {
    this.dataSource = dataSource;
    this.queryRunner = new QueryRunner(dataSource);
  }

  /**
   * @param sql sql
   * @param params params
   * @return int
   * @title execute
   * @description execute
   * @author BiJi'an
   * @date 2023-11-29 00:48
   */
  public int execute(String sql, Object... params) {
    try {
      return queryRunner.execute(sql, params);
    } catch (SQLException e) {
      throw new JdbcException("execute sql error", e);
    }
  }

  /**
   * @param sql sql
   * @param rsh rsh
   * @param params params
   * @return T
   * @title query
   * @description query
   * @author BiJi'an
   * @date 2023-11-30 00:12
   */
  public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) {
    try {
      return queryRunner.query(sql, rsh, params);
    } catch (SQLException e) {
      throw new JdbcException("execute sql error", e);
    }
  }

  /**
   * @return java.sql.Connection
   * @title getConnection
   * @description getConnection
   * @author BiJi'an
   * @date 2023-12-03 00:08
   */
  public Connection getConnection() {
    try {
      return this.dataSource.getConnection();
    } catch (SQLException e) {
      throw new JdbcException("getConnection  error", e);
    }
  }

  /**
   * @param sqlLines sqlLines
   * @title execute with QueryRunner
   * @description execute
   * @author BiJi'an
   * @date 2023-11-29 00:45
   */
  public void executeBatch(boolean transaction, List<String> sqlLines) {
    try {
      if (transaction) {

        try (Connection connection = this.getConnection()) {
          connection.setAutoCommit(false);
          for (String sqlLine : sqlLines) {
            queryRunner.execute(connection, sqlLine);
          }
          connection.commit();
        }

      } else {
        for (String sqlLine : sqlLines) {
          queryRunner.execute(sqlLine);
        }
      }
    } catch (Exception e) {
      throw new JdbcException("execute sql error", e);
    }
  }
}
