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
package io.github.kylinhunter.commons.generator.config.bean;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Table {
  private String name;
  private List<String> skipColumns = ListUtils.newArrayList();
  private Map<String, String> columnTypes = MapUtils.newHashMap();

  /**
   * @param column column
   * @return java.lang.String
   * @title getColumnType
   * @description
   * @author BiJi'an
   * @date 2023-02-26 19:16
   */
  public String getColumnType(String column) {

    return columnTypes.get(column);
  }
}
