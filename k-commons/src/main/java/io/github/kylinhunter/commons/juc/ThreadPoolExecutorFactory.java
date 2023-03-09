package io.github.kylinhunter.commons.juc;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-04 01:32
 **/
public class ThreadPoolExecutorFactory {
    private static final Map<String, ThreadPoolExecutor> pools = Maps.newHashMap();

    /**
     * @param corePoolSize    corePoolSize
     * @param maximumPoolSize maximumPoolSize
     * @param capacity        capacity
     * @return java.util.concurrent.ThreadPoolExecutor
     * @title a
     * @description
     * @author BiJi'an
     * @date 2023-03-04 01:42
     */
    public static synchronized ThreadPoolExecutor register(String name, int corePoolSize, int maximumPoolSize,
                                                           int capacity) {
        return pools.computeIfAbsent(name, (n) -> create(corePoolSize, maximumPoolSize, capacity));

    }

    /**
     * @param corePoolSize    corePoolSize
     * @param maximumPoolSize maximumPoolSize
     * @param capacity        capacity
     * @return java.util.concurrent.ThreadPoolExecutor
     * @title create
     * @description
     * @author BiJi'an
     * @date 2023-03-03 00:13
     */

    private static synchronized ThreadPoolExecutor create(int corePoolSize, int maximumPoolSize, int capacity) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(capacity));

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
        ThreadPoolExecutor threadPoolExecutor = pools.get(name);
        if (threadPoolExecutor == null) {
            throw new InitException("no thread pool:" + name);
        }
        return threadPoolExecutor;
    }

    /**
     * @param name name
     * @return java.util.concurrent.ThreadPoolExecutor
     * @title shutdownNow
     * @description
     * @author BiJi'an
     * @date 2023-03-08 19:53
     */
    public static void shutdownNow(String name) {
        final ThreadPoolExecutor threadPoolExecutor = get(name);
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
        }
    }

}
