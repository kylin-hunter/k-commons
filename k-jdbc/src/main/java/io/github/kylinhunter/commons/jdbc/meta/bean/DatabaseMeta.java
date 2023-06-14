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

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
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
  private DbType dbType;

  public void setUrl(String url) {
    this.url = url;
    this.dbType = calDbType(url);
  }

  public DbType calDbType(String jdbcUrl) {

    ExceptionChecker.checkNotEmpty(jdbcUrl, "jdbcUrl can't be  empty");
    String lowerCaseJdbcUrl = jdbcUrl.toLowerCase();
    if (lowerCaseJdbcUrl.contains("mysql")) {
      return DbType.MYSQL;
    } else if (lowerCaseJdbcUrl.contains("oracle")) {
      return DbType.ORACLE;
    } else if (lowerCaseJdbcUrl.contains("sqlserver")) {
      return DbType.SQL_SERVER;
    } else {
      return DbType.OTHERS;
    }
  }
}
