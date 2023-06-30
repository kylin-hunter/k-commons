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

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 */
@RequiredArgsConstructor
class MethodCompManager {
  private CompManager compManager;
  protected CMethodManager methodManager;

  public MethodCompManager(CompManager compManager) {
    this.compManager = compManager;
    this.methodManager = new CMethodManager(compManager);
  }

  /**
   * @return void
   * @title calComponents
   * @description
   * @author BiJi'an
   * @date 2023-01-20 00:27
   */
  public void calculate() {
    methodManager.calculate();

    List<CMethod> methods = methodManager.getCmethods();
    for (CMethod method : methods) {
      calculate(method);
    }
    compManager.check(methodManager.getCompClasses());
  }

  /**
   * @param cmethod cmethod
   * @return void
   * @title calComponent
   * @description
   * @author BiJi'an
   * @date 2023-01-21 00:37
   */
  public void calculate(CMethod cmethod) {
    Method method = cmethod.getMethod();
    Object object = cmethod.getCompObject();

    int parameterCount = method.getParameterCount();
    Class<?> compClazz = method.getReturnType();
    if (parameterCount <= 0) {
      this.compManager.register(compClazz, cmethod, ObjectCreator.create(object, method));
    } else {
      Class<?>[] parameterTypes = method.getParameterTypes();
      Type[] genericParameterTypes = method.getGenericParameterTypes();

      Object[] parameterObj = new Object[parameterCount];
      for (int i = 0; i < parameterCount; i++) {
        Class<?> curParametorClass = parameterTypes[i];
        Object obj = compManager.get(curParametorClass, false);
        if (obj != null) {
          parameterObj[i] = obj;
        } else {
          if (List.class.isAssignableFrom(curParametorClass)) {
            Type type = genericParameterTypes[i];
            Class<?> argClass = GenericTypeUtils.getActualTypeArgument(type, 0);
            List<?> comps = compManager.getAll(argClass, false);
            if (comps.size() > 0) {
              parameterObj[i] = comps;
            }
          }
        }
        if (parameterObj[i] == null) {
          throw new InitException("no component:" + compClazz.getName());
        }
      }
      this.compManager.register(
          compClazz, cmethod, ObjectCreator.create(object, method, parameterObj));
    }
  }
}
