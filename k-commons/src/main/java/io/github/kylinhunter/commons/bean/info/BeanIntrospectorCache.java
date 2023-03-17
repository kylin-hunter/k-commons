package io.github.kylinhunter.commons.bean.info;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:15
 **/
public class BeanIntrospectorCache {

    private static final Map<Class<?>, BeanIntrospector> beanIntrospectors = MapUtils.newHashMap();

    /**
     * @param clazz clazz
     * @return List<PropertyDescriptor>
     * @title init
     * @description
     * @author BiJi'an
     * @date 2023-02-19 01:22
     */
    private static BeanIntrospector init(Class<?> clazz) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            BeanIntrospector beanIntrospector = new BeanIntrospector(beanInfo);
            initPropertyDescriptor(beanIntrospector);
            return beanIntrospector;
        } catch (Exception e) {
            throw new InitException("init error", e);
        }
    }

    /**
     * @param beanIntrospector beanIntrospector
     * @return void
     * @title initPropertyDescriptor
     * @description
     * @author BiJi'an
     * @date 2023-03-19 22:45
     */
    private static void initPropertyDescriptor(BeanIntrospector beanIntrospector) throws IntrospectionException {
        PropertyDescriptor[] propertyDescriptors = beanIntrospector.getBeanInfo().getPropertyDescriptors();
        if (propertyDescriptors != null && propertyDescriptors.length > 0) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (!propertyDescriptor.getName().equals("class")) {
                    if (propertyDescriptor.getWriteMethod() != null && propertyDescriptor.getReadMethod() != null) {
                        beanIntrospector.addPropertyDescriptor(propertyDescriptor.getName(), propertyDescriptor);
                        initPropertyDescriptor(beanIntrospector, propertyDescriptor);
                    }
                }
            }
        }
    }

    /**
     * @param beanIntrospector   beanIntrospector
     * @param propertyDescriptor propertyDescriptor
     * @return void
     * @title initPropertyDescriptor
     * @description
     * @author BiJi'an
     * @date 2023-03-19 14:17
     */
    private static void initPropertyDescriptor(BeanIntrospector beanIntrospector,
                                               PropertyDescriptor propertyDescriptor) throws IntrospectionException {
        Class<?> propertyType = propertyDescriptor.getPropertyType();
        if (ClassUtils.isPrimitiveOrWrapper(propertyType)) {
            beanIntrospector.addPropertyDescriptor(propertyDescriptor.getName(), propertyDescriptor);
        } else {
            if (propertyType == String.class) {
                beanIntrospector.addPropertyDescriptor(propertyDescriptor.getName(), propertyDescriptor);
            } else {
                if (propertyType != Class.class) {
                    BeanInfo beanInfo = Introspector.getBeanInfo(propertyType);
                    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                    if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                        for (PropertyDescriptor descriptor : propertyDescriptors) {
                            if (descriptor.getWriteMethod() != null && descriptor.getReadMethod() != null) {
                                initPropertyDescriptor(beanIntrospector, descriptor);
                            }
                        }
                    }
                }
            }

        }

    }

    private static Set<Class<?>> supportTypes = SetUtils.newHashSet();

    static {
        supportTypes.add(String.class);
    }

    /**
     * @param clazz clazz
     * @return java.util.List<java.beans.PropertyDescriptor>
     * @title get
     * @description
     * @author BiJi'an
     * @date 2023-02-19 01:21
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
