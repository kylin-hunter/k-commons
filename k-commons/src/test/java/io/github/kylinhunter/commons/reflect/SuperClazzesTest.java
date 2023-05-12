package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.reflect.test.ReflectBeanChild;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;

class SuperClazzesTest {

  @SuppressWarnings("unchecked")
  @Test
  void test() {

    System.out.println("#### getSuperTypes1");
    Collection<Class<?>> result11 = ReflectionUtils.getSuperTypes(ReflectBeanChild.class).stream()
        .filter(e -> !e.isInterface()).collect(
            Collectors.toSet());
    result11.forEach(System.out::println);

    System.out.println("#### get");
    Class<?> result12 = SuperClazzes.get(ReflectBeanChild.class);
    System.out.println(result12);
    Assertions.assertEquals(1, result11.size());
    Assertions.assertNotNull(result12);

    System.out.println("####getAllSuperTypes1");
    Collection<Class<?>> result21 = ReflectionUtils.getAllSuperTypes(ReflectBeanChild.class)
        .stream()
        .filter(e -> !e.isInterface()).collect(
            Collectors.toSet());
    result21.forEach(System.out::println);

    System.out.println("####getAll");
    Collection<Class<?>> result22 = SuperClazzes.getAll(ReflectBeanChild.class);
    result22.forEach(System.out::println);

    Assertions.assertEquals(result21.size(), result22.size());

    System.out.println("####getAllSuperTypes2");
    Collection<Class<?>> result31 = ReflectionUtils.getAllSuperTypes(ReflectBeanChild.class)
        .stream()
        .filter(e -> !e.isInterface())
        .filter(e -> !e.getSimpleName().equals("ReflectBeanGrandFather"))
        .filter(e -> !e.getSimpleName().equals("ReflectBeanFather")).collect(Collectors.toSet());
    result31.forEach(System.out::println);

    System.out.println("####getAll");
    Collection<Class<?>> result32 = SuperClazzes
        .getAll(ReflectBeanChild.class, e -> !e.getSimpleName().equals("ReflectBeanGrandFather"),
            e -> !e.getSimpleName().equals("ReflectBeanFather"));
    result32.forEach(System.out::println);

    Assertions.assertEquals(result31.size(), result32.size());
    Assertions.assertEquals(0, result32.size());

  }


}