package io.github.kylinhunter.commons.bean.info;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Map;

import io.github.kylinhunter.commons.collections.MapUtils;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-18 01:39
 **/
@Data
public class BeanIntrospector {
    private BeanInfo beanInfo;
    private Map<String, PropertyDescriptor> propertyDescriptors = MapUtils.newHashMap();

    /**
     * @param name               name
     * @param propertyDescriptor propertyDescriptor
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-02-18 01:53
     */
    public void put(String name, PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptors.put(name, propertyDescriptor);
    }

    /**
     * @param name name
     * @return java.beans.PropertyDescriptor
     * @title getPropertyDescriptor
     * @description
     * @author BiJi'an
     * @date 2023-02-18 02:22
     */
    public PropertyDescriptor getPropertyDescriptor(String name) {
        return propertyDescriptors.get(name);
    }
}
