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
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 20:39
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClassInfo {

  @EqualsAndHashCode.Include private String packageName;
  @EqualsAndHashCode.Include private String name;
  private SuperClass superClass = new SuperClass();
  private String comment;
  private Interfaces interfaces = new Interfaces();
  private Snippets snippets = new Snippets();
  private Imports imports = new Imports();
  private List<FieldInfo> fields = ListUtils.newArrayList();

  /**
   * @param clazz clazz
   * @return void
   * @title addImport
   * @description
   * @author BiJi'an
   * @date 2023-02-19 19:30
   */
  public void addImport(Class<?> clazz) {
    this.imports.add(clazz.getName());
  }

  /**
   * @param fullClassName fullClassName
   * @return void
   * @title addImport
   * @description
   * @author BiJi'an
   * @date 2023-02-19 19:30
   */
  public void addImport(String fullClassName) {
    this.imports.add(fullClassName);
  }

  /**
   * @param fullClassName className
   * @return void
   * @title addInterface
   * @description
   * @author BiJi'an
   * @date 2023-02-19 19:31
   */
  public void addInterface(String fullClassName) {
    this.imports.add(fullClassName);
    this.interfaces.add(fullClassName);
  }

  /**
   * @param fullClassNames fullClassNames
   * @return void
   * @title addInterfaces
   * @description
   * @author BiJi'an
   * @date 2023-02-19 19:31
   */
  public void addInterfaces(List<String> fullClassNames) {
    if (!CollectionUtils.isEmpty(fullClassNames)) {
      fullClassNames.forEach(this::addInterface);
    }
  }

  /**
   * @param fullClassNames fullClassNames
   * @return void
   * @title setSuperClass
   * @description
   * @author BiJi'an
   * @date 2023-02-19 23:56
   */
  public void setSuperClass(String fullClassNames) {
    this.superClass.setClassName(fullClassNames);
    this.imports.add(fullClassNames);
  }

  /**
   * @param prefix prefix
   * @return java.lang.String
   * @title superClass
   * @description
   * @author BiJi'an
   * @date 2023-02-19 23:56
   */
  public String superClass(String prefix) {
    return this.superClass.toString(prefix);
  }

  /**
   * @param prefix prefix
   * @return java.lang.String
   * @title interfaces
   * @description
   * @author BiJi'an
   * @date 2023-02-19 23:57
   */
  public String interfaces(String prefix) {
    return this.interfaces.toString(prefix);
  }

  /**
   * @param prefix prefix
   * @param postfix postfix
   * @return java.lang.String
   * @title imports
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:03
   */
  public String imports(String prefix, String postfix) {
    return this.imports.toString(prefix, postfix);
  }

  /**
   * @param location location
   * @return java.lang.String
   * @title snippets
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:48
   */
  public String snippets(String location) {
    return this.snippets.toString(location);
  }
}
