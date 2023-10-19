package io.github.kylinhunter.commons.log.jul;

import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JULManagerTest {

  @Test
  void init() {
    Assertions.assertTrue(JULManager.init(false));
    Logger logger = Logger.getLogger("111");
    logger.info("test");

    JULManager.reset();
    Assertions.assertFalse(JULManager.init("error.properties", false));
    logger.info("test");

    JULManager.reset();
    Assertions.assertTrue(JULManager.init("k-jul-logging-with-slf4j.properties", false));
    logger.info("test");

    JULManager.reset();
    Assertions.assertTrue(JULManager.init("", true));
    logger.info("test");
  }
}
