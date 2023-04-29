package io.github.kylinhunter.commons.bean.copy.convertor.imp;

import io.github.kylinhunter.commons.bean.copy.AbstractFieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.Direction;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.yaml.YamlHelper;
import java.beans.PropertyDescriptor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
public class YamlFieldConvertor extends AbstractFieldConvertor {

    public YamlFieldConvertor(Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
        super(direction, sourcePD, targetPD);
        Class<?> propertyType =
                direction == Direction.FORWARD ? targetPD.getPropertyType() : sourcePD.getPropertyType();
        if (propertyType != String.class) {
            throw new InitException(" not a String type");
        }
    }

    @Override
    public void forword(Object source, Object target) {
        Object sourceValue = this.read(source);
        if (sourceValue != null) {
            this.write(target, YamlHelper.dumpAsMap(sourceValue));
        }
    }

    @Override
    public void backward(Object source, Object target) {
        String sourceValue = this.read(source);
        if (sourceValue != null) {
            this.write(target, YamlHelper.loadFromText(this.targetPD.getPropertyType(), sourceValue));
        }
    }
}
