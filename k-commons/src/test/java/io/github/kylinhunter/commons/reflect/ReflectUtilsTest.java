package io.github.kylinhunter.commons.reflect;

import java.lang.reflect.Method;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;

class ReflectUtilsTest {

  @Test
  void test() {
    System.out.println("####1");
    Set<Method> methods1 = ReflectionUtils.getMethods(ReflectBean.class);
    methods1.forEach(System.out::println);
    System.out.println("####2");
    Set<Method> methods2=ReflectionUtils.getAllMethods(ReflectBean.class);
    methods2.forEach(System.out::println);

  }
}