package io.github.kylinhunter.commons.utils.retry;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.utils.exception.ToRetryException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-12 22:46
 */
@Slf4j
public class RetryDemoTask {

  public Map<String, Integer> stats = MapUtils.newHashMap();


  /**
   * @param type type
   * @return boolean
   * @title retryTask
   * @description retryTask
   * @author BiJi'an
   * @date 2023-11-12 22:55
   */
  public boolean retryTask(String type) {
    log.info("reveive :{}", type);
    Integer count = stats.compute(type, (k, v) -> {
      if (v == null) {
        v = 1;
      } else {
        v++;
      }
      return v;
    });

    if ("exception_ingnore".equals(type)) {
      throw new KRuntimeException("exception_ingnore");
    } else if ("exception_always".equals(type)) {
      throw new ToRetryException("exception_always");
    } else if ("failed_1".equals(type)) {
      if (count <= 1) {
        throw new ToRetryException("failed_1");
      }
    } else if ("failed_2".equals(type)) {
      if (count <= 2) {
        throw new ToRetryException("failed_2");
      }
    } else if ("failed_3".equals(type)) {
      if (count <= 3) {
        throw new ToRetryException("failed_3");
      }
    } else if ("failed_4".equals(type)) {
      if (count <= 4) {
        throw new ToRetryException("failed_4");
      }
    }
    return true;
  }


}