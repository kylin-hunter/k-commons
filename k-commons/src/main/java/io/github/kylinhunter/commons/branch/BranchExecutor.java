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
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2022/01/01
 */
@ToString(of = "selected")
public class BranchExecutor<P, T> {
  private boolean selected = false;
  private Function<P, T> factory;

  private final P param;

  public BranchExecutor(P param) {
    this.param = param;
  }

  /**
   * @param param param
   * @return io.github.kylinhunter.commons.tools.select.BranchExecutor
   *     <P, T>
   * @title
   * @description
   * @author BiJi'an
   * @date 2022/01/01 4:51 下午
   */
  public static <P, T> BranchExecutor<P, T> param(P param) {
    return new BranchExecutor<>(param);
  }

  /**
   * @param branch branch
   * @return io.github.kylinhunter.commons.tools.select.BranchExecutor
   *     <P, T>
   * @title
   * @description
   * @author BiJi'an
   * @date 2022/01/01 4:51 下午
   */
  public BranchExecutor<P, T> test(Branch<P, T> branch) {
    if (!selected) {
      boolean pass = branch.tester().test(param);
      if (pass) {
        selected = true;
        factory = branch.factory();
      }
    }
    return this;
  }

  /**
   * @param supplier supplier
   * @return T
   * @title
   * @description
   * @author BiJi'an
   * @date 2022/01/01 4:52 下午
   */
  public T others(Function<P, T> supplier) {
    return selected ? this.factory.apply(param) : supplier.apply(param);
  }

  /**
   * @param tester tester
   * @return io.github.kylinhunter.commons.select.BranchBuilder
   *     <P, T>
   * @title predicate
   * @description
   * @author BiJi'an
   * @date 2022-11-21 02:39
   */
  public BranchBuilder<P, T> predicate(Predicate<P> tester) {

    return factory -> Branch.of(tester, factory);
  }
}
