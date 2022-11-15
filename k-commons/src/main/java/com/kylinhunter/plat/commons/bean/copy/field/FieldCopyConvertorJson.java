package com.kylinhunter.plat.commons.bean.copy.field;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.kylinhunter.plat.commons.bean.copy.AbstractFieldCopyConvertor;
import com.kylinhunter.plat.commons.util.JsonUtils;
import com.kylinhunter.plat.commons.util.ReflectionUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/

public class FieldCopyConvertorJson extends AbstractFieldCopyConvertor {

    private JavaType targetType;
    public FieldCopyConvertorJson(boolean reverse, Method sourceMethod, Method targetMethod, Field sourceField,
                                  Field targetField) {
        super(reverse, sourceMethod, targetMethod, sourceField, targetField);
        if (List.class.isAssignableFrom(targetField.getType())) {
            Class<?> clazz = ReflectionUtil.getGenericType(targetField, 0);
            targetType = JsonUtils.constructParametricType(List.class, clazz);

        }

    }

    @Override
    public void forword(Object source, Object target) {
        Object sourceValue = this.getSourceValue(source);
        String targetValue = JsonUtils.toString(sourceValue, true);
        this.setTargetValue(target, targetValue);
    }

    @Override
    public void backward(Object source, Object target) {
        Object sourceValue = this.getSourceValue(source);
        Object targetValue;
        if (targetType != null) {
            targetValue = JsonUtils.readValue(String.valueOf(sourceValue), targetType, true);
        } else {
            targetValue = JsonUtils.toObject(String.valueOf(sourceValue), this.targetField.getType(), true);
        }
        this.setTargetValue(target, targetValue);
    }
}
