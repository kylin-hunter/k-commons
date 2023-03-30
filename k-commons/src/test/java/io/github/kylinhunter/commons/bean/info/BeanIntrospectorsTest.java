package io.github.kylinhunter.commons.bean.info;

import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        propertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getName());
        });
        Assertions.assertEquals(17, propertyDescriptors.size());

        System.out.println("getFullPropertyDescriptor=Grandfather");
        Map<String, ExPropertyDescriptor> fullPropertyDescriptors = beanIntrospector.getFullExPropertyDescriptors();
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(159, fullPropertyDescriptors.size());

        fullPropertyDescriptors = BeanIntrospectors.get(Father.class).getFullExPropertyDescriptors();
        System.out.println("getFullPropertyDescriptor=Father");
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(605, fullPropertyDescriptors.size());

        fullPropertyDescriptors = BeanIntrospectors.get(Son.class).getFullExPropertyDescriptors();
        System.out.println("getFullPropertyDescriptor=Son");
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(159, fullPropertyDescriptors.size());

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

        propertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });

        Assertions.assertEquals(2, propertyDescriptors.size());

        System.out.println("getFullPropertyDescriptor=Grandfather");
        Map<String, ExPropertyDescriptor> fullPropertyDescriptors = beanIntrospector.getFullExPropertyDescriptors();
        fullPropertyDescriptors.forEach((k, v) -> {
            System.out.println(k + ":" + v.getReadMethod().getReturnType().getSimpleName());
        });
        Assertions.assertEquals(15, fullPropertyDescriptors.size());

    }

}