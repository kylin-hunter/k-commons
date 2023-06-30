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

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 */
@Getter
@Setter
public class Imports {
  private final List<String> imports = ListUtils.newArrayList();

  @Override
  public String toString() {

    return toString("import ", ";", System.lineSeparator());
  }

  /**
   * @param prefix prefix
   * @param postfix postfix
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:32
   */
  public String toString(String prefix, String postfix) {
    return toString(prefix, postfix, System.lineSeparator());
  }

  /**
   * @param prefix prefix
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:20
   */
  public String toString(String prefix, String postfix, String separator) {
    return imports.stream().map(e -> prefix + e + postfix).collect(Collectors.joining(separator));
  }

  /**
   * @param fullClassName className
   * @return void
   * @title add
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:24
   */
  public void add(String fullClassName) {
    if (!StringUtil.isEmpty(fullClassName)) {
      if (!fullClassName.startsWith("java.lang")) {
        if (!imports.contains(fullClassName)) {
          this.imports.add(fullClassName);
        }
      }
    }
  }

  public void add(Class<?> clazz) {
    if (clazz != null) {
      this.add(clazz.getName());
    }
  }
}
