package com.kylinhunter.plat.commons.bean.copy.convertor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@FunctionalInterface
public interface FieldCopyConvertor {
    void convert(Object source, Object target);
}
