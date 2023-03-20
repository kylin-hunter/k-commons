package io.github.kylinhunter.commons.bean.info;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BeanIntrospectorsTest {

    @Test
    void get() {
        BeanIntrospector beanIntrospector = BeanIntrospectors.get(Grandfather.class);
        System.out.println("getPropertyDescriptors=Grandfather");

        final Map<String, PropertyDescriptor> propertyDescriptors = beanIntrospector.getPropertyDescriptors();
        propertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getName());
        });
        Assertions.assertEquals(17, propertyDescriptors.size());

        System.out.println("getFullPropertyDescriptor=Grandfather");
        Map<String, PropertyDescriptor> fullPropertyDescriptors = beanIntrospector.getFullPropertyDescriptors();
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(87, fullPropertyDescriptors.size());

        fullPropertyDescriptors = BeanIntrospectors.get(Father.class).getFullPropertyDescriptors();
        System.out.println("getFullPropertyDescriptor=Father");
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(141, fullPropertyDescriptors.size());

        fullPropertyDescriptors = BeanIntrospectors.get(Son.class).getFullPropertyDescriptors();
        System.out.println("getFullPropertyDescriptor=Son");
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(87, fullPropertyDescriptors.size());

    }

}