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
package io.github.kylinhunter.commons.jdbc.config.url.imp;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BiJi'an
 * @description JdbcUrlParserSqlServer j
 *     <p>jdbc:sqlserver://localhost:1433;DatabaseName=test;username=sa; password=passwd
 * @date 2023-01-10 11:11
 */
@C
public class JdbcUrlParserSqlServer implements JdbcUrlParser {

  private Pattern pattern = Pattern.compile(".+//(.+):(\\d+);(.*)");

  @Override
  public JdbcUrl parse(String jdbcUrl) {
    JdbcUrl jdbcUrlInfo = null;
    Matcher matcher = pattern.matcher(jdbcUrl);

    if (matcher.find()) {
      jdbcUrlInfo = new JdbcUrl();
      jdbcUrlInfo.setDbType(DbType.SQL_SERVER);
      jdbcUrlInfo.setHost(matcher.group(1));
      jdbcUrlInfo.setPort(Integer.parseInt(matcher.group(2)));
      String group3 = matcher.group(3);
      Map<String, String> params = StringUtil.split(group3, ';', '=');
      jdbcUrlInfo.setParams(params);
      for (Entry<String, String> env : params.entrySet()) {
        if ("DatabaseName".equalsIgnoreCase(env.getKey())) {
          jdbcUrlInfo.setDatabase(env.getValue());
        }
      }
    }

    if (jdbcUrlInfo == null) {
      throw new JdbcException("invalid jdbcUrl" + jdbcUrl);
    }
    return jdbcUrlInfo;
  }
}
