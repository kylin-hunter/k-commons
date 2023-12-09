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

import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrlParser;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BiJi'an
 * @description parse
 * <p>jdbc:oracle:thin:@localhost:1521:ORACLE?user=your_username&password=your_password
 * @date 2023-01-10 11:11
 */
public class OracleJdbcUrlParser implements JdbcUrlParser {

  private final Pattern pattern = Pattern.compile(".+@(.+):(\\d+):([\\d\\w]+)([\\?]{0,1})(.*)");

  /**
   *
   */
  @Override
  public JdbcUrl parse(String url) {
    JdbcUrl jdbcUrl = null;
    Matcher matcher = pattern.matcher(url);

    if (matcher.find()) {

      jdbcUrl = new JdbcUrl();
      jdbcUrl.setDbType(DbType.ORACLE);
      jdbcUrl.setHost(matcher.group(1));
      jdbcUrl.setPort(Integer.parseInt(matcher.group(2)));
      jdbcUrl.setDatabase(matcher.group(3));
      String group5 = matcher.group(5);
      Map<String, String> params = StringUtil.split(group5, '&', '=');
      jdbcUrl.setParams(params);
    }

    if (jdbcUrl == null) {
      throw new JdbcException("invalid url" + url);
    }
    return jdbcUrl;
  }

  @Override
  public String toString(JdbcUrl jdbcUrl) {
    StringBuilder builder = new StringBuilder();
    builder.append("jdbc:oracle:thin:@");
    builder.append(jdbcUrl.getHost()).append(":");
    builder.append(jdbcUrl.getPort()).append(":");
    builder.append(jdbcUrl.getDatabase());

    Map<String, String> params = jdbcUrl.getParams();
    if (params.size() > 0) {
      builder.append("?");
      params.forEach((k, v) -> {
        builder.append(k).append("=").append(v).append("&");
      });
      builder.setLength(builder.length() - 1);
    }

    return builder.toString();
  }
}
