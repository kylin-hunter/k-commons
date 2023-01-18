package io.github.kylinhunter.commons.cache.guava;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 01:26
 **/
public interface Cache<V> {

    void put(String key, V v);

    void put(Object key, V v);

    void put(Object[] keys, V v);

    V get(String key);

    V get(Object... keys);

    void invalidate(String key);

    void invalidate(Object... keys);

    /**
     * @author BiJi'an
     * @description
     * @date 2022-11-27 01:26
     **/
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface Include {
    }
}
