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

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.config.url.imp.JdbcUrlParserMysql;
import io.github.kylinhunter.commons.jdbc.config.url.imp.JdbcUrlParserOracle;
import io.github.kylinhunter.commons.jdbc.config.url.imp.JdbcUrlParserSqlServer;
import java.util.Objects;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
public class JdbcUtils {

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
    Objects.requireNonNull(jdbcUrl);
    String lowerCaseJdbcUrl = jdbcUrl.toLowerCase();
    if (lowerCaseJdbcUrl.contains("mysql")) {
      return CF.get(JdbcUrlParserMysql.class).parse(jdbcUrl);
    } else if (lowerCaseJdbcUrl.contains("oracle")) {
      return CF.get(JdbcUrlParserOracle.class).parse(jdbcUrl);
    } else if (lowerCaseJdbcUrl.contains("sqlserver")) {
      return CF.get(JdbcUrlParserSqlServer.class).parse(jdbcUrl);
    } else {
      throw new ParamException("unkown dbtype");
    }
  }
}
