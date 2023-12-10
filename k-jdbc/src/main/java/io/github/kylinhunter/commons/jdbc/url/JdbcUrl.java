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
package io.github.kylinhunter.commons.jdbc.url;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-18 00:08
 */
@Data
@NoArgsConstructor
public class JdbcUrl implements Serializable {

  private DbType dbType;
  private String host;
  private int port;
  private String database;
  private Map<String, String> params = MapUtils.newLinkedHashMap();


  public JdbcUrl(String host, int port, String database) {
    this.host = host;
    this.port = port;
    this.database = database;
  }

  /**
   * @param params params
   * @title setParams
   * @description setParams
   * @author BiJi'an
   * @date 2023-11-25 20:48
   */
  public void setParams(Map<String, String> params) {
    if (params != null && params.size() > 0) {
      params.forEach(
          (k, v) -> {
            if (k != null && v != null) {
              this.params.put(k.trim(), v.trim());
            }
          });
    }
  }

  /**
   * @param host host
   * @title setHost
   * @description setHost
   * @author BiJi'an
   * @date 2023-11-25 20:49
   */
  public void setHost(String host) {
    if (host != null) {
      this.host = host.trim();
    }
  }

  /**
   * @param database database
   * @title setDatabase
   * @description setDatabase
   * @author BiJi'an
   * @date 2023-11-25 20:49
   */
  public void setDatabase(String database) {
    if (database != null) {
      this.database = database.trim();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JdbcUrl jdbcUrl = (JdbcUrl) o;
    return port == jdbcUrl.port && host.equals(jdbcUrl.host);
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, port);
  }

}
