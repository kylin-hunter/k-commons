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
package io.github.kylinhunter.commons.juc;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 01:32
 */
public class ScheduledExecutorFactory {

  private static final Map<String, ScheduledExecutorService> pools = MapUtils.newHashMap();
  private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
  private static int DEFAULT_CORE_POOL_SIZE = PROCESSORS;

  /**
   * @param corePoolSize corePoolSize
   * @return java.util.concurrent.ThreadPoolExecutor
   * @title a
   * @description
   * @author BiJi'an
   * @date 2023-03-04 01:42
   */
  public static synchronized ScheduledExecutorService register(String name, int corePoolSize) {
    return pools.computeIfAbsent(name, (n) -> create(corePoolSize));
  }

  /**
   * @param corePoolSize corePoolSize
   * @return java.util.concurrent.ThreadPoolExecutor
   * @title create
   * @description
   * @author BiJi'an
   * @date 2023-03-11 20:18
   */
  private static synchronized ScheduledExecutorService create(int corePoolSize) {

    return Executors.newScheduledThreadPool(corePoolSize);
  }

  /**
   * @param name name
   * @return java.util.concurrent.ThreadPoolExecutor
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-03-04 01:44
   */
  public static ScheduledExecutorService get(String name) {
    return get(name, false);
  }

  /**
   * @param name name
   * @param createIfNoExist createIfNoExist
   * @return java.util.concurrent.ScheduledExecutorService
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-10 15:33
   */
  public static ScheduledExecutorService get(String name, boolean createIfNoExist) {
    ScheduledExecutorService scheduledExecutorService = pools.get(name);
    if (scheduledExecutorService == null) {
      if (createIfNoExist) {
        scheduledExecutorService = register(name, DEFAULT_CORE_POOL_SIZE);
      } else {
        throw new InitException("no thread pool:" + name);
      }
    }

    return scheduledExecutorService;
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
    ScheduledExecutorService threadPoolExecutor = get(name);
    if (threadPoolExecutor != null) {
      return threadPoolExecutor.shutdownNow();
    }
    return Collections.emptyList();
  }
}
