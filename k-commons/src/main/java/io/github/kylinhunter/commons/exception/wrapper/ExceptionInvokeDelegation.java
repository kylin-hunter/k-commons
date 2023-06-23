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
package io.github.kylinhunter.commons.exception.wrapper;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 10:36
 */
public class ExceptionInvokeDelegation {

  private static final Logger log = Logger.getLogger(ExceptionInvokeDelegation.class.toString());

  /**
   * @param method method
   * @param callable callable
   * @return java.lang.Object
   * @title intercept
   * @description intercept
   * @author BiJi'an
   * @date 2023-06-20 00:00
   */
  @RuntimeType
  public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable)
      throws Throwable {
    try {
      return callable.call();
    } catch (Throwable e) {
      Throwable thr = getNewException(method, e);
      if (thr != null) {
        throw thr;
      }
      throw e;
    }
  }

  /**
   * @param method method
   * @param oriThrowable oriThrowable
   * @return java.lang.Throwable
   * @title getNewThrowable
   * @description getNewThrowable
   * @author BiJi'an
   * @date 2023-06-20 00:00
   */
  @SuppressWarnings("unchecked")
  private static Throwable getNewException(Method method, Throwable oriThrowable) {
    ExceptionWrapper annotation = method.getAnnotation(ExceptionWrapper.class);
    if (annotation != null) {
      Class<? extends Throwable> thr = annotation.value();

      try {
        if (KRuntimeException.class.isAssignableFrom(thr)) {
          Constructor<? extends KRuntimeException> constructor =
              (Constructor<? extends KRuntimeException>) thr.getConstructor();
          KRuntimeException throwable = constructor.newInstance();
          String msg = annotation.msg();
          msg = !StringUtil.isEmpty(msg) ? msg : oriThrowable.getMessage();
          throwable.setErrInfo(new ErrInfo(annotation.code(), msg));
          return throwable;
        }
      } catch (Exception e) {
        log.warning("Exception Invoke Delegation error:" + e.getMessage());
      }

      try {
        Constructor<? extends Throwable> constructor = thr.getConstructor();
        Throwable throwable = constructor.newInstance();
        throwable.initCause(oriThrowable);
        return throwable;
      } catch (Exception e) {
        log.warning("Exception Invoke Delegation error:" + e.getMessage());
      }
    }

    return oriThrowable;
  }
}
