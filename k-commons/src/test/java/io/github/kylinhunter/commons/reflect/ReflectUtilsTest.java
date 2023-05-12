package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.reflect.test.ReflectBeanChild;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectUtilsTest {

  @Test
  void test() {

    System.out.println("#### get1");
    Set<Method> result12 = ReflectUtils.getMethods(ReflectBeanChild.class);
    result12.forEach(System.out::println);
    Assertions.assertTrue(result12.size() > 0);

    System.out.println("#### get + predicate ");
    Set<Method> result13 =
        ReflectUtils.getMethods(ReflectBeanChild.class, e -> e.getName().equals("getF1"));
    result13.forEach(System.out::println);

    Assertions.assertEquals(1, result13.size());

    System.out.println("####getAll");
    Set<Method> result22 = ReflectUtils.getAllMethods(ReflectBeanChild.class);
    result22.forEach(System.out::println);

    Assertions.assertTrue(result22.size() > 0);

    System.out.println("####getAll + predicate ");
    Set<Method> result23 =
        ReflectUtils.getAllMethods(
            ReflectBeanChild.class,
            e -> !e.getName().equals("getC1"),
            e -> !e.getName().equals("getC2"));
    result23.forEach(System.out::println);

    Assertions.assertTrue(result23.size() > 0);
  }

  @Test
  void testSuperClass() {

    System.out.println("#### get");
    Class<?> result12 = ReflectUtils.getSuperClass(ReflectBeanChild.class);
    Assertions.assertNotNull(result12);

    Collection<Class<?>> result22 = ReflectUtils.getAllSuperClasses(ReflectBeanChild.class);
    result22.forEach(System.out::println);
    Assertions.assertEquals(2, result22.size());

    Collection<Class<?>> result32 =
        ReflectUtils.getAllSuperClasses(
            ReflectBeanChild.class,
            e -> !e.getSimpleName().equals("ReflectBeanGrandFather"),
            e -> !e.getSimpleName().equals("ReflectBeanFather"));
    result32.forEach(System.out::println);

    Assertions.assertEquals(0, result32.size());
  }

  @Test
  void testInterfaces() {

    System.out.println("#### get1");
    Collection<Class<?>> result12 = ReflectUtils.getInterfaces(ReflectBeanChild.class);
    result12.forEach(System.out::println);
    Assertions.assertEquals(2, result12.size());

    System.out.println("#### get2");
    Collection<Class<?>> result13 =
        ReflectUtils.getInterfaces(
            ReflectBeanChild.class, e -> e.getSimpleName().equals("ReflectInterface3"));
    result13.forEach(System.out::println);
    Assertions.assertEquals(1, result13.size());

    System.out.println("####getAll");
    Collection<Class<?>> result22 = ReflectUtils.getAllInterfaces(ReflectBeanChild.class);
    result22.forEach(System.out::println);
    Assertions.assertEquals(4, result22.size());

    System.out.println("####getAll2");
    Collection<Class<?>> result23 =
        ReflectUtils.getAllInterfaces(
            ReflectBeanChild.class,
            e -> !e.getSimpleName().equals("ReflectInterface1"),
            e -> !e.getSimpleName().equals("ReflectInterface2"));
    result23.forEach(System.out::println);
    Assertions.assertEquals(2, result23.size());
  }
}
