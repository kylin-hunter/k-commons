package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.reflect.test.ReflectBeanChild;
import io.github.kylinhunter.commons.reflect.test.TestClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectUtilsTest {

  @Test
  void invoke() throws NoSuchMethodException {
    TestClass testClass = new TestClass(1, 2);
    ReflectUtils.invoke(testClass, testClass.getClass().getMethod("setA", int.class), 11);
    int a = ReflectUtils.invoke(testClass, testClass.getClass().getMethod("getA"));
    Assertions.assertEquals(11, a);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          ReflectUtils.invoke(testClass, testClass.getClass().getMethod("setA", int.class), "1");
        });
  }

  @Test
  void tesMethods() {

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

  @Test
  void testFields() {

    System.out.println("#### get1");
    Set<Field> result12 = ReflectUtils.getFields(ReflectBeanChild.class);
    result12.forEach(System.out::println);
    Assertions.assertTrue(result12.size() == 3);

    System.out.println("#### get + predicate ");
    Set<Field> result13 =
        ReflectUtils.getFields(ReflectBeanChild.class, e -> e.getName().equals("c2"));
    result13.forEach(System.out::println);

    Assertions.assertEquals(1, result13.size());

    System.out.println("####getAll");
    Set<Field> result22 = ReflectUtils.getAllFields(ReflectBeanChild.class);
    result22.forEach(System.out::println);

    Assertions.assertTrue(result22.size() == 7);

    System.out.println("####getAll + predicate ");
    Set<Field> result23 =
        ReflectUtils.getAllFields(
            ReflectBeanChild.class,
            e -> !e.getName().equals("c2"),
            e -> !e.getName().equals("gf2"));
    result23.forEach(System.out::println);

    Assertions.assertTrue(result23.size() == 5);
  }

  @Test
  void tesetSetField() {

    ReflectBeanChild obj = new ReflectBeanChild();
    Assertions.assertEquals(0, obj.getC1());

    Optional<Field> c1Field = ReflectUtils.getAllFields(ReflectBeanChild.class).stream()
        .filter(e -> e.getName().equals(
            "c1")).findFirst();

    ReflectUtils.setField(obj, c1Field.get(), 2);
    Assertions.assertEquals(2, obj.getC1());
    ReflectUtils.setField(obj, "c1", 3);
    Assertions.assertEquals(3, obj.getC1());

  }
}
