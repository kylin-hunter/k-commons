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

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Data
@ToString(exclude = "rawMetadatas")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TableMeta {

  @Include private String database; // column name

  @Include String name; // c1olumn name
  /*
   * the raw metadata of table
   */
  private Map<String, Object> rawMetadatas = MapUtils.newHashMap();

  private String remarks; // column remarks

  public Map<String, Object> getRawMetadatas() {
    return rawMetadatas != null ? rawMetadatas : MapUtils.newHashMap();
  }

  /**
   * @param database database
   * @param name name
   * @title equals
   * @description equals
   * @author BiJi'an
   * @date 2023-12-15 22:40
   */
  public boolean equals(String database, String name) {
    return this.database != null
        && this.name != null
        && this.database.equals(database)
        && this.name.equals(name);
  }
}
