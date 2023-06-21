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