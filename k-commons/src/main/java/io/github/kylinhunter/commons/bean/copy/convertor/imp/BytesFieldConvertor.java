package io.github.kylinhunter.commons.bean.copy.convertor.imp;

import java.beans.PropertyDescriptor;

import io.github.kylinhunter.commons.bean.copy.AbstractFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.ConvertExcetion;
import io.github.kylinhunter.commons.bean.copy.convertor.Direction;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.serialize.ObjectBytesSerializer;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
public class BytesFieldConvertor extends AbstractFieldConvertor {

    public BytesFieldConvertor(Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
        super(direction, sourcePD, targetPD);
        Class<?> propertyType = direction == Direction.FORWARD ? targetPD.getPropertyType() : sourcePD.getPropertyType();
        if (!propertyType.isArray() || !(propertyType.getComponentType() == byte.class)) {
            throw new InitException(" not a byte[] type");
        }
    }

    @Override
    public void forword(Object source, Object target) throws ConvertExcetion {
        try {
            Object sourceValue = this.read(source);
            if (sourceValue != null) {
                this.write(target, ObjectBytesSerializer.serialize(sourceValue));
            }
        } catch (Exception e) {
            throw new ConvertExcetion(" convert error ", e);
        }

    }

    @Override
    public void backward(Object source, Object target) throws ConvertExcetion {
        Object sourceValue = this.read(source);
        if (sourceValue != null) {
            try {
                this.write(target, ObjectBytesSerializer.deserialize((byte[]) sourceValue));
            } catch (Exception e) {
                throw new ConvertExcetion("convert error", e);
            }
        }
    }
}
