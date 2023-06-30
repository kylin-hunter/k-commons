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
package io.github.kylinhunter.commons.exception;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import io.github.kylinhunter.commons.reflect.ObjectCreator;

public class ExCatcher {
  public static <R> R run(Supplier<R> supplier) {
    return run(BizException.class, supplier);
  }

  public static <T extends RuntimeException, R> R run(Class<T> clazz, Supplier<R> supplier) {

    try {
      return supplier.get();
    } catch (Throwable e) {
      throw ObjectCreator.create(clazz, new Class[] {Throwable.class}, new Object[] {e});
    }
  }

  @FunctionalInterface
  public interface Supplier<T> {
    T get() throws Throwable;
  }
}
