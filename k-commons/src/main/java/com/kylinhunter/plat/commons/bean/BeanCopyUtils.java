package com.kylinhunter.plat.commons.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.kylinhunter.plat.commons.bean.copy.BeanCopy;
import com.kylinhunter.plat.commons.bean.copy.convertor.ClassCopy;
import com.kylinhunter.plat.commons.bean.copy.convertor.ClassCopyConvertor;
import com.kylinhunter.plat.commons.bean.copy.convertor.FieldCopy;
import com.kylinhunter.plat.commons.bean.copy.convertor.FieldCopyConvertor;
import com.kylinhunter.plat.commons.bean.copy.convertor.TargetType;
import com.kylinhunter.plat.commons.bean.copy.field.FieldCopyConvertorJson;
import com.kylinhunter.plat.commons.exception.inner.FormatException;
import com.kylinhunter.plat.commons.exception.inner.InitException;
import com.kylinhunter.plat.commons.util.ReflectionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Slf4j
public class BeanCopyUtils {
    public static final Map<String, BeanCopy> ALL_FIELD_COPYS = new ConcurrentHashMap<>();
    public static BeanCopy DEFAULT_BEAN_COPY = new BeanCopy();

    /**
     * @param source source
     * @param target target
     * @return void
     * @title 复制属性
     * @description
     * @author BiJi'an
     * @date 2021/8/13 8:52 下午
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        try {
            BeanUtils.copyProperties(source, target, ignoreProperties);
            BeanCopy beanCopy = getBeanCopy(source.getClass(), target.getClass());
            if (beanCopy != null) {
                beanCopy.copy(source, target);
            }
        } catch (Exception e) {
            throw new FormatException("copyProperties error", e);
        }
    }

    /**
     * @param sourceClass sourceClass
     * @param targetClass targetClass
     * @return com.kylinhunter.plat.commons.utils.bean.BeanCopy
     * @title 获取source的所有fieldcopy
     * @description
     * @author BiJi'an
     * @date 2021/8/13 8:47 下午
     */
    public static BeanCopy getBeanCopy(Class<?> sourceClass, Class<?> targetClass) {
        String key = sourceClass.getName() + targetClass.getName();
        BeanCopy beanCopy = ALL_FIELD_COPYS.get(key);
        if (beanCopy == null) {
            synchronized(BeanCopyUtils.class) {
                beanCopy = ALL_FIELD_COPYS.get(key);
                if (beanCopy == null) {
                    beanCopy = init(sourceClass, targetClass);
                    ALL_FIELD_COPYS.put(key, beanCopy);
                }
            }
        }
        return beanCopy;
    }

    private static BeanCopy init(Class<?> sourceClass, Class<?> targetClass) {

        BeanCopy beanCopy = new BeanCopy();
        beanCopy.setClassCopyConvertor(initClassConvertor(sourceClass, targetClass));
        beanCopy.setFieldCopyConvertors(initFieldConvertor(sourceClass, targetClass));
        if (!beanCopy.isEmpty()) {
            return beanCopy;
        } else {
            return DEFAULT_BEAN_COPY;
        }

    }

    @SuppressWarnings("unchecked")
    private static ClassCopyConvertor<Object, Object> initClassConvertor(Class<?> sourceClass, Class<?> targetClass) {
        ClassCopy classCopy = sourceClass.getAnnotation(ClassCopy.class);
        try {
            if (classCopy != null) {
                Constructor<?> constructor = classCopy.value().getConstructor(boolean.class);
                return (ClassCopyConvertor<Object, Object>) constructor.newInstance(false);
            } else {
                classCopy = targetClass.getAnnotation(ClassCopy.class);
                if (classCopy != null) {
                    Class<?>[] targets = classCopy.targets();
                    for (Class<?> target : targets) {
                        if (target == sourceClass) {
                            Constructor<?> constructor = classCopy.value().getConstructor(boolean.class);
                            return (ClassCopyConvertor<Object, Object>) constructor.newInstance(true);
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
     * @title 初始化 filedcopy信息
     * @description
     * @author BiJi'an
     * @date 2021/8/13 8:47 下午
     */
    private static List<FieldCopyConvertor> initFieldConvertor(Class<?> sourceClass,
                                                               Class<?> targetClass) {
        List<FieldCopyConvertor> fieldCopyConvertors = Lists.newArrayList();

        Map<String, Field> sourceFields = ReflectionUtil.getAllDeclaredFields(sourceClass, true);
        Map<String, Field> targetFields = ReflectionUtil.getAllDeclaredFields(targetClass, true);

        sourceFields.forEach((fieldName, sourceField) -> {
            Field targetField = targetFields.get(fieldName);
            if (targetField != null) {
                FieldCopy fieldCopy = sourceField.getAnnotation(FieldCopy.class);
                if (fieldCopy != null) {
                    TargetType targetType = fieldCopy.value();
                    List<Class<?>> targets = Arrays.asList(fieldCopy.targets());
                    if (targetType == TargetType.JSON && targets.contains(targetClass)) {
                        processJson(false, sourceClass, targetClass, sourceField,
                                targetField, fieldName, fieldCopyConvertors);
                    }
                } else {

                    FieldCopy targetFieldCopy = targetField.getAnnotation(FieldCopy.class);
                    if (targetFieldCopy != null) {
                        TargetType targetType = targetFieldCopy.value();
                        List<Class<?>> targets = Arrays.asList(targetFieldCopy.targets());
                        if (targetType == TargetType.JSON && targets.contains(sourceClass)) {

                            processJson(true, sourceClass, targetClass, sourceField,
                                    targetField, fieldName, fieldCopyConvertors);

                        }
                    }

                }
            }

        });
        return fieldCopyConvertors;

    }

    private static void processJson(boolean reverse, Class<?> sourceClass,
                                    Class<?> targetClass,
                                    Field sourceField, Field targetField,
                                    String sourceFieldName,
                                    List<FieldCopyConvertor> fieldCopyConvertors) {
        PropertyDescriptor sourcePD = BeanUtils.getPropertyDescriptor(sourceClass, sourceFieldName);
        PropertyDescriptor targetPD = BeanUtils.getPropertyDescriptor(targetClass, sourceFieldName);
        if (sourcePD != null && targetPD != null) {
            Method read = sourcePD.getReadMethod();
            Method write = targetPD.getWriteMethod();
            if (read != null && write != null) {
                fieldCopyConvertors.add(new FieldCopyConvertorJson(reverse, read, write, sourceField, targetField));
            }
        }
    }

}
