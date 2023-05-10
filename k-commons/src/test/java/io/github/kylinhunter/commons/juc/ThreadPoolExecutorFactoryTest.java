package io.github.kylinhunter.commons.juc;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThreadPoolExecutorFactoryTest {

  @Test
  void test() {
    ThreadPoolExecutorFactory.register("a", 1, 2, 1);

    Assertions.assertThrows(RuntimeException.class, () -> {
      ThreadPoolExecutorFactory.get("a1");
    });

    ThreadPoolExecutor service = ThreadPoolExecutorFactory.get("a");
    Assertions.assertNotNull(service);

    List<Runnable> runnables = ThreadPoolExecutorFactory.shutdownNow("a");
    Assertions.assertTrue(runnables.isEmpty());


  }
}