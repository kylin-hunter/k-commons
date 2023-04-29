package io.github.kylinhunter.commons.util;

import org.junit.jupiter.api.Test;

class OnceRunnerTest {

  @Test
  void run() {
    OnceRunner.run(
        OnceRunnerTest.class,
        () -> {
          System.out.println(111);
        });
  }
}
