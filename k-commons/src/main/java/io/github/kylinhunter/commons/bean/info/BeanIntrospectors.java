package io.github.kylinhunter.commons.bean.info;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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
                initFullPropertyDescriptor(beanIntrospector, new DuplicateRemover(), 1, "", exPropertyDescriptor);
            }
        }
    }

    /**
     * @param beanIntrospector beanIntrospector
     * @param propertyAccessor propertyAccessor
     * @param level            level
     * @param parent           parent
     * @param exPd             exPd
     * @return void
     * @title initFullPropertyDescriptor
     * @description
     * @author BiJi'an
     * @date 2023-04-01 10:52
     */
    private static void initFullPropertyDescriptor(BeanIntrospector beanIntrospector, DuplicateRemover propertyAccessor,
                                                   int level, String parent,
                                                   ExPropertyDescriptor exPd) throws IntrospectionException {

        if (!exPd.isCanReadWrite()) {
            return;
        }
        if (!StringUtils.isEmpty(parent)) {
            parent = parent + ".";
        }
        String propName = exPd.getName();
        ExPropType type = exPd.getExPropType();
        beanIntrospector.addFullPropertyDescriptor(parent + propName, exPd);

        // add sub type
        if (type == ExPropType.CUSTOM) {
            if (!propertyAccessor.duplicate(level, exPd)) {
                BeanInfo beanInfo = Introspector.getBeanInfo(exPd.getPropertyType());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                    parent = parent + propName;
                    for (PropertyDescriptor descriptor : propertyDescriptors) {
                        ExPropertyDescriptor exPdSub = BeanInfoHelper.getExFieldDescriptor(descriptor);
                        initFullPropertyDescriptor(beanIntrospector, propertyAccessor, level + 1, parent, exPdSub);

                    }
                }
            }
        } else if (type == ExPropType.ARRAY || type == ExPropType.LIST) {
            Class<?> propActualClazz = exPd.getPropActualClazz();
            if (propActualClazz != null) {
                BeanInfo beanInfo = Introspector.getBeanInfo(propActualClazz);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                    parent = parent + propName ;
                    for (PropertyDescriptor descriptor : propertyDescriptors) {
                        ExPropertyDescriptor exPdSub = BeanInfoHelper.getExFieldDescriptor(descriptor);

                        initFullPropertyDescriptor(beanIntrospector, propertyAccessor, level + 1, parent,
                                exPdSub);
                    }
                }
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
