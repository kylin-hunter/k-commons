package o.github.kylinhunter.commons.utils.bean.copy.convertor.imp;

import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import java.beans.PropertyDescriptor;
import java.text.DecimalFormat;
import java.util.Set;
import o.github.kylinhunter.commons.utils.bean.copy.AbstractFieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.Direction;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
public class NumberToStrFieldConvertor extends AbstractFieldConvertor {
  private static final ThreadLocal<DecimalFormat> decimalFormats =
      ThreadLocal.withInitial(() -> new DecimalFormat("#.######"));

  private static final Set<Class<?>> acceptedClazzes =
      SetUtils.newHashSet(
          int.class,
          Integer.class,
          long.class,
          Long.class,
          float.class,
          Float.class,
          double.class,
          Double.class);

  public NumberToStrFieldConvertor(
      Direction direction, PropertyDescriptor sourcePD, PropertyDescriptor targetPD) {
    super(direction, sourcePD, targetPD);
    Class<?> propertyType =
        direction == Direction.FORWARD ? targetPD.getPropertyType() : sourcePD.getPropertyType();
    if (propertyType != String.class) {
      throw new InitException("  require  a String type");
    }

    propertyType =
        direction == Direction.FORWARD ? sourcePD.getPropertyType() : targetPD.getPropertyType();
    if (!acceptedClazzes.contains(propertyType)) {
      throw new InitException("  no accept type=>" + propertyType.getName());
    }
  }

  @Override
  public void forword(Object source, Object target) {
    final Class<?> propertyType = sourcePD.getPropertyType();

    Object sourceValue = this.read(source);
    if (sourceValue != null) {
      if (propertyType == int.class
          || propertyType == Integer.class
          || propertyType == long.class
          || propertyType == Long.class) {
        this.write(target, String.valueOf(sourceValue));

      } else {
        this.write(target, decimalFormats.get().format(((Number) sourceValue).doubleValue()));
      }
    }
  }

  @Override
  public void backward(Object source, Object target) {
    final Class<?> propertyType = targetPD.getPropertyType();

    String sourceValue = this.read(source);
    if (sourceValue != null) {
      if (propertyType == int.class || propertyType == Integer.class) {
        this.write(target, NumberUtils.toInt(sourceValue));
      } else if (propertyType == long.class || propertyType == Long.class) {
        this.write(target, NumberUtils.toLong(sourceValue));
      } else if (propertyType == float.class || propertyType == Float.class) {
        this.write(target, NumberUtils.toFloat(sourceValue));
      } else if (propertyType == double.class || propertyType == Double.class) {
        this.write(target, NumberUtils.toDouble(sourceValue));
      }
    }
  }
}
