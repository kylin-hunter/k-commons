package io.github.kylinhunter.commons.cache.guava;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.compress.utils.Lists;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import io.github.kylinhunter.commons.sys.KConst;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 11:46
 **/
public class CacheKeyGenerator {

    private static final String DELIMITER = ",";
    private static Map<Class<?>, List<Field>> data;

    static {
        init(KConst.K_BASE_PACKAGE);
    }

    /**
     * @param pkgs pkgs
     * @return void
     * @title init
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:38
     */
    public static void init(String... pkgs) {
        data = Maps.newHashMap();
        for (String pkg : pkgs) {
            Reflections reflections = new Reflections(pkg, Scanners.FieldsAnnotated);
            Set<Field> fields = reflections.getFieldsAnnotatedWith(Cache.Include.class);
            fields.forEach(field -> data.compute(field.getDeclaringClass(), (k, v) -> {
                if (v == null) {
                    v = Lists.newArrayList();
                }
                v.add(field);
                return v;
            }));
        }
    }

    /**
     * @param params params
     * @return java.lang.String
     * @title toKey
     * @description
     * @author BiJi'an
     * @date 2022-08-17 14:20
     */
    public static CacheKey toKey(Object... params) {
        CacheKey cacheKey = new CacheKey(params);
        cacheKey.setKey(keyString(params));
        return cacheKey;

    }

    /**
     * @param params params
     * @return java.lang.String
     * @title toKeyStr
     * @description
     * @author BiJi'an
     * @date 2022-11-27 14:38
     */
    private static String keyString(Object... params) {

        try {
            StringJoiner joiner = new StringJoiner(DELIMITER);
            for (Object param : params) {
                List<Field> fields = data.get(param.getClass());
                if (fields != null) {
                    for (Field field : fields) {

                        field.setAccessible(true);
                        joiner.add(String.valueOf(field.get(param)));

                    }
                } else {
                    joiner.add(String.valueOf(param.hashCode()));
                }

            }
            return joiner.toString();
        } catch (Exception e) {
            throw new BizException("to key error", e);
        }

    }
}
