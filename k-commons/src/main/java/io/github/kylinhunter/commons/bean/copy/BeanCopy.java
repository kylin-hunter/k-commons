package io.github.kylinhunter.commons.bean.copy;

import java.util.List;

import io.github.kylinhunter.commons.bean.copy.convertor.ClassConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.ConvertExcetion;
import io.github.kylinhunter.commons.bean.copy.convertor.FieldConvertor;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Data
public class BeanCopy {
    private ClassConvertor<Object, Object> classConvertor;
    private List<FieldConvertor> fieldConvertors;

    public boolean isEmpty() {
        return classConvertor == null && (fieldConvertors == null || fieldConvertors.isEmpty());
    }

    /**
     * @param source source
     * @param target target
     * @return void
     * @title copy
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:14
     */
    public void copy(Object source, Object target) throws ConvertExcetion {
        if (classConvertor != null) {
            classConvertor.convert(source, target);
        }
        if (fieldConvertors != null && !fieldConvertors.isEmpty()) {
            for (FieldConvertor fieldConvertor : fieldConvertors) {
                fieldConvertor.convert(source, target);
            }
        }
    }
}
