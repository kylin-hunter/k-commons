package io.github.kylinhunter.commons.juc;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScheduledExecutorFactoryTest {

  @Test
  void test() {
    ScheduledExecutorFactory.register("a", 1);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          ScheduledExecutorFactory.get("a1");
        });

    ScheduledExecutorService service = ScheduledExecutorFactory.get("a");
    Assertions.assertNotNull(service);
    List<Runnable> runnables = ScheduledExecutorFactory.shutdownNow("a");
    Assertions.assertTrue(runnables.isEmpty());
  }
}
