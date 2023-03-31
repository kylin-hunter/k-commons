package io.github.kylinhunter.commons.bean.info;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Map;

import io.github.kylinhunter.commons.collections.MapUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:39
 **/
@Data
public class BeanIntrospector {
    private final BeanInfo beanInfo;
    private Map<String, ExPropertyDescriptor> exPropertyDescriptors = MapUtils.newTreeMap();
    private Map<String, PropertyDescriptor> propertyDescriptors = MapUtils.newTreeMap();

    /**
     * @param name                 name
     * @param exPropertyDescriptor exPropertyDescriptor
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-02-19 01:53
     */
    public void addPropertyDescriptor(String name, ExPropertyDescriptor exPropertyDescriptor) {
        if (exPropertyDescriptor != null) {
            this.exPropertyDescriptors.put(name, exPropertyDescriptor);
            this.propertyDescriptors.put(name, exPropertyDescriptor.getPropertyDescriptor());
        }

    }

    /**
     * @param name name
     * @return java.beans.PropertyDescriptor
     * @title getPropertyDescriptor
     * @description
     * @author BiJi'an
     * @date 2023-02-19 02:22
     */
    public ExPropertyDescriptor getExPropertyDescriptor(String name) {
        return exPropertyDescriptors.get(name);
    }

    /**
     * @param name name
     * @return java.beans.PropertyDescriptor
     * @title getPropertyDescriptor
     * @description
     * @author BiJi'an
     * @date 2023-04-01 10:58
     */
    public PropertyDescriptor getPropertyDescriptor(String name) {
        return propertyDescriptors.get(name);

    }

}
