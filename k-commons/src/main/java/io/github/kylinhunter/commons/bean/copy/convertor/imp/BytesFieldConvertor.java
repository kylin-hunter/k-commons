package io.github.kylinhunter.commons.bean.copy.convertor.imp;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.github.kylinhunter.commons.bean.copy.AbstractFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.ConvertExcetion;
import io.github.kylinhunter.commons.bean.copy.convertor.Direction;
import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
public class BytesFieldConvertor extends AbstractFieldConvertor {

    public BytesFieldConvertor(Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
        super(direction, sourcePD, targetPD);
        if (direction == Direction.FORWARD) {

            final Class<?> returnType = targetPD.getReadMethod().getReturnType();
            if (!returnType.isArray() || !(returnType.getComponentType() == byte.class)) {
                throw new InitException("target is not byte[] type");
            }

        }
    }

    @Override
    public void forword(Object source, Object target) throws ConvertExcetion {
        try (ByteArrayOutputStream bao = new ByteArrayOutputStream();
             ObjectOutputStream oo = new ObjectOutputStream(bao)) {
            Object sourceValue = this.read(source);

            if (sourceValue != null) {
                oo.writeObject(sourceValue);
                this.write(target, bao.toByteArray());
            }

        } catch (Exception e) {
            throw new ConvertExcetion("convert error", e);
        }

    }

    @Override
    public void backward(Object source, Object target) throws ConvertExcetion {
        try {
            Object sourceValue = this.read(source);
            if (sourceValue != null) {
                ByteArrayInputStream bai = new ByteArrayInputStream((byte[]) sourceValue);
                ObjectInputStream in = new ObjectInputStream(bai);
                this.write(target, in.readObject());
            }

        } catch (Exception e) {
            throw new ConvertExcetion("convert error", e);
        }
    }
}
