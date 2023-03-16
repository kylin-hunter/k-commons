package io.github.kylinhunter.commons.bean.info;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

import io.github.kylinhunter.commons.collections.MapUtils;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-18 01:15
 **/
public class BeanIntrospectorCache {

    private static final Map<Class<?>, BeanIntrospector> beanIntrospectors = MapUtils.newHashMap();

    /**
     * @param clazz clazz
     * @return List<PropertyDescriptor>
     * @title init
     * @description
     * @author BiJi'an
     * @date 2023-02-18 01:22
     */
    private static BeanIntrospector init(Class<?> clazz) {
        try {
            BeanIntrospector beanIntrospector = new BeanIntrospector();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            beanIntrospector.setBeanInfo(beanInfo);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    beanIntrospector.put(propertyDescriptor.getName(), propertyDescriptor);

                }
            }
            return beanIntrospector;

        } catch (Exception e) {
            throw new InitException("init error", e);
        }
    }

    /**
     * @param clazz clazz
     * @return java.util.List<java.beans.PropertyDescriptor>
     * @title get
     * @description
     * @author BiJi'an
     * @date 2023-02-18 01:21
     */
    public static BeanIntrospector get(Class<?> clazz) {
        BeanIntrospector beanIntrospector = beanIntrospectors.get(clazz);
        if (beanIntrospector == null) {
            synchronized(BeanIntrospectorCache.class) {
                beanIntrospector = beanIntrospectors.get(clazz);
                if (beanIntrospector == null) {
                    beanIntrospector = init(clazz);
                    beanIntrospectors.put(clazz, beanIntrospector);
                }
            }
        }
        return beanIntrospector;
    }
}
