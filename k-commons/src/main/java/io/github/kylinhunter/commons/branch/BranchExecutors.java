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

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-06 17:05
 */
public class BranchExecutors {

  /**
   * @param p p
   * @param tClass
   * @return io.github.kylinhunter.commons.select.BranchExecutor
   *     <P, T>
   * @title use
   * @description
   * @author BiJi'an
   * @date 2022-11-21 02:40
   */
  public static <P, T> BranchExecutor<P, T> use(P p, Class<T> tClass) {
    return new BranchExecutor<>(p);
  }
}
