package io.github.kylinhunter.commons.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.apache.commons.lang3.ClassUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassUtilTest {

  @Test
  void loadClass() {
    Class<Object> objectClass = ClassUtil.loadClass("io.github.kylinhunter.commons.sys.KConst");
    Assertions.assertNotNull(objectClass);
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          ClassUtil.loadClass("a.a");
        });
  }

  @Test
  void test() {

    System.out.println("输出类对象");
    Class<?> clazz = ArrayList.class;
    test(clazz);

    System.out.println("输出数组类型");
    clazz = ArrayList[].class;
    test(clazz);

    System.out.println("匿名内部类");
    clazz = new Object() {}.getClass();
    test(clazz);

    System.out.println("输出内部类");
    clazz = AAA.class;
    test(clazz);

    System.out.println("输出普通类");
    clazz = ClassUtilTest.class;
    test(clazz);

    // 基本数据类型
    System.out.println("基本数据类型");
    clazz = int.class;
    test(clazz);
  }

  private void test(Class<?> clazz) {
    System.out.println("class.getName()=>\t" + clazz.getName());
    System.out.println("class.getCanonicalName()=>\t" + clazz.getCanonicalName());
    System.out.println("class.getSimpleName()=>\t" + clazz.getSimpleName());
    System.out.println("getShortClassName()=>\t" + ClassUtil.getShortClassName(clazz));
    System.out.println("getPackageName()=>\t" + ClassUtil.getPackageName(clazz));

    assertEquals(ClassUtil.getShortClassName(clazz), ClassUtils.getShortClassName(clazz));
    assertEquals(ClassUtil.getPackageName(clazz), ClassUtils.getPackageName(clazz));
  }

  static class AAA {}
}
