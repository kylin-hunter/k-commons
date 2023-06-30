package io.github.kylinhunter.commons.log.jul;

import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JULManagerTest {

  @Test
  void init() {
    Assertions.assertTrue(JULManager.init());
    ;
    Logger logger = Logger.getLogger("111");
    logger.info("test");

    JULManager.setConfigFile("aaa.properties");
    Assertions.assertFalse(JULManager.init());
    ;
    logger = Logger.getLogger("111");
    logger.info("test");
  }
}
