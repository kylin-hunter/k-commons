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
package io.github.kylinhunter.commons.branch;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author BiJi'an
 * @description
 * @date 2022/01/01
 */
public interface Branch<P, T> {

  Predicate<P> tester();

  Function<P, T> factory();

  /**
   * @param tester tester
   * @param factory factory
   * @return io.github.kylinhunter.commons.tools.select.Branch
   *     <P, T>
   * @title of
   * @description
   * @author BiJi'an
   * @date 2022-05-31 15:59
   */
  static <P, T> Branch<P, T> of(Predicate<P> tester, Function<P, T> factory) {
    return new Branch<P, T>() {

      @Override
      public Predicate<P> tester() {
        return tester;
      }

      @Override
      public Function<P, T> factory() {
        return factory;
      }
    };
  }
}
