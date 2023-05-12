package io.github.kylinhunter.commons.juc;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 01:32
 */
public class ThreadPoolExecutorFactory {

  private static final Map<String, ThreadPoolExecutor> pools = MapUtils.newHashMap();
  private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
  private static int DEFAULT_CORE_POOL_SIZE = PROCESSORS;
  private static int DEFAULT_MAXIMUM_POOL_SIZE = PROCESSORS * 2;
  private static int DEFAULT_CAPACITY = Integer.MAX_VALUE;

  /**
   * @param corePoolSize corePoolSize
   * @param maximumPoolSize maximumPoolSize
   * @param capacity capacity
   * @return java.util.concurrent.ThreadPoolExecutor
   * @title a
   * @description
   * @author BiJi'an
   * @date 2023-03-04 01:42
   */
  public static synchronized ThreadPoolExecutor register(
      String name, int corePoolSize, int maximumPoolSize, int capacity) {
    return pools.computeIfAbsent(name, (n) -> create(corePoolSize, maximumPoolSize, capacity));
  }

  /**
   * @param corePoolSize corePoolSize
   * @param maximumPoolSize maximumPoolSize
   * @param capacity capacity
   * @return java.util.concurrent.ThreadPoolExecutor
   * @title create
   * @description
   * @author BiJi'an
   * @date 2023-03-03 00:13
   */
  private static synchronized ThreadPoolExecutor create(
      int corePoolSize, int maximumPoolSize, int capacity) {
    return new ThreadPoolExecutor(
        corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(capacity));
  }

  /**
   * @param name name
   * @return java.util.concurrent.ThreadPoolExecutor
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-03-04 01:44
   */
  public static ThreadPoolExecutor get(String name) {
    return get(name, false);
  }

  public static ThreadPoolExecutor get(String name, boolean createIfNoExist) {
    ThreadPoolExecutor threadPoolExecutor = pools.get(name);
    if (threadPoolExecutor == null) {
      if (createIfNoExist) {
        threadPoolExecutor =
            register(name, DEFAULT_CORE_POOL_SIZE, DEFAULT_MAXIMUM_POOL_SIZE, DEFAULT_CAPACITY);
      } else {
        throw new InitException("no thread pool:" + name);
      }
    }
    return threadPoolExecutor;
  }

  /**
   * @param name name
   * @return java.util.concurrent.ThreadPoolExecutor
   * @title shutdownNow
   * @description
   * @author BiJi'an
   * @date 2023-03-11 19:53
   */
  public static List<Runnable> shutdownNow(String name) {
    final ThreadPoolExecutor threadPoolExecutor = get(name);
    if (threadPoolExecutor != null) {
      return threadPoolExecutor.shutdownNow();
    }
    return Collections.emptyList();
  }
}
