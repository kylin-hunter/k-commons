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

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 19:52
 */
@Getter
@Setter
public class FieldInfo {
  private String name;
  private String columnName;
  private String type;
  private String fullType;
  private String comment;
  private Snippets snippets = new Snippets();

  /**
   * @param location location
   * @param snippets snippets
   * @return void
   * @title addSnippet
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:52
   */
  public void addSnippet(String location, String snippets) {
    this.snippets.add(location, snippets);
  }

  /**
   * @param location location
   * @return java.lang.String
   * @title snippets
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:52
   */
  public String snippets(String location) {
    return this.snippets.toString(location);
  }
}
