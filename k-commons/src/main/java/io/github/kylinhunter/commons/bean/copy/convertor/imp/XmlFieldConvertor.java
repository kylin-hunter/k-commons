package io.github.kylinhunter.commons.bean.copy.convertor.imp;

import java.beans.PropertyDescriptor;

import io.github.kylinhunter.commons.bean.copy.AbstractFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.Direction;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.xml.JAXBHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
public class XmlFieldConvertor extends AbstractFieldConvertor {

    public XmlFieldConvertor(Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
        super(direction, sourcePD, targetPD);
        Class<?> returnType = direction == Direction.FORWARD ? targetPD.getPropertyType() : sourcePD.getPropertyType();
        if (returnType != String.class) {
            throw new InitException(" not a String type");
        }
    }

    @Override
    public void forword(Object source, Object target) {
        Object sourceValue = this.read(source);
        if (sourceValue != null) {
            this.write(target, JAXBHelper.marshal(sourceValue));
        }
    }

    @Override
    public void backward(Object source, Object target) {
        String sourceValue = this.read(source);
        if (sourceValue != null) {
            this.write(target, JAXBHelper.unmarshal(this.targetPD.getPropertyType(), sourceValue));
        }
    }
}
