package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.reflect.test.ReflectBeanChild;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;

class InterfacesTest {

  @Test
  void test() {
    System.out.println("#### getSuperTypes");
    Collection<Class<?>> result11 =
        ReflectionUtils.getSuperTypes(ReflectBeanChild.class).stream()
            .filter(e -> e.isInterface())
            .collect(Collectors.toSet());
    result11.forEach(System.out::println);

    System.out.println("#### get1");
    Collection<Class<?>> result12 = Interfaces.get(ReflectBeanChild.class);
    result12.forEach(System.out::println);

    Assertions.assertEquals(result11.size(), result12.size());
    System.out.println("#### get2");
    Collection<Class<?>> result13 =
        Interfaces.get(ReflectBeanChild.class, e -> e.getSimpleName().equals("ReflectInterface3"));
    result13.forEach(System.out::println);

    Assertions.assertEquals(1, result13.size());

    System.out.println("####getAllSuperTypes");
    Collection<Class<?>> result21 =
        ReflectionUtils.getAllSuperTypes(ReflectBeanChild.class).stream()
            .filter(e -> e.isInterface())
            .collect(Collectors.toSet());
    result21.forEach(System.out::println);

    System.out.println("####getAll");
    Collection<Class<?>> result22 = Interfaces.getAll(ReflectBeanChild.class);
    result22.forEach(System.out::println);

    Assertions.assertEquals(result21.size(), result22.size());

    System.out.println("####getAll2");
    Collection<Class<?>> result23 =
        Interfaces.getAll(
            ReflectBeanChild.class,
            e -> !e.getSimpleName().equals("ReflectInterface1"),
            e -> !e.getSimpleName().equals("ReflectInterface2"));
    result23.forEach(System.out::println);

    Assertions.assertEquals(2, result23.size());
  }
}
