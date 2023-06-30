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
package io.github.kylinhunter.commons.generator.context.bean.clazz;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 */
@Getter
@Setter
public class Snippets {
  private Map<String, List<String>> snippets = MapUtils.newHashMap();

  /**
   * @param location location
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:42
   */
  public String toString(String location) {
    return toString(location, System.lineSeparator());
  }

  /**
   * @param separator separator
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:20
   */
  public String toString(String location, String separator) {
    List<String> tmpSnippets = this.snippets.get(location);
    if (!CollectionUtils.isEmpty(tmpSnippets)) {
      return String.join(separator, tmpSnippets);
    }
    return StringUtil.EMPTY;
  }

  /**
   * @param snippet snippet
   * @return void
   * @title add
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:24
   */
  public void add(String name, String snippet) {

    if (!StringUtil.isBlank(name) && !StringUtil.isBlank(snippet)) {
      this.snippets.compute(
          name,
          (k, v) -> {
            if (v == null) {
              v = ListUtils.newArrayList();
            }
            v.add(snippet);
            return v;
          });
    }
  }
}
