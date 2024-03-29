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
package io.github.kylinhunter.commons.component;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.init.ClassScanner;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 */
class CConstructorManager {

  private final Map<Class<?>, List<CConstructor>> constructorsMap = MapUtils.newHashMap();
  @Getter private List<CConstructor> constructors = ListUtils.newArrayList();
  @Getter private final Set<Class<?>> compClasses = SetUtils.newHashSet();
  private final ClassScanner classScanner;
  private final CConstructorDepCalculator constructorDepCalculator;

  public CConstructorManager(CompManager compManager) {
    this.classScanner = compManager.classScanner;
    this.constructorDepCalculator = new CConstructorDepCalculator(this);
  }

  /**
   * @title clear
   * @description
   * @author BiJi'an
   * @date 2023-01-19 23:14
   */
  public void clean() {
    constructorsMap.clear();
    constructors.clear();
    compClasses.clear();
  }

  /**
   * @return java.util.List<io.github.kylinhunter.commons.component.CConstructor>
   * @title initConstructors
   * @description
   * @author BiJi'an
   * @date 2023-01-19 23:57
   */
  public void calculate() {
    this.clean();
    Set<Class<?>> compClazzes = classScanner.getClassesByAnnotatedWith(C.class);
    for (Class<?> compClazz : compClazzes) {
      this.calculate(compClazz);
    }
    this.constructors = constructorDepCalculator.calculate(this.constructors);
  }

  /**
   * @param compClazz clazz
   * @title calConstructor
   * @description
   * @author BiJi'an
   * @date 2023-01-19 23:53
   */
  private void calculate(Class<?> compClazz) {
    C c = compClazz.getAnnotation(C.class);
    CConstructor cconstructor = new CConstructor(compClazz, c);
    this.compClasses.add(compClazz);
    this.constructors.add(cconstructor);
    this.registerAll(compClazz, cconstructor);
  }

  /**
   * @param compClazz compClazz
   * @param cconstructor cconstructor
   * @title registerAll
   * @description
   * @author BiJi'an
   * @date 2023-02-11 23:08
   */
  private void registerAll(Class<?> compClazz, CConstructor cconstructor) {
    this.register(compClazz, cconstructor);
    Set<Class<?>> interfaces = classScanner.getAllInterface(compClazz);
    for (Class<?> interfaceClazz : interfaces) {
      this.register(interfaceClazz, cconstructor);
    }
  }

  /**
   * @param clazz clazz
   * @param cconstructor cconstructor
   * @title register
   * @description
   * @author BiJi'an
   * @date 2023-02-12 22:28
   */
  private void register(Class<?> clazz, CConstructor cconstructor) {
    constructorsMap.compute(
        clazz,
        (k, v) -> {
          if (v == null) {
            v = ListUtils.newArrayList();
          }
          if (!v.contains(cconstructor)) {
            if (cconstructor.isPrimary()) {
              v.add(0, cconstructor);
            } else {
              v.add(cconstructor);
            }
          }
          return v;
        });
  }

  /**
   * @param compClazz compClazz
   * @return io.github.kylinhunter.commons.component.CConstructor
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-01-19 23:56
   */
  public CConstructor get(Class<?> compClazz) {
    return get(compClazz, false);
  }

  /**
   * @param compClazz compClazz
   * @param required required
   * @return io.github.kylinhunter.commons.component.CConstructor
   * @title getFirst
   * @description
   * @author BiJi'an
   * @date 2023-02-11 22:10
   */
  public CConstructor get(Class<?> compClazz, boolean required) {
    List<CConstructor> constructors = constructorsMap.get(compClazz);
    if (constructors != null && constructors.size() > 0) {
      return constructors.get(0);
    }
    if (required) {
      throw new InitException(" no cconstructor " + compClazz.getName());
    }
    return null;
  }

  /**
   * @param compClazz compClazz
   * @return java.util.Set<io.github.kylinhunter.commons.component.CConstructor>
   * @title getCConstructorByInterface
   * @description
   * @author BiJi'an
   * @date 2023-01-19 23:56
   */
  public List<CConstructor> getAll(Class<?> compClazz) {
    List<CConstructor> cConstructors = constructorsMap.get(compClazz);
    if (cConstructors != null) {
      return cConstructors;
    } else {
      return Collections.emptyList();
    }
  }
}
