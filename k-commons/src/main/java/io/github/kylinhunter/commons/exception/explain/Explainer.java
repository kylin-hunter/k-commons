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
package io.github.kylinhunter.commons.exception.explain;

import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 02:45
 */
@RequiredArgsConstructor
public class Explainer<T extends Throwable> {

  @Getter private final Class<? extends Throwable> source;
  private Function<T, ExplainResult> explainFun;

  /**
   * @param throwable throwable
   * @return io.github.kylinhunter.commons.exception.explain.ExplainResult
   * @title explain
   * @description explain
   * @author BiJi'an
   * @date 2023-06-22 00:38
   */
  public ExplainResult explain(T throwable) {
    return explainFun.apply(throwable);
  }

  /**
   * @param explainer explainer
   * @title explain
   * @description explain
   * @author BiJi'an
   * @date 2023-06-22 00:49
   */
  public void explain(Function<T, ExplainResult> explainer) {
    this.explainFun = explainer;
  }
}
