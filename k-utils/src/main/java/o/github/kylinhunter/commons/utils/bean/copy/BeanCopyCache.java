package o.github.kylinhunter.commons.utils.bean.copy;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import o.github.kylinhunter.commons.utils.bean.BeanCopyUtils;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.ConvertType;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.Direction;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.FieldConvertor;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.FieldCopy;
import o.github.kylinhunter.commons.utils.bean.info.BeanIntrospector;
import o.github.kylinhunter.commons.utils.bean.info.BeanIntrospectors;
import org.reflections.ReflectionUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
@Data
public class BeanCopyCache {
  public static final Map<String, BeanCopy> FIELD_COPYS = MapUtils.newConcurrentMap();
  public static final BeanCopy DEFAULT_BEAN_COPY = new BeanCopy();

  /**
   * @param sourceClass sourceClass
   * @param targetClass targetClass
   * @return io.github.kylinhunter.commons.utils.bean.BeanCopy
   * @title getBeanCopy
   * @description
   * @author BiJi'an
   * @date 2021/8/13 8:47 下午
   */
  public static BeanCopy getBeanCopy(Class<?> sourceClass, Class<?> targetClass) {
    String key = sourceClass.getName() + targetClass.getName();
    BeanCopy beanCopy = FIELD_COPYS.get(key);
    if (beanCopy == null) {
      synchronized (BeanCopyUtils.class) {
        beanCopy = FIELD_COPYS.get(key);
        if (beanCopy == null) {
          beanCopy = init(sourceClass, targetClass);
          FIELD_COPYS.put(key, beanCopy);
        }
      }
    }
    return beanCopy;
  }

  /**
   * @param sourceClass sourceClass
   * @param targetClass targetClass
   * @return io.github.kylinhunter.commons.bean.copy.BeanCopy
   * @title init
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:16
   */
  private static BeanCopy init(Class<?> sourceClass, Class<?> targetClass) {

    BeanCopy beanCopy = new BeanCopy();
    beanCopy.setFieldConvertors(initFieldConvertor(sourceClass, targetClass));
    if (!beanCopy.isEmpty()) {
      return beanCopy;
    } else {
      return DEFAULT_BEAN_COPY;
    }
  }

  /**
   * @param sourceClass sourceClass
   * @param targetClass targetClass
   * @return io.github.kylinhunter.commons.utils.bean.BeanCopy
   * @title initFieldConvertor
   * @description
   * @author BiJi'an
   * @date 2021/8/13 8:47 下午
   */
  @SuppressWarnings("unchecked")
  private static List<FieldConvertor> initFieldConvertor(
      Class<?> sourceClass, Class<?> targetClass) {
    try {
      List<FieldConvertor> fieldConvertors = ListUtils.newArrayList();
      Set<Field> allSourceFields = ReflectionUtils.getAllFields(sourceClass);
      Map<String, Field> targetFields =
          ReflectionUtils.getAllFields(targetClass).stream()
              .collect(Collectors.toMap(Field::getName, e -> e, (o, n) -> n));

      for (Field sourceField : allSourceFields) {
        String fieldName = sourceField.getName();
        Field targetField = targetFields.get(fieldName);
        if (targetField != null) {
          FieldCopy fieldCopy = sourceField.getAnnotation(FieldCopy.class);
          if (fieldCopy != null) {
            ConvertType convertType = fieldCopy.value();

            FieldConvertor fieldConvertor =
                initFieldConvertor(
                    Direction.FORWARD, sourceClass, targetClass, fieldName, convertType);
            if (fieldConvertor != null) {
              fieldConvertors.add(fieldConvertor);
            }

          } else {

            FieldCopy targetFieldCopy = targetField.getAnnotation(FieldCopy.class);
            if (targetFieldCopy != null) {
              ConvertType convertType = targetFieldCopy.value();
              FieldConvertor fieldConvertor =
                  initFieldConvertor(
                      Direction.BACKEND, sourceClass, targetClass, fieldName, convertType);
              if (fieldConvertor != null) {
                fieldConvertors.add(fieldConvertor);
              }
            }
          }
        }
      }

      return fieldConvertors;
    } catch (Exception e) {
      throw new InitException("init field convertor error", e);
    }
  }

  /**
   * @param direction direction
   * @param sourceClass sourceClass
   * @param targetClass targetClass
   * @param fieldName fieldName
   * @param convertType convertType
   * @return io.github.kylinhunter.commons.bean.copy.convertor.FieldConvertor
   * @title processJson
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:26
   */
  private static FieldConvertor initFieldConvertor(
      Direction direction,
      Class<?> sourceClass,
      Class<?> targetClass,
      String fieldName,
      ConvertType convertType)
      throws NoSuchMethodException,
          IllegalAccessException,
          InvocationTargetException,
          InstantiationException {
    BeanIntrospector sourceBeanIntrospector = BeanIntrospectors.get(sourceClass);
    PropertyDescriptor sourcePD = sourceBeanIntrospector.getPropertyDescriptor(fieldName);
    BeanIntrospector targetBeanIntrospector = BeanIntrospectors.get(targetClass);
    PropertyDescriptor targetPD = targetBeanIntrospector.getPropertyDescriptor(fieldName);
    if (sourcePD != null && targetPD != null) {
      Method read = sourcePD.getReadMethod();
      Method write = targetPD.getWriteMethod();
      if (read != null && write != null) {
        Class<? extends FieldConvertor> clazz = convertType.getClazz();
        Constructor<? extends FieldConvertor> constructor =
            clazz.getConstructor(
                Direction.class, PropertyDescriptor.class, PropertyDescriptor.class);
        return constructor.newInstance(direction, sourcePD, targetPD);
      }
    }
    return null;
  }
}
