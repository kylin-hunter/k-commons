package o.github.kylinhunter.commons.utils.bean.info;

import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import io.github.kylinhunter.commons.reflect.bean.ActualType;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.ClassUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:08
 */
@Data
public class ExPropertyDescriptor {
  private PropertyDescriptor propertyDescriptor;
  private boolean canReadWrite;
  private ExPropType exPropType = ExPropType.UNKNOWN;
  private Class<?>[] genericActualClazzes;

  public ExPropertyDescriptor(PropertyDescriptor pd) {
    this.propertyDescriptor = pd;
    this.canReadWrite = pd.getWriteMethod() != null && pd.getReadMethod() != null;

    Class<?> propertyType = pd.getPropertyType();

    if (ClassUtils.isPrimitiveOrWrapper(propertyType)) {
      this.exPropType = ExPropType.PRIMITIVE_OR_WRAPPER;
    } else if (propertyType == String.class) {
      this.exPropType = ExPropType.STRING;
    } else if (propertyType.isArray()) {
      this.exPropType = ExPropType.ARRAY;
      this.genericActualClazzes = new Class[] {propertyType.getComponentType()};
    } else if (List.class.isAssignableFrom(propertyType)) {
      this.exPropType = ExPropType.LIST;
      ActualType actualType = GenericTypeUtils.getMethodReturnActualType(pd.getReadMethod());
      if (actualType != null && actualType.getType(0) != null) {
        this.genericActualClazzes = actualType.getTypes();
      }
    } else {
      String packageName = ClassUtils.getPackageName(propertyType);
      if (!packageName.startsWith("java.") && !packageName.startsWith("com.sun")) {
        this.exPropType = ExPropType.NON_JDK_TYPE;
      }
    }
  }

  /**
   * @return java.lang.Class<?>
   * @title getPropActualClazz
   * @description
   * @author BiJi'an
   * @date 2023-03-19 14:34
   */
  public Class<?> getActualClazz() {
    if (genericActualClazzes != null && genericActualClazzes.length > 0) {
      return genericActualClazzes[0];
    }
    return null;
  }

  /**
   * @return java.lang.String
   * @title getName
   * @description
   * @author BiJi'an
   * @date 2023-04-01 10:46
   */
  public String getName() {
    return propertyDescriptor.getName();
  }

  /**
   * @return java.lang.Class<?>
   * @title getPropertyType
   * @description
   * @author BiJi'an
   * @date 2023-04-01 10:51
   */
  public Class<?> getPropertyType() {
    return propertyDescriptor.getPropertyType();
  }

  /**
   * @return java.lang.reflect.Method
   * @title getReadMethod
   * @description
   * @author BiJi'an
   * @date 2023-04-01 10:55
   */
  public Method getReadMethod() {
    return propertyDescriptor.getReadMethod();
  }

  /**
   * @return java.lang.reflect.Method
   * @title getWriteMethod
   * @description
   * @author BiJi'an
   * @date 2023-04-01 10:55
   */
  public Method getWriteMethod() {
    return propertyDescriptor.getWriteMethod();
  }
}
