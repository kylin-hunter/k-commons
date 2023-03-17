package io.github.kylinhunter.commons.bean.info;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BeanIntrospectorCacheTest {

    @Test
    void get() {
        BeanIntrospector beanIntrospector = BeanIntrospectorCache.get(Grandfather.class);
        System.out.println("getPropertyDescriptors=Grandfather");

        final Map<String, PropertyDescriptor> propertyDescriptors = beanIntrospector.getPropertyDescriptors();
        propertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getName());
        });
        Assertions.assertEquals(16, propertyDescriptors.size());

        System.out.println("getFullPropertyDescriptor=Grandfather");
        Map<String, PropertyDescriptor> fullPropertyDescriptors = beanIntrospector.getFullPropertyDescriptors();
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(84, fullPropertyDescriptors.size());

        fullPropertyDescriptors = BeanIntrospectorCache.get(Father.class).getFullPropertyDescriptors();
        System.out.println("getFullPropertyDescriptor=Father");
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(136, fullPropertyDescriptors.size());

        fullPropertyDescriptors = BeanIntrospectorCache.get(Son.class).getFullPropertyDescriptors();
        System.out.println("getFullPropertyDescriptor=Son");
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(85, fullPropertyDescriptors.size());

    }

}