package io.github.kylinhunter.commons.bean.copy.convertor.imp;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;

import io.github.kylinhunter.commons.bean.copy.AbstractFieldConvertor;
import io.github.kylinhunter.commons.util.JsonUtils;
import io.github.kylinhunter.commons.bean.copy.convertor.Direction;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
public class JsonFieldConvertor extends AbstractFieldConvertor {

    private JavaType targetType;

    public JsonFieldConvertor(Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
        super(direction, sourcePD, targetPD);
        Method readMethod = targetPD.getReadMethod();
        if (List.class.isAssignableFrom(readMethod.getReturnType())) {
            Type returnType = readMethod.getGenericReturnType();
            if (returnType instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) returnType;
                Class<?> clazz = (Class<?>) type.getActualTypeArguments()[0];
                targetType = JsonUtils.constructParametricType(List.class, clazz);
            }

        }
    }

    @Override
    public void forword(Object source, Object target) {
        Object sourceValue = this.read(source);
        String targetValue = JsonUtils.toString(sourceValue, true);
        this.write(target, targetValue);
    }

    @Override
    public void backward(Object source, Object target) {
        Object sourceValue = this.read(source);
        Object targetValue;
        if (targetType != null) {
            targetValue = JsonUtils.readValue(String.valueOf(sourceValue), targetType, true);
        } else {
            targetValue = JsonUtils.toObject(String.valueOf(sourceValue), targetPD.getPropertyType(), true);
        }
        this.write(target, targetValue);
    }
}
