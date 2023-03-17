package io.github.kylinhunter.commons.bean.info;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

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
    private static final Set<String> skipProperties = SetUtils.newHashSet();

    static {
        skipProperties.add("class");
    }

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
            BeanIntrospector beanIntrospector = new BeanIntrospector(Introspector.getBeanInfo(clazz));
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
        if (!ArrayUtils.isEmpty(propertyDescriptors)) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String propName = propertyDescriptor.getName();
                if (!skipProperties.contains(propName)) {
                    if (propertyDescriptor.getWriteMethod() != null && propertyDescriptor.getReadMethod() != null) {
                        beanIntrospector.addPropertyDescriptor(propName, propertyDescriptor);
                        initPropertyDescriptor(beanIntrospector, new BeanContext(), 1, "", propertyDescriptor);
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
                                               BeanContext beanContext, int level, String parent,
                                               PropertyDescriptor propertyDescriptor) throws IntrospectionException {

        Class<?> propertyType = propertyDescriptor.getPropertyType();
        if (!StringUtils.isEmpty(parent)) {
            parent = parent + ".";
        }
        String propName = propertyDescriptor.getName();
        if (ClassUtils.isPrimitiveOrWrapper(propertyType)) {
            beanIntrospector.addFullPropertyDescriptor(parent + propName, propertyDescriptor);
        } else {
            if (propertyType == String.class) {
                beanIntrospector.addFullPropertyDescriptor(parent + propName, propertyDescriptor);
            } else {

                beanIntrospector.addFullPropertyDescriptor(parent + propName, propertyDescriptor);
                if (beanContext.accept(level, propertyType)) {
                    BeanInfo beanInfo = Introspector.getBeanInfo(propertyType);
                    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                    if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                        parent = parent + propName;
                        for (PropertyDescriptor descriptor : propertyDescriptors) {
                            if (descriptor.getWriteMethod() != null && descriptor.getReadMethod() != null) {
                                initPropertyDescriptor(beanIntrospector, beanContext, level + 1, parent, descriptor);
                            }
                        }
                    }
                }
            }
        }
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
