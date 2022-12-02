package io.github.kylinhunter.commons.cache.guava;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import io.github.kylinhunter.commons.sys.KConst;
import io.github.kylinhunter.commons.util.ObjectValues;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 11:46
 **/
public class CacheKeyGenerator {

    private static final String DELIMITER = ",";
    private static Map<Class<?>, List<Field>> customKeyFields;
    private static final Set<Class<?>> basicSerializedClasses = Sets.newHashSet();

    static {
        init(KConst.K_BASE_PACKAGE);
    }

    public static void initBasicSerializedClasses() {
        basicSerializedClasses.add(String.class);
        basicSerializedClasses.add(Integer.class);
        basicSerializedClasses.add(int.class);
        basicSerializedClasses.add(Long.class);
        basicSerializedClasses.add(long.class);
        basicSerializedClasses.add(Float.class);
        basicSerializedClasses.add(float.class);
        basicSerializedClasses.add(Double.class);
        basicSerializedClasses.add(double.class);
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
        initBasicSerializedClasses();
        customKeyFields = Maps.newHashMap();
        for (String pkg : pkgs) {
            Reflections reflections = new Reflections(pkg, Scanners.FieldsAnnotated);
            Set<Field> fields = reflections.getFieldsAnnotatedWith(Cache.Include.class);
            fields.forEach(field -> customKeyFields.compute(field.getDeclaringClass(), (k, v) -> {
                if (v == null) {
                    v = Lists.newArrayList();
                }
                field.setAccessible(true);
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
                List<Field> fields = customKeyFields.get(param.getClass());
                if (fields != null && fields.size() > 0) {
                    for (Field field : fields) {
                        joiner.add(String.valueOf(field.get(param)));

                    }
                } else {
                    if (basicSerializedClasses.contains(param.getClass())) {
                        joiner.add(ObjectValues.getString(param));
                    } else {
                        throw new BizException("invalid param class " + param.getClass());

                    }

                }

            }
            return joiner.toString();
        } catch (Exception e) {
            throw new BizException("to key error", e);
        }

    }

}
