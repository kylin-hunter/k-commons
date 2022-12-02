package io.github.kylinhunter.commons.exception.explain;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 02:11
 **/
public abstract class AbstractExplainerSupplier implements ExplainerSupplier {
    @Getter
    private final List<Explainer> explainers = Lists.newArrayList();

    /**
     * @return io.github.kylinhunter.commons.source.explainer.Explainer
     * @title create
     * @description
     * @author BiJi'an
     * @date 2022-11-24 02:59
     */
    public <T extends Throwable> Explainer<T> createExplain(Class<T> clazz) {
        Explainer explainer = new Explainer<T>(clazz);
         explainers.add(explainer);
        return explainer;
    }

    @Override
    public List<Explainer> get() {
        customize();
        return explainers;
    }

    public abstract void customize();
}
