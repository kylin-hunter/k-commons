package com.kylinhunter.plat.commons.exception.explain;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-19 18:59
 **/
@FunctionalInterface
public interface Explain<T extends Throwable> {
    ExplainInfo explain(T t);
}
