package o.github.kylinhunter.commons.utils.bean.copy.convertor.imp;

import io.github.kylinhunter.commons.exception.embed.InitException;
import java.beans.PropertyDescriptor;
import o.github.kylinhunter.commons.utils.bean.copy.AbstractFieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.Direction;
import o.github.kylinhunter.commons.utils.yaml.YamlHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
public class YamlFieldConvertor extends AbstractFieldConvertor {

  public YamlFieldConvertor(
      Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
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
