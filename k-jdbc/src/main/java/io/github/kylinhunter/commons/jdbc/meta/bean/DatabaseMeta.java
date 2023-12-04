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
package io.github.kylinhunter.commons.jdbc.meta.bean;

import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.utils.JdbcUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Data
public class DatabaseMeta {

  private String url;
  private String productName;
  private String version;
  private String driverName;

  /*
   * the extra information of the database
   */
  private JdbcUrl jdbcUrl;
  private DbType dbType;

  public void setUrl(String url) {
    this.url = url;
    this.jdbcUrl = JdbcUtils.parse(url);
    this.dbType = this.jdbcUrl.getDbType();
  }
}
