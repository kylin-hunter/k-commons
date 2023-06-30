package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.reflect.test.TestClass;
import org.junit.jupiter.api.Test;

class ObjectCreatorTest {

  @Test
  void createBean() {
    TestClass bean =
        ObjectCreator.create(
            TestClass.class, new Class[] {int.class, int.class}, new Object[] {11, 22});
    bean.println();
  }
}
