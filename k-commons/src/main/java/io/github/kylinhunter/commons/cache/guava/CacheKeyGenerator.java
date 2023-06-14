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

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import io.github.kylinhunter.commons.sys.KConst;
import io.github.kylinhunter.commons.util.ObjectValues;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 11:46
 */
public class CacheKeyGenerator {

  private static final String DELIMITER = ",";
  private static Map<Class<?>, List<Field>> customKeyFields;
  private static final Set<Class<?>> basicSerializedClasses = SetUtils.newHashSet();

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
    customKeyFields = MapUtils.newHashMap();
    for (String pkg : pkgs) {
      Reflections reflections = new Reflections(pkg, Scanners.FieldsAnnotated);
      Set<Field> fields = reflections.getFieldsAnnotatedWith(Cache.Include.class);
      fields.forEach(
          field ->
              customKeyFields.compute(
                  field.getDeclaringClass(),
                  (k, v) -> {
                    if (v == null) {
                      v = ListUtils.newArrayList();
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
