package io.github.kylinhunter.commons.exception.explain;

import io.github.kylinhunter.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 02:11
 **/
public class DefaultExplainerSupplier extends AbstractExplainerSupplier {

    @Override
    public void customize() {
        this.createExplain()
                .setSource(IllegalArgumentException.class)
                .setExplainer(e -> new Explainer.ExplainResult(ErrInfos.PARAM, e.getMessage()));

    }

}
