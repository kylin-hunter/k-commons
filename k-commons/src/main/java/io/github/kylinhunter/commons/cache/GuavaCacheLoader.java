package io.github.kylinhunter.commons.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GuavaCacheLoader<K, V> extends CacheLoader<K, V> {

    /**
     * @return io.github.kylinhunter.commons.cache.GuavaCacheConfig
     * @title loadConfig
     * @description
     * @author BiJi'an
     * @date 2022-11-27 02:34
     */
    public GuavaCacheConfig loadConfig() {
        return GuavaCacheConfig.getDefaultConfig();
    }

    @SuppressWarnings({"rawtypes", "unchecked", "ResultOfMethodCallIgnored"})
    public LoadingCache<K, V> buildCache() {
        GuavaCacheConfig guavaCacheConfig = loadConfig();

        CacheBuilder cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(guavaCacheConfig.getMaxSize());
        if (guavaCacheConfig.getRefreshAfterWrite() > 0) {
            cacheBuilder
                    .refreshAfterWrite(guavaCacheConfig.getRefreshAfterWrite(), guavaCacheConfig.getRefreshTimeUnit());
        }

        if (guavaCacheConfig.getExpireAfterWrite() > 0) {
            cacheBuilder.expireAfterWrite(guavaCacheConfig.getExpireAfterWrite(), guavaCacheConfig.getExpireTimeUnit());
        }

        cacheBuilder.removalListener(
                notification -> log.info(notification.getKey() + " remove:" + notification.getCause()));

        return cacheBuilder.build(this);
    }
}
