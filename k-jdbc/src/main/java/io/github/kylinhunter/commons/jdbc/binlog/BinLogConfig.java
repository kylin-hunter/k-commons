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
package io.github.kylinhunter.commons.jdbc.binlog;

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 21:22
 */
@Data
public class BinLogConfig {

  long serverId;
  String hostname;
  int port;
  String schema;
  String username;
  String password;
  private String binlogFilename;
  private long binlogPosition;

  private String url;
  private JdbcUrl jdbcUrl;

  private SavePointManager savePointManager;

  /**
   * @param url url
   * @title setUrl
   * @description setUrl
   * @author BiJi'an
   * @date 2023-12-16 17:29
   */
  public void setUrl(String url) {
    this.url = url;
    this.jdbcUrl = JdbcUtils.parse(url);
    this.hostname = jdbcUrl.getHost();
    this.port = jdbcUrl.getPort();
  }

  /**
   * @param jdbcUrl jdbcUrl
   * @title setJdbcUrl
   * @description setJdbcUrl
   * @author BiJi'an
   * @date 2023-12-16 17:29
   */
  public void setJdbcUrl(JdbcUrl jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
    this.url = JdbcUtils.toString(jdbcUrl);
    this.hostname = jdbcUrl.getHost();
    this.port = jdbcUrl.getPort();
  }
}
