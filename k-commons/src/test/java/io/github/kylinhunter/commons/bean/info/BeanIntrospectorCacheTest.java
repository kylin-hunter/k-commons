package io.github.kylinhunter.commons.bean.info;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BeanIntrospectorCacheTest {

    @Test
    void get() {
        Grandfather bean = new Grandfather();
        Father father = new Father();
        bean.setFather(father);
        BeanIntrospector beanIntrospector = BeanIntrospectorCache.get(Grandfather.class);
        System.out.println("getPropertyDescriptors");

        final Map<String, PropertyDescriptor> propertyDescriptors = beanIntrospector.getPropertyDescriptors();
        propertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getName());
        });
        Assertions.assertEquals(14, propertyDescriptors.size());
        System.out.println("getFullPropertyDescriptor");
        beanIntrospector.getFullPropertyDescriptors().forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getName());
        });
    }

}