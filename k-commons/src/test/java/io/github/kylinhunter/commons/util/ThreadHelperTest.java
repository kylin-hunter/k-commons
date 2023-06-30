package io.github.kylinhunter.commons.util;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThreadHelperTest {

  @Test
  void sleep() throws InterruptedException {

    ThreadHelper.sleep(1, TimeUnit.MILLISECONDS);

    TimeUnit timeUnit = mock(TimeUnit.class);
    doThrow(InterruptedException.class).when(timeUnit).sleep(anyLong());

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          ThreadHelper.sleep(1, timeUnit);
        });
  }
}
