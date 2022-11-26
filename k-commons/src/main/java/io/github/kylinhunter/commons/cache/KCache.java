package io.github.kylinhunter.commons.cache;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 01:26
 **/
public interface KCache<K, V> {

    void put(K k, V v);

    V get(K k);

    void invalidate(K key);

}
