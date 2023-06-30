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
import java.lang.reflect.Method;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class ObjectCreator {

  /**
   * @param clazz clazz
   * @param parameterTypes parameterTypes
   * @param parameters parameters
   * @return T
   * @title create
   * @description
   * @author BiJi'an
   * @date 2022-12-03 20:44
   */
  public static <T> T create(Class<T> clazz, Class<?>[] parameterTypes, Object[] parameters) {
    try {
      Constructor<T> constructor = clazz.getConstructor(parameterTypes);
      return constructor.newInstance(parameters);
    } catch (Exception e) {
      throw new InitException("init createBean error", e);
    }
  }

  /**
   * @param clazz clazz
   * @return T
   * @title createBean
   * @description
   * @author BiJi'an
   * @date 2023-01-20 00:10
   */
  public static <T> T create(Class<T> clazz) {
    try {
      Constructor<T> constructor = clazz.getConstructor();
      return constructor.newInstance();
    } catch (Exception e) {
      throw new InitException("init createBean error", e);
    }
  }

  /**
   * @param constructor constructor
   * @param initargs initargs
   * @return T
   * @title createBean
   * @description
   * @author BiJi'an
   * @date 2023-02-12 23:59
   */
  public static <T> T create(Constructor<T> constructor, Object... initargs) {
    try {
      return constructor.newInstance(initargs);
    } catch (Exception e) {
      throw new InitException("init createBean error", e);
    }
  }

  /**
   * @param method method
   * @param obj obj
   * @param args args
   * @return T
   * @title createBean1
   * @description
   * @author BiJi'an
   * @date 2023-02-12 00:06
   */
  public static <T> T create(Object obj, Method method, Object... args) {
    return ReflectUtils.invoke(obj, method, args);
  }
}
