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
package io.github.kylinhunter.commons.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import lombok.SneakyThrows;

/**
 * @author BiJi'an
 * @description
 * @date 2022-08-16 19:54
 */
public abstract class AbstractCache<V> extends CacheLoader<CacheKey, V> implements Cache<V> {

  private final LoadingCache<CacheKey, V> cache;

  public AbstractCache() {
    this.cache = this.buildCache();
  }

  /**
   * @param key key
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
   * @title put
   * @description
   * @author BiJi'an
   * @date 2022-11-27 14:39
   */
  private void put(CacheKey k, V v) {
    this.cache.put(k, v);
  }

  /***
   * @title put
   * @description
   * @author BiJi'an
   * @param key key
   * @param v v
   * @date 2023-01-19 01:22
   * @return void
   */
  @Override
  public void put(String key, V v) {
    put(CacheKeyGenerator.toKey(key), v);
  }

  /**
   * @param keys keys
   * @param v v
   * @title put
   * @description
   * @author BiJi'an
   * @date 2023-01-19 01:22
   */
  @Override
  public void put(Object[] keys, V v) {
    put(CacheKeyGenerator.toKey(keys), v);
  }

  /**
   * @param key key
   * @param v v
   * @title put
   * @description
   * @author BiJi'an
   * @date 2023-01-19 01:30
   */
  @Override
  public void put(Object key, V v) {
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
  @SneakyThrows
  private V get(CacheKey k) {
    return this.cache.get(k);
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
  private CacheConfig loadConfig() {
    CacheConfig cacheConfig = new CacheConfig();
    custom(cacheConfig);
    return cacheConfig;
  }

  protected abstract void custom(CacheConfig cacheConfig);

  /**
   * @return
   *     com.google.common.cache.LoadingCache<io.github.kylinhunter.commons.cache.guava.CacheKey, V>
   * @title buildCache
   * @description
   * @author BiJi'an
   * @date 2022-11-27 14:40
   */
  @SuppressWarnings({"rawtypes", "unchecked", "ResultOfMethodCallIgnored"})
  private LoadingCache<CacheKey, V> buildCache() {
    CacheConfig cacheConfig = loadConfig();

    CacheBuilder cacheBuilder = CacheBuilder.newBuilder().maximumSize(cacheConfig.getMaxSize());
    if (cacheConfig.getRefreshAfterWrite() > 0) {
      cacheBuilder.refreshAfterWrite(
          cacheConfig.getRefreshAfterWrite(), cacheConfig.getRefreshTimeUnit());
    }

    if (cacheConfig.getExpireAfterWrite() > 0) {
      cacheBuilder.expireAfterWrite(
          cacheConfig.getExpireAfterWrite(), cacheConfig.getExpireTimeUnit());
    }
    RemovalListener<CacheKey, V> removalListener = getRemovalListener();
    if (removalListener != null) {
      cacheBuilder = cacheBuilder.removalListener(removalListener);
    }

    return cacheBuilder.build(this);
  }

  /**
   * @return
   *     com.google.common.cache.RemovalListener<io.github.kylinhunter.commons.cache.guava.CacheKey,
   *     V>
   * @title getRemovalListener
   * @description
   * @author BiJi'an
   * @date 2023-03-19 00:21
   */
  public RemovalListener<CacheKey, V> getRemovalListener() {
    return null;
  }
}
