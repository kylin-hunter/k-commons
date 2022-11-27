package io.github.kylinhunter.commons.cache.guava;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-08-16 19:54
 **/
@Slf4j
public abstract class AbstractGuavaCache<V> extends CacheLoader<CacheKey, V> implements Cache<V> {

    private final LoadingCache<CacheKey, V> cache;

    public AbstractGuavaCache() {
        this.cache = this.buildCache();
    }

    /**
     * @param key key
     * @return void
     * @title invalidate
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:39
     */
    private void invalidate(CacheKey key) {
        cache.invalidate(key);
    }

    /**
     * @param keys keys
     * @return void
     * @title invalidate
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:39
     */
    public void invalidate(Object... keys) {
        invalidate(CacheKeyGenerator.toKey(keys));
    }

    /**
     * @param key key
     * @return void
     * @title invalidate
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:39
     */

    public void invalidate(String key) {
        invalidate(CacheKeyGenerator.toKey(key));

    }

    /**
     * @param k k
     * @param v v
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:39
     */

    private void put(CacheKey k, V v) {
        this.cache.put(k, v);
    }

    /**
     * @param v    v
     * @param keys keys
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:39
     */
    public void put(V v, Object... keys) {
        put(CacheKeyGenerator.toKey(keys), v);
    }

    /**
     * @param v   v
     * @param key key
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:39
     */
    public void put(V v, String key) {
        put(CacheKeyGenerator.toKey(key), v);

    }

    /**
     * @param k k
     * @return V
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:39
     */
    private V get(CacheKey k) {
        try {
            return this.cache.get(k);
        } catch (ExecutionException e) {
            throw new BizException("get cache error", e);
        }
    }

    /**
     * @param keys keys
     * @return V
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:40
     */
    public V get(Object... keys) {
        return get(CacheKeyGenerator.toKey(keys));
    }

    /**
     * @param key key
     * @return V
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:40
     */
    public V get(String key) {
        return get(CacheKeyGenerator.toKey(key));
    }

    /**
     * @return io.github.kylinhunter.commons.cache.guava.CacheConfig
     * @title loadConfig
     * @description
     * @author BiJi'an
     * @date 2022-11-27 02:34
     */
    public CacheConfig loadConfig() {
        return CacheConfig.getDefaultConfig();
    }

    /**
     * @return com.google.common.cache.LoadingCache<io.github.kylinhunter.commons.cache.guava.CacheKey, V>
     * @title buildCache
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:40
     */
    @SuppressWarnings({"rawtypes", "unchecked", "ResultOfMethodCallIgnored"})
    public LoadingCache<CacheKey, V> buildCache() {
        CacheConfig cacheConfig = loadConfig();

        CacheBuilder cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(cacheConfig.getMaxSize());
        if (cacheConfig.getRefreshAfterWrite() > 0) {
            cacheBuilder
                    .refreshAfterWrite(cacheConfig.getRefreshAfterWrite(), cacheConfig.getRefreshTimeUnit());
        }

        if (cacheConfig.getExpireAfterWrite() > 0) {
            cacheBuilder.expireAfterWrite(cacheConfig.getExpireAfterWrite(), cacheConfig.getExpireTimeUnit());
        }

        cacheBuilder.removalListener(
                notification -> log.info(notification.getKey() + " remove:" + notification.getCause()));

        return cacheBuilder.build(this);
    }
}
