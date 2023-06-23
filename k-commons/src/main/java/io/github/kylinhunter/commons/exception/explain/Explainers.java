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

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.ExceptionFinder;
import io.github.kylinhunter.commons.exception.ExceptionFinder.ExceptionFind;
import io.github.kylinhunter.commons.exception.common.KThrowable;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-21 23:08
 */
public class Explainers {

  public final Map<Class<? extends Throwable>, Explainer> allExplainers = MapUtils.newHashMap();
  public final Set<Class<? extends Throwable>> allExceptions = SetUtils.newHashSet();

  public Explainer getExplain(Class<? extends Throwable> clazz) {
    return allExplainers.get(clazz);
  }

  /**
   * @param explainers explainers
   * @title add
   * @description add
   * @author BiJi'an
   * @date 2023-06-22 00:13
   */
  public void add(List<Explainer> explainers) {
    explainers.forEach(
        explain -> {
          allExplainers.put(explain.getSource(), explain);
          allExceptions.add(explain.getSource());
        });
  }

  /**
   * @param throwable throwable
   * @return io.github.kylinhunter.commons.exception.explain.ExplainResult
   * @title explain
   * @description explain
   * @author BiJi'an
   * @date 2023-06-22 00:26
   */
  public ExplainResult explain(Throwable throwable) {

    ExplainResult explainResult = null;
    if (throwable instanceof KThrowable) {
      explainResult = new ExplainResult((KThrowable) throwable, throwable.getMessage());
    } else {
      ExceptionFind exceptionFind = ExceptionFinder.find(throwable, true, this.allExceptions);
      if (exceptionFind != null) {
        Explainer explainer = this.getExplain(exceptionFind.getSource());
        if (explainer != null) {
          explainResult = explainer.explain(exceptionFind.getTarget());
        }
      }
      if (explainResult == null) {
        explainResult = new ExplainResult(ErrInfos.UNKNOWN, throwable.getMessage());
      }
    }
    return explainResult;
  }
}
