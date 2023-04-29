package io.github.kylinhunter.commons.exception.info;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ErrInfoManagerTest {

  @Test
  void test() {
    ErrInfoManager.println();

    Assertions.assertEquals("UNKNOWN", ErrInfoManager.getDefaultMsg(-1));
    Assertions.assertEquals("FORMAT", ErrInfoManager.getDefaultMsg(10001));
    Assertions.assertEquals("INIT", ErrInfoManager.getDefaultMsg(10002));
    Assertions.assertEquals("INTERNAL", ErrInfoManager.getDefaultMsg(10003));
    Assertions.assertEquals("IO", ErrInfoManager.getDefaultMsg(10004));
    Assertions.assertEquals("PARAM", ErrInfoManager.getDefaultMsg(10005));
    Assertions.assertEquals("BIZ", ErrInfoManager.getDefaultMsg(20001));
    Assertions.assertEquals("DB", ErrInfoManager.getDefaultMsg(20002));
    Assertions.assertEquals("DB_NO_EXIST", ErrInfoManager.getDefaultMsg(20003));
  }
}
