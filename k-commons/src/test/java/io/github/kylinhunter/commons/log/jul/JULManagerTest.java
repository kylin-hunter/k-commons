package io.github.kylinhunter.commons.log.jul;

import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JULManagerTest {

  @Test
  void init() {
    Assertions.assertTrue(JULManager.init());
    Logger logger = Logger.getLogger("111");
    logger.info("test");

    Assertions.assertFalse(JULManager.init("aaa.properties"));

    logger = Logger.getLogger("111");
    logger.info("test");
  }
}
