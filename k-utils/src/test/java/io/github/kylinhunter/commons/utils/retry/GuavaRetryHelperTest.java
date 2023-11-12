package io.github.kylinhunter.commons.utils.retry;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import io.github.kylinhunter.commons.utils.exception.ToRetryException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GuavaRetryHelperTest {

  @Test
  void test1() {
    Retryer<?> defaultRetriever = RetryerBuilder.<Boolean>newBuilder()
        .retryIfExceptionOfType(ToRetryException.class)
        .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.MILLISECONDS))
        .withStopStrategy(StopStrategies.stopAfterAttempt(2))
        .build();
    GuavaRetryHelper.defaultRetryer(defaultRetriever);
    RetryDemoTask retryDemoTask = new RetryDemoTask();
    Boolean result = GuavaRetryHelper.retry(() -> retryDemoTask.retryTask("success"), false);
    Assertions.assertTrue(result);

    result = GuavaRetryHelper.retry(() -> retryDemoTask.retryTask("exception_ingnore"), false);
    Assertions.assertFalse(result);

    result = GuavaRetryHelper.retry(() -> retryDemoTask.retryTask("exception_always"), false);
    Assertions.assertFalse(result);

    result = GuavaRetryHelper.retry(() -> retryDemoTask.retryTask("failed_1"), false);
    Assertions.assertTrue(result);

    result = GuavaRetryHelper.retry(() -> retryDemoTask.retryTask("failed_2"), false);
    Assertions.assertFalse(result);


  }

  @Test
  void test2() {
    Retryer<?> defaultRetriever = RetryerBuilder.<Boolean>newBuilder()
        .retryIfExceptionOfType(ToRetryException.class)
        .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.MILLISECONDS))
        .withStopStrategy(StopStrategies.stopAfterAttempt(3))
        .build();
    GuavaRetryHelper.registerRetryer(RetryType.TYPE1, defaultRetriever);

    RetryDemoTask retryDemoTask = new RetryDemoTask();
    Boolean result = GuavaRetryHelper.retry(RetryType.TYPE1,
        () -> retryDemoTask.retryTask("success"), false);
    Assertions.assertTrue(result);

    result = GuavaRetryHelper.retry(RetryType.TYPE1,
        () -> retryDemoTask.retryTask("exception_ingnore"), false);
    Assertions.assertFalse(result);

    result = GuavaRetryHelper.retry(RetryType.TYPE1,
        () -> retryDemoTask.retryTask("exception_always"), false);
    Assertions.assertFalse(result);

    result = GuavaRetryHelper.retry(RetryType.TYPE1, () -> retryDemoTask.retryTask("failed_1"),
        false);
    Assertions.assertTrue(result);

    result = GuavaRetryHelper.retry(RetryType.TYPE1, () -> retryDemoTask.retryTask("failed_2"),
        false);
    Assertions.assertTrue(result);

    result = GuavaRetryHelper.retry(RetryType.TYPE1, () -> retryDemoTask.retryTask("failed_3"),
        false);
    Assertions.assertFalse(result);

    result = GuavaRetryHelper.retry(RetryType.TYPE1, () -> retryDemoTask.retryTask("failed_4"),
        false);
    Assertions.assertFalse(result);

  }

}
