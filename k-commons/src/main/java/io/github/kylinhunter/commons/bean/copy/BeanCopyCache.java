package io.github.kylinhunter.commons.bean.copy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.ReflectionUtils;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.bean.BeanCopyUtils;
import io.github.kylinhunter.commons.bean.copy.convertor.ClassConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.ClassCopy;
import io.github.kylinhunter.commons.bean.copy.convertor.ConvertType;
import io.github.kylinhunter.commons.bean.copy.convertor.Direction;
import io.github.kylinhunter.commons.bean.copy.convertor.FieldConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.FieldCopy;
import io.github.kylinhunter.commons.exception.inner.InitException;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Data
public class BeanCopyCache {
    public static final Map<String, BeanCopy> FIELD_COPYS = Maps.newConcurrentMap();
    public static BeanCopy DEFAULT_BEAN_COPY = new BeanCopy();

    /**
     * @param sourceClass sourceClass
     * @param targetClass targetClass
     * @return com.kylinhunter.plat.commons.utils.bean.BeanCopy
     * @title getBeanCopy
     * @description
     * @author BiJi'an
     * @date 2021/8/13 8:47 下午
     */
    public static BeanCopy getBeanCopy(Class<?> sourceClass, Class<?> targetClass) {
        String key = sourceClass.getName() + targetClass.getName();
        BeanCopy beanCopy = FIELD_COPYS.get(key);
        if (beanCopy == null) {
            synchronized(BeanCopyUtils.class) {
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
     * @return com.kylinhunter.plat.commons.bean.copy.BeanCopy
     * @title init
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:16
     */
    private static BeanCopy init(Class<?> sourceClass, Class<?> targetClass) {

        BeanCopy beanCopy = new BeanCopy();
        beanCopy.setClassConvertor(initClassConvertor(sourceClass, targetClass));
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
     * @return com.kylinhunter.plat.commons.bean.copy.convertor.ClassConvertor<java.lang.Object, java.lang.Object>
     * @title initClassConvertor
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:28
     */
    @SuppressWarnings("unchecked")
    private static ClassConvertor<Object, Object> initClassConvertor(Class<?> sourceClass, Class<?> targetClass) {
        ClassCopy classCopy = sourceClass.getAnnotation(ClassCopy.class);
        try {
            if (classCopy != null) {
                Constructor<?> constructor = classCopy.value().getConstructor(Direction.class);
                return (ClassConvertor<Object, Object>) constructor.newInstance(Direction.FORWARD);
            } else {
                classCopy = targetClass.getAnnotation(ClassCopy.class);
                if (classCopy != null) {
                    Class<?>[] targets = classCopy.targets();
                    for (Class<?> target : targets) {
                        if (target == sourceClass) {
                            Constructor<?> constructor = classCopy.value().getConstructor(Direction.class);
                            return (ClassConvertor<Object, Object>) constructor.newInstance(Direction.BACKEND);
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new InitException("init class convertor error", e);
        }
    }

    /**
     * @param sourceClass sourceClass
     * @param targetClass targetClass
     * @return com.kylinhunter.plat.commons.utils.bean.BeanCopy
     * @title initFieldConvertor
     * @description
     * @author BiJi'an
     * @date 2021/8/13 8:47 下午
     */
    @SuppressWarnings("unchecked")
    private static List<FieldConvertor> initFieldConvertor(Class<?> sourceClass, Class<?> targetClass) {
        try {
            List<FieldConvertor> fieldConvertors = Lists.newArrayList();
            Set<Field> allSourceFields = ReflectionUtils.getAllFields(sourceClass);
            Map<String, Field> targetFields = ReflectionUtils.getAllFields(targetClass).stream()
                    .collect(Collectors.toMap(Field::getName, e -> e));

            for (Field sourceField : allSourceFields) {
                String fieldName = sourceField.getName();
                Field targetField = targetFields.get(fieldName);
                if (targetField != null) {
                    FieldCopy fieldCopy = sourceField.getAnnotation(FieldCopy.class);
                    if (fieldCopy != null) {
                        ConvertType convertType = fieldCopy.value();
                        List<Class<?>> targets = Arrays.asList(fieldCopy.targets());
                        if (targets.contains(targetClass)) {
                            FieldConvertor fieldConvertor = initFieldConvertor(Direction.FORWARD,
                                    sourceClass, targetClass, fieldName, convertType);
                            if (fieldConvertor != null) {
                                fieldConvertors.add(fieldConvertor);
                            }
                        }
                    } else {

                        FieldCopy targetFieldCopy = targetField.getAnnotation(FieldCopy.class);
                        if (targetFieldCopy != null) {
                            ConvertType convertType = targetFieldCopy.value();
                            List<Class<?>> targets = Arrays.asList(targetFieldCopy.targets());
                            if (targets.contains(sourceClass)) {
                                FieldConvertor fieldConvertor = initFieldConvertor(Direction.BACKEND,
                                        sourceClass, targetClass, fieldName, convertType);
                                if (fieldConvertor != null) {
                                    fieldConvertors.add(fieldConvertor);
                                }

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
     * @param direction   direction
     * @param sourceClass sourceClass
     * @param targetClass targetClass
     * @param fieldName   fieldName
     * @param convertType convertType
     * @return com.kylinhunter.plat.commons.bean.copy.convertor.FieldConvertor
     * @title processJson
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:26
     */
    private static FieldConvertor initFieldConvertor(Direction direction, Class<?> sourceClass, Class<?> targetClass,
                                                     String fieldName, ConvertType convertType)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        PropertyDescriptor sourcePD = BeanUtils.getPropertyDescriptor(sourceClass, fieldName);
        PropertyDescriptor targetPD = BeanUtils.getPropertyDescriptor(targetClass, fieldName);
        if (sourcePD != null && targetPD != null) {
            Method read = sourcePD.getReadMethod();
            Method write = targetPD.getWriteMethod();
            if (read != null && write != null) {
                Class<? extends FieldConvertor> clazz = convertType.getClazz();
                Constructor<? extends FieldConvertor> constructor =
                        clazz.getConstructor(Direction.class, PropertyDescriptor.class, PropertyDescriptor.class);
                return constructor.newInstance(direction, sourcePD, targetPD);
            }
        }
        return null;
    }
}
