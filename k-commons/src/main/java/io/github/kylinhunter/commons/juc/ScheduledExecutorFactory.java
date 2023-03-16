package io.github.kylinhunter.commons.juc;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import io.github.kylinhunter.commons.collections.MapUtils;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 01:32
 **/
public class ScheduledExecutorFactory {
    private static final Map<String, ScheduledExecutorService> pools = MapUtils.newHashMap();

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
     * @param corePoolSize
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
        ScheduledExecutorService scheduledExecutorService = pools.get(name);
        if (scheduledExecutorService == null) {
            throw new InitException("no thread pool:" + name);
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
    public static void shutdownNow(String name) {
        ScheduledExecutorService threadPoolExecutor = get(name);
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
        }
    }

}
