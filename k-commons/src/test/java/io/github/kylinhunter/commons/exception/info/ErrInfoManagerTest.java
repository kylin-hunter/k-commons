package io.github.kylinhunter.commons.exception.info;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ErrInfoManagerTest {

  @Test
  void test() {
    ErrInfoManager.println();

    Assertions.assertEquals("UNKNOWN", ErrInfoManager.getDefaultMsg(-1));
    Assertions.assertEquals("FORMAT", ErrInfoManager.getDefaultMsg(1001));
    Assertions.assertEquals("INIT", ErrInfoManager.getDefaultMsg(1002));
    Assertions.assertEquals("INTERNAL", ErrInfoManager.getDefaultMsg(1003));
    Assertions.assertEquals("IO", ErrInfoManager.getDefaultMsg(1004));
    Assertions.assertEquals("PARAM", ErrInfoManager.getDefaultMsg(1005));
    Assertions.assertEquals("BIZ", ErrInfoManager.getDefaultMsg(1101));
  }
}
