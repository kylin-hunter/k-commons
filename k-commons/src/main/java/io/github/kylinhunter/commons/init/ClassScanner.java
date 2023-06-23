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
package io.github.kylinhunter.commons.init;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-20 15:45
 */
public class ClassScanner {

  private final Reflections reflections;

  public ClassScanner(Set<String> allPackages) {
    reflections = new Reflections(allPackages, Scanners.SubTypes, Scanners.TypesAnnotated);
  }

  /**
   * @param type type
   * @return java.util.Set<java.lang.Class < ? extends T>>
   * @title getSubTypesOf
   * @description getSubTypesOf
   * @author BiJi'an
   * @date 2023-06-20 20:39
   */
  public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
    return reflections.getSubTypesOf(type);
  }

  /**
   * @param annotation annotation
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getTypesAnnotatedWith
   * @description getTypesAnnotatedWith
   * @author BiJi'an
   * @date 2023-06-20 21:06
   */
  public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getTypesAnnotatedWith(annotation);
  }
}
