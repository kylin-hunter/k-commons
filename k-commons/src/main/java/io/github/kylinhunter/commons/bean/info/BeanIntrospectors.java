package io.github.kylinhunter.commons.bean.info;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:15
 **/
public class BeanIntrospectors {
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
                ExPropertyDescriptor exPropertyDescriptor = BeanInfoHelper.getExFieldDescriptor(propertyDescriptor);
                beanIntrospector.addPropertyDescriptor(propertyDescriptor.getName(), exPropertyDescriptor);
            }
        }
    }

    /**
     * @param clazz clazz
     * @return java.util.List<PropertyDescriptor>
     * @title get
     * @description
     * @author BiJi'an
     * @date 2023-02-19 01:21
     */
    public static BeanIntrospector get(Class<?> clazz) {
        BeanIntrospector beanIntrospector = beanIntrospectors.get(clazz);
        if (beanIntrospector == null) {
            synchronized(BeanIntrospectors.class) {
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
