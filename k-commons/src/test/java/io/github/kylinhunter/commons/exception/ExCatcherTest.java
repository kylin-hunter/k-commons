package io.github.kylinhunter.commons.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExCatcherTest {

  @Test
  void run() {

    int a = ExCatcher.run(() -> 1);
    Assertions.assertEquals(1, a);

    Assertions.assertThrows(
        RuntimeException.class,
        () ->
            ExCatcher.run(
                () -> {
                  throw new Exception("111");
                }));
  }
}
