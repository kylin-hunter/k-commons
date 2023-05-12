package io.github.kylinhunter.commons.reflect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassUtilTest {

  @Test
  void loadClass() {
    Class<Object> objectClass = ClassUtil.loadClass("io.github.kylinhunter.commons.sys.KConst");
    Assertions.assertNotNull(objectClass);
  }
}
