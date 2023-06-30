package io.github.kylinhunter.commons.exception.explain;

import io.github.kylinhunter.commons.exception.info.ErrInfo;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 02:11
 */
public class TestExplainerSupplier extends AbstractExplainerSupplier {

  public static final ErrInfo errInfoInfoTest = new ErrInfo(-100, "errInfo2-msg");

  @Override
  public void explain() {

    this.addExplainer(TestException2.class)
        .explain(
            e -> {
              ExplainResult explainResult = new ExplainResult(errInfoInfoTest);
              explainResult.setExtra("extra");
              return explainResult;
            });
  }
}
