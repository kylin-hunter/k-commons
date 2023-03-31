package io.github.kylinhunter.commons.bean.info;

import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.bean.info.complex.Grandfather;
import io.github.kylinhunter.commons.bean.info.simple.SimpleGrandfather;

class BeanIntrospectorsTest {

    @Test
    void get() {
        BeanIntrospector beanIntrospector = BeanIntrospectors.get(Grandfather.class);
        System.out.println("getPropertyDescriptors=Grandfather");

        Map<String, ExPropertyDescriptor> propertyDescriptors = beanIntrospector.getExPropertyDescriptors();
        propertyDescriptors =
                propertyDescriptors.entrySet().stream().filter((e) -> e.getValue().isCanReadWrite())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue));
        propertyDescriptors.forEach((k, v) -> System.out.println(k + ":" + v.getReadMethod().getName()));
        Assertions.assertEquals(17, propertyDescriptors.size());


    }

    @Test
    public void testArrayAndList() {
        BeanIntrospector beanIntrospector = BeanIntrospectors.get(SimpleGrandfather.class);
        System.out.println("getPropertyDescriptors=Grandfather");

        Map<String, ExPropertyDescriptor> propertyDescriptors = beanIntrospector.getExPropertyDescriptors();
        propertyDescriptors =
                propertyDescriptors.entrySet().stream().filter((e) -> e.getValue().isCanReadWrite())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue));

        propertyDescriptors.forEach((k, v) -> System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName()));

        Assertions.assertEquals(2, propertyDescriptors.size());


    }

}