package com.kylinhunter.plat.commons.bean.copy.convertor;

/**
 * @description
 * @author BiJi'an
 * @date   2022-01-01 19:09
 **/

public interface ClassCopyConvertor<S, T> {
    void convert(S source, T target);
}
