package io.github.kylinhunter.commons.bean.info;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.bean.info.complex.Grandfather;
import io.github.kylinhunter.commons.bean.info.simple.SimpleGrandfather;

class BeanIntrospectorsTest {

    @Test
    void testGet() {
        BeanIntrospector beanIntrospector = BeanIntrospectors.get(Grandfather.class);
        System.out.println("getPropertyDescriptors=Grandfather");

        Map<String, ExPropertyDescriptor> propertyDescriptors = beanIntrospector.getExPropertyDescriptors();

        propertyDescriptors.forEach(
                (k, v) -> System.out.println(k + ":" + v.getReadMethod().getName() + ":" + v.isCanReadWrite()));
        Assertions.assertEquals(18, propertyDescriptors.size());

    }

    @Test
    public void testArrayAndList() {
        BeanIntrospector beanIntrospector = BeanIntrospectors.get(SimpleGrandfather.class);
        System.out.println("getPropertyDescriptors=Grandfather");

        Map<String, ExPropertyDescriptor> propertyDescriptors = beanIntrospector.getExPropertyDescriptors();

        propertyDescriptors
                .forEach((k, v) -> System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName()));

        Assertions.assertEquals(3, propertyDescriptors.size());

    }

}