package io.github.kylinhunter.commons.bean.info;

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:15
 **/
public class BeanIntrospectors {
    private static final Map<Class<?>, BeanIntrospector> beanIntrospectors = MapUtils.newHashMap();

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
                    beanIntrospector = new BeanIntrospector(clazz);
                    beanIntrospectors.put(clazz, beanIntrospector);
                }
            }
        }
        return beanIntrospector;
    }

}
