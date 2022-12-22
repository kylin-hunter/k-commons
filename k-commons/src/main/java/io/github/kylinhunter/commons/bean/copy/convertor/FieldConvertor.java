package io.github.kylinhunter.commons.bean.copy.convertor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@FunctionalInterface
public interface FieldConvertor {
    /**
     * @param source source
     * @param target target
     * @return void
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:02
     */
    void convert(Object source, Object target) throws ConvertExcetion;
}
