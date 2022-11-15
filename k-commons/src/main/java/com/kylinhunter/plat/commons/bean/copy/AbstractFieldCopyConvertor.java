package com.kylinhunter.plat.commons.bean.copy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.kylinhunter.plat.commons.bean.copy.convertor.FieldCopyConvertor;
import com.kylinhunter.plat.commons.exception.inner.InternalException;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Data
public abstract class AbstractFieldCopyConvertor implements FieldCopyConvertor  {

    protected final boolean reverse;
    protected final Method sourceMethod;
    protected final Method targetMethod;
    protected final Field sourceField;
    protected final Field targetField;

    public Object getSourceValue(Object source) {
        try {
            return sourceMethod.invoke(source);
        } catch (Exception e) {
            throw new InternalException("getSourceValue error", e);
        }

    }

    public void setTargetValue(Object target, Object value) {
        try {
            targetMethod.invoke(target, value);
        } catch (Exception e) {
            throw new InternalException("setTargetValue error", e);
        }
    }

    @Override
    public void convert(Object source, Object target) {
        if (reverse) {
            this.backward(source, target);
        } else {
            this.forword(source, target);
        }
    }

    public abstract void forword(Object source, Object target);

    public abstract void backward(Object source, Object target);

}
