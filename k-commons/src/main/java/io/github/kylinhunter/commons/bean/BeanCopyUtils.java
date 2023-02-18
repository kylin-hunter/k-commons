package io.github.kylinhunter.commons.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import io.github.kylinhunter.commons.bean.copy.BeanCopy;
import io.github.kylinhunter.commons.bean.copy.BeanCopyCache;
import io.github.kylinhunter.commons.bean.info.BeanIntrospector;
import io.github.kylinhunter.commons.bean.info.BeanIntrospectorCache;
import io.github.kylinhunter.commons.exception.embed.FormatException;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Slf4j
public class BeanCopyUtils {

    /**
     * @param source           the source bean
     * @param target           the target bean
     * @param ignoreProperties array of property names to ignore
     * @return void
     * @title copyProperties
     * @description Copy the property values of the given source bean into the given target bean
     * @author BiJi'an
     * @date 2021/8/13 8:52 下午
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        try {
            copy(source, target, ignoreProperties);
            BeanCopy beanCopy = BeanCopyCache.getBeanCopy(source.getClass(), target.getClass());
            if (beanCopy != null) {
                beanCopy.copy(source, target);
            }
        } catch (Exception e) {
            throw new FormatException("copyProperties error", e);
        }
    }

    /**
     * @param source           source
     * @param target           target
     * @param ignoreProperties ignoreProperties
     * @return void
     * @title copy
     * @description
     * @author BiJi'an
     * @date 2023-02-18 02:14
     */
    private static void copy(Object source, Object target, String... ignoreProperties) {

        BeanIntrospector sourceBeanIntrospector = BeanIntrospectorCache.get(source.getClass());
        Map<String, PropertyDescriptor> sourcePds = sourceBeanIntrospector.getPropertyDescriptors();

        BeanIntrospector targetBeanIntrospector = BeanIntrospectorCache.get(target.getClass());
        Map<String, PropertyDescriptor> targetPds = targetBeanIntrospector.getPropertyDescriptors();

        sourcePds.forEach((name, pdSource) -> {
            if (!isIgnoreProperties(name, ignoreProperties)) {
                PropertyDescriptor pdTarget = targetPds.get(name);
                if (pdTarget != null) {
                    Method readMethod = pdSource.getReadMethod();
                    Method writeMethod = pdTarget.getWriteMethod();
                    if (readMethod != null && writeMethod != null
                            && writeMethod.getParameterCount() == 1 && writeMethod
                            .getParameterTypes()[0]
                            .isAssignableFrom(readMethod.getReturnType())) {
                        Object readObj = ReflectUtils.invoke(source, readMethod);
                        ReflectUtils.invoke(target, writeMethod, readObj);

                    }
                }
            }
        });
    }

    /**
     * @param name             name
     * @param ignoreProperties ignoreProperties
     * @return boolean
     * @title isIgnoreProperties
     * @description
     * @author BiJi'an
     * @date 2023-02-18 02:04
     */
    private static boolean isIgnoreProperties(String name, String... ignoreProperties) {
        if (ignoreProperties.length <= 0) {
            return false;
        }
        for (String ignoreProperty : ignoreProperties) {
            if (name.equals(ignoreProperty)) {
                return true;
            }
        }
        return false;
    }

}
