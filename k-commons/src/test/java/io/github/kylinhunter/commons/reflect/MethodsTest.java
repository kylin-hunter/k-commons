package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.reflect.test.ReflectBeanChild;
import java.lang.reflect.Method;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;

class MethodsTest {

  @Test
  void test() {
    System.out.println("#### getSuperTypes");
    Set<Method> result11 = ReflectionUtils.getMethods(ReflectBeanChild.class);
    result11.forEach(System.out::println);

    System.out.println("#### get1");
    Set<Method> result12 = Methods.get(ReflectBeanChild.class);
    result12.forEach(System.out::println);
    Assertions.assertEquals(result11.size(), result12.size());

    System.out.println("#### get + predicate ");
    Set<Method> result13 = Methods.get(ReflectBeanChild.class, e -> e.getName().equals("getF1"));
    result13.forEach(System.out::println);

    Assertions.assertEquals(1, result13.size());

    System.out.println("####getAllSuperTypes");
    Set<Method> result21 = ReflectionUtils.getAllMethods(ReflectBeanChild.class);
    result21.forEach(System.out::println);

    System.out.println("####getAll");
    Set<Method> result22 = Methods.getAll(ReflectBeanChild.class);
    result22.forEach(System.out::println);

    Assertions.assertEquals(result21.size(), result22.size());

    System.out.println("####getAll2");
    Set<Method> result23 = Methods.getAll(ReflectBeanChild.class, e -> !e.getName().equals("getC1"),
            e -> !e.getName().equals("getC2"));
    result23.forEach(System.out::println);

    Assertions.assertEquals(result21.size() - 2, result23.size());
  }
}