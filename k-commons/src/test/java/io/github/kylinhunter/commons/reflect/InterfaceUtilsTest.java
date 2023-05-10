package io.github.kylinhunter.commons.reflect;

import static org.junit.jupiter.api.Assertions.*;

import io.github.kylinhunter.commons.reflect.test.ReflectBeanChild;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;

class InterfaceUtilsTest {

  @Test
  void test() {
    System.out.println("#### getSuperTypes");
    Collection<Class<?>> result11 = ReflectionUtils.getSuperTypes(ReflectBeanChild.class).stream()
        .filter(e -> e.isInterface()).collect(
            Collectors.toSet());
    result11.forEach(System.out::println);

    System.out.println("#### get");
    Collection<Class<?>> result12 = InterfaceUtils.get(ReflectBeanChild.class);
    result12.forEach(System.out::println);

    Assertions.assertEquals(result11.size(), result12.size());

    System.out.println("####getAllSuperTypes");
    Collection<Class<?>> result21 = ReflectionUtils.getAllSuperTypes(ReflectBeanChild.class)
        .stream()
        .filter(e -> e.isInterface()).collect(
            Collectors.toSet());
    result21.forEach(System.out::println);

    System.out.println("####12");
    Collection<Class<?>> result22 = InterfaceUtils.getAll(ReflectBeanChild.class);
    result22.forEach(System.out::println);

    Assertions.assertEquals(result21.size(), result22.size());
  }
}