package io.github.kylinhunter.commons.bean.info;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:39
 **/
@Data
public class BeanIntrospector {
    private Class<?> clazz;
    private BeanInfo beanInfo;
    private Map<String, ExPropertyDescriptor> exPropertyDescriptors = MapUtils.newTreeMap();
    private Map<String, PropertyDescriptor> propertyDescriptors = MapUtils.newTreeMap();

    public BeanIntrospector(Class<?> clazz) {
        try {
            this.clazz = clazz;
            this.beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    this.addPropertyDescriptor(propertyDescriptor.getName(), propertyDescriptor);
                }
            }
        } catch (IntrospectionException e) {
            throw new InitException("init error", e);
        }
    }

    /**
     * @param name               name
     * @param propertyDescriptor propertyDescriptor
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-02-19 01:53
     */
    public void addPropertyDescriptor(String name, PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor != null) {
            ExPropertyDescriptor exPropertyDescriptor = new ExPropertyDescriptor(propertyDescriptor);
            this.exPropertyDescriptors.put(name, exPropertyDescriptor);
            this.propertyDescriptors.put(name, propertyDescriptor);

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
        ExPropertyDescriptor exPropertyDescriptor = exPropertyDescriptors.get(name);
        if (exPropertyDescriptor != null) {
            return exPropertyDescriptor.getPropertyDescriptor();
        }
        return null;
    }

}
