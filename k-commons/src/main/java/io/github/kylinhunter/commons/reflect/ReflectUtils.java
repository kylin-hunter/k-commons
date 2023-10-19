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
package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.exception.embed.InitException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 16:37
 */
public class ReflectUtils {

  /**
   * @param method method
   * @param obj obj
   * @param args args
   * @return java.lang.Object
   * @title invoke
   * @description
   * @author BiJi'an
   * @date 2023-02-10 10:38
   */
  @SuppressWarnings("unchecked")
  public static <T> T invoke(Object obj, Method method, Object... args) {
    try {
      method.setAccessible(true);
      return (T) method.invoke(obj, args);
    } catch (Exception e) {
      throw new InitException("invoke error: " + method, e);
    }
  }

  /**
   * @param obj obj
   * @param field field
   * @param value value
   * @title set
   * @description
   * @author BiJi'an
   * @date 2023-02-11 01:07
   */
  public static void set(Object obj, Field field, Object value) {
    try {
      field.setAccessible(true);
      field.set(obj, value);
    } catch (Exception e) {
      throw new InitException("invoke error", e);
    }
  }

  /**
   * @param obj obj
   * @param field field
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2023-06-15 01:49
   */
  public static Object get(Object obj, Field field) {
    try {
      field.setAccessible(true);
      return field.get(obj);
    } catch (Exception e) {
      throw new InitException("invoke error", e);
    }
  }

  /**
   * @param clazz clazz
   * @return java.lang.Class<?>
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-12 16:57
   */
  public static Class<?> getSuperClass(Class<?> clazz) {
    return SuperClazzes.get(clazz);
  }

  /**
   * @param clazz clazz
   * @return java.util.Collection<java.lang.Class < ?>>
   * @title getAll
   * @description
   * @author BiJi'an
   * @date 2023-05-11 00:14
   */
  @SafeVarargs
  public static Set<Class<?>> getAllSuperClasses(
      Class<?> clazz, Predicate<Class<?>>... predicates) {
    return SuperClazzes.getAll(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getInterfaces
   * @description
   * @author BiJi'an
   * @date 2023-05-12 17:33
   */
  @SafeVarargs
  public static Set<Class<?>> getInterfaces(Class<?> clazz, Predicate<Class<?>>... predicates) {
    return Interfaces.get(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getAllInterfaces
   * @description
   * @author BiJi'an
   * @date 2023-05-12 17:33
   */
  @SafeVarargs
  public static Set<Class<?>> getAllInterfaces(Class<?> clazz, Predicate<Class<?>>... predicates) {
    return Interfaces.getAll(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.reflect.Method>
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-13 00:29
   */
  @SafeVarargs
  public static Set<Method> getMethods(Class<?> clazz, Predicate<Method>... predicates) {
    return Methods.get(clazz, predicates);
  }

  public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {

    try {
      return clazz.getMethod(name, parameterTypes);
    } catch (NoSuchMethodException e) {
      // no method
    }
    return null;
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.reflect.Method>
   * @title getAll
   * @description
   * @author BiJi'an
   * @date 2023-05-13 00:29
   */
  @SafeVarargs
  public static Set<Method> getAllMethods(Class<?> clazz, Predicate<Method>... predicates) {
    return Methods.getAll(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.reflect.Constructor < ?>>
   * @title getConstructors
   * @description getConstructors
   * @author BiJi'an
   * @date 2023-06-21 02:06
   */
  @SafeVarargs
  public static Set<Constructor<?>> getConstructors(
      Class<?> clazz, Predicate<Constructor<?>>... predicates) {
    return Constructors.get(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.reflect.Constructor < ?>>
   * @title getAllConstructors
   * @description getAllConstructors
   * @author BiJi'an
   * @date 2023-06-21 02:06
   */
  @SafeVarargs
  public static Set<Constructor<?>> getAllConstructors(
      Class<?> clazz, Predicate<Constructor<?>>... predicates) {
    return Constructors.getAll(clazz, predicates);
  }
}
