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
package io.github.kylinhunter.commons.jdbc.utils;

import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.config.url.imp.MysqlJdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.config.url.imp.OracleJdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.config.url.imp.SqlServerJdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import java.util.Objects;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
public class JdbcUtils {

  private static JdbcUrlParser mysqlJdbcUrlParser = new MysqlJdbcUrlParser();
  private static OracleJdbcUrlParser oracleJdbcUrlParser = new OracleJdbcUrlParser();
  private static SqlServerJdbcUrlParser sqlServerJdbcUrlParser = new SqlServerJdbcUrlParser();

  /**
   * @param jdbcUrl jdbcUrl
   * @return io.github.kylinhunter.commons.jdbc.config.JdbcUrlInfo
   * @throws
   * @title parse
   * @description parse
   * @author BiJi'an
   * @date 2023-11-25 22:19
   */
  public static JdbcUrl parse(String jdbcUrl) {
    DbType dbType = calDbType(jdbcUrl);
    switch (dbType) {
      case MYSQL:
        return mysqlJdbcUrlParser.parse(jdbcUrl);
      case ORACLE:
        return oracleJdbcUrlParser.parse(jdbcUrl);
      case SQL_SERVER:
        return sqlServerJdbcUrlParser.parse(jdbcUrl);
    }
    throw new JdbcException("UnSupported dbType" + dbType);
  }

  /**
   * @param jdbcUrl jdbcUrl
   * @return io.github.kylinhunter.commons.jdbc.constant.DbType
   * @title calDbType
   * @description calDbType
   * @author BiJi'an
   * @date 2023-11-26 11:50
   */
  public static DbType calDbType(String jdbcUrl) {
    Objects.requireNonNull(jdbcUrl);
    String lowerCaseJdbcUrl = jdbcUrl.toLowerCase();
    if (lowerCaseJdbcUrl.contains("mysql")) {
      return DbType.MYSQL;
    } else if (lowerCaseJdbcUrl.contains("oracle")) {
      return DbType.ORACLE;
    } else if (lowerCaseJdbcUrl.contains("sqlserver")) {
      return DbType.SQL_SERVER;
    } else {
      throw new JdbcException("no suppert jdbc url:" + jdbcUrl);
    }
  }
}
