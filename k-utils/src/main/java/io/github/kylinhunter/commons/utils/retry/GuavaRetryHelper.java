/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.utils.retry;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.utils.exception.ToRetryException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-12 21:45
 */
@Slf4j
public class GuavaRetryHelper {

  private static final Map<Enum, Retryer> RETRYERS = MapUtils.newHashMap();
  private static Retryer DEFAULT_RETRYER;

  static {
    Retryer<?> defaultRetriever =
        RetryerBuilder.<Boolean>newBuilder()
            .retryIfExceptionOfType(ToRetryException.class)
            .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
            .withStopStrategy(StopStrategies.stopAfterAttempt(2))
            .build();
    DEFAULT_RETRYER = defaultRetriever;
  }

  /**
   * @param type type
   * @param retryer retryer
   * @return void
   * @title registerRetryer
   * @description registerRetryer
   * @author BiJi'an
   * @date 2023-11-12 23:45
   */
  public static <E extends Enum<E>> void registerRetryer(Enum<E> type, Retryer retryer) {
    RETRYERS.put(type, retryer);
  }

  /**
   * @param retryer retryer
   * @return void
   * @title defaultRetryer
   * @description defaultRetryer
   * @author BiJi'an
   * @date 2023-11-12 23:46
   */
  public static void defaultRetryer(Retryer retryer) {
    DEFAULT_RETRYER = retryer;
  }

  /**
   * @param callable callable
   * @param defaultValue defaultValue
   * @return V
   * @title retry
   * @description retry
   * @author BiJi'an
   * @date 2023-11-12 22:45
   */
  public static <V> V retry(Retryer retryer, Callable<V> callable, V defaultValue) {

    try {
      return (V) retryer.call(callable);
    } catch (Exception e) {
      log.error("retry error", e);
      if (e instanceof RetryException) {
        RetryException retryException = (RetryException) e;
        log.error(
            "retry error  numberOfFailedAttempts={}", retryException.getNumberOfFailedAttempts());
      }
    }

    return defaultValue;
  }

  /**
   * @param type type
   * @param callable callable
   * @param defaultValue defaultValue
   * @return V
   * @title retry
   * @description retry
   * @author BiJi'an
   * @date 2023-11-12 23:37
   */
  public static <V, E extends Enum<E>> V retry(Enum<E> type, Callable<V> callable, V defaultValue) {
    return retry(RETRYERS.get(type), callable, defaultValue);
  }

  /**
   * @param callable callable
   * @param defaultValue defaultValue
   * @return V
   * @title retry
   * @description retry
   * @author BiJi'an
   * @date 2023-11-12 23:37
   */
  public static <V> V retry(Callable<V> callable, V defaultValue) {
    return retry(DEFAULT_RETRYER, callable, defaultValue);
  }
}
