package io.github.kylinhunter.commons.bean.copy.convertor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/

public interface ClassConvertor<S, T> {
    /**
     * @param source source
     * @param target target
     * @return void
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:04
     */
    void convert(S source, T target);
}
