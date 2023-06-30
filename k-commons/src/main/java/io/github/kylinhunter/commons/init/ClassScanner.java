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

import io.github.kylinhunter.commons.collections.SetUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-20 15:45
 */
public class ClassScanner {

  private final Set<String> allPackages;
  private final Reflections reflections;

  public ClassScanner(String pkg) {
    this(SetUtils.newHashSet(pkg));
  }

  public ClassScanner(Set<String> allPackages) {
    this.allPackages = allPackages;
    reflections =
        new Reflections(
            allPackages, Scanners.SubTypes, Scanners.TypesAnnotated, Scanners.FieldsAnnotated);
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

  /**
   * @param annotation annotation
   * @return java.util.Set<java.lang.reflect.Field> x
   * @title getFieldsAnnotatedWith
   * @description getFieldsAnnotatedWith
   * @author BiJi'an
   * @date 2023-06-25 20:03
   */
  public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {

    return reflections.getFieldsAnnotatedWith(annotation);
  }

  /**
   * @param clazz clazz
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getAllInterface
   * @description
   * @author BiJi'an
   * @date 2023-01-19 02:43
   */
  @SuppressWarnings("unchecked")
  public Set<Class<?>> getAllInterface(Class<?> clazz) {
    return ReflectionUtils.getAllSuperTypes(clazz, c -> c.isInterface() && isValid(c));
  }

  /**
   * @param clazz clazz
   * @return boolean
   * @title isValid
   * @description
   * @author BiJi'an
   * @date 2023-01-19 22:07
   */
  public boolean isValid(Class<?> clazz) {
    for (String pkg : allPackages) {

      if (clazz.getPackage().getName().contains(pkg)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param annotation annotation
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getTypesAnnotatedWith
   * @description
   * @author BiJi'an
   * @date 2023-02-09 23:26
   */
  public Set<Class<?>> getClassesByAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getTypesAnnotatedWith(annotation);
  }
}
