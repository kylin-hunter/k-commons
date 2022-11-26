package io.github.kylinhunter.commons.cache;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.LoadingCache;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-08-16 19:54
 **/
@Slf4j
public abstract class GuavaCache<K, V> extends GuavaCacheLoader<K, V> implements KCache<K, V> {

    private final LoadingCache<K, V> cache;

    public GuavaCache() {
        this.cache = super.buildCache();
    }

    public void invalidate(K key) {
        cache.invalidate(key);
    }

    @Override
    public void put(K k, V v) {
        this.cache.put(k, v);
    }

    @Override
    public V get(K k) {
        try {
            return this.cache.get(k);
        } catch (ExecutionException e) {
            throw new BizException("get cache error", e);
        }
    }
}
