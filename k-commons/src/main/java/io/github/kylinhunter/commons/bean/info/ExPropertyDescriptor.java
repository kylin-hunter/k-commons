package io.github.kylinhunter.commons.bean.info;

import java.beans.PropertyDescriptor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 10:09
 **/
public class ExPropertyDescriptor {
    private PropertyDescriptor propertyDescriptor;
    private PropType propType;

    public ExPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
        Class<?> propertyType = propertyDescriptor.getPropertyType();
    }

    void valuof( Class<?> propertyType){
         String name = propertyType.getName();
    }

}
