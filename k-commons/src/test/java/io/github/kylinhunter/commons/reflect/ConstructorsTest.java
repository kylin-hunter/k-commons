package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.reflect.test.ReflectBeanChild;
import java.lang.reflect.Constructor;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConstructorsTest {

  @Test
  void get() {
    Constructor<ReflectBeanChild> constructor = Constructors.getConstructor(ReflectBeanChild.class,
        int.class, int.class, int.class);
    Assertions.assertEquals(true, true);

    Set<Constructor<?>> constructors = Constructors.get(ReflectBeanChild.class, (e) -> true);
    constructors.forEach(System.out::println);
    Assertions.assertEquals(2, constructors.size());

    constructors = Constructors.getAll(ReflectBeanChild.class, e -> true);
    constructors.forEach(System.out::println);
    Assertions.assertEquals(6, constructors.size());
  }
}