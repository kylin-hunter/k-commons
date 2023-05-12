package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.collections.ArrayUtils;
import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-10 23:08
 */
public class SuperClazzes {

  private static final Map<Class<?>, Set<Class<?>>> CACHE_GET_ALL = MapUtils.newHashMap();

  /**
   * @param clazz clazz
   * @return java.util.Collection<java.lang.Class < ?>>
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-11 00:14
   */
  public static Class<?> get(Class<?> clazz) {
    Class<?> superclass = clazz.getSuperclass();
    return superclass != null && !superclass.equals(Object.class) ? superclass : null;
  }

  /**
   * @param clazz clazz
   * @return java.util.Collection<java.lang.Class < ?>>
   * @title getAll
   * @description
   * @author BiJi'an
   * @date 2023-05-11 00:14
   */
  @SafeVarargs
  public static Set<Class<?>> getAll(Class<?> clazz, Predicate<Class<?>>... predicates) {
    Set<Class<?>> result =
        CACHE_GET_ALL.computeIfAbsent(
            clazz,
            (c) -> {
              Set<Class<?>> tmpResult = SetUtils.newHashSet();
              Class<?> superclass = c.getSuperclass();
              while (superclass != null && !superclass.equals(Object.class)) {
                tmpResult.add(superclass);
                superclass = superclass.getSuperclass();
              }
              return tmpResult;
            });
    if (!CollectionUtils.isEmpty(result) && !ArrayUtils.isEmpty(predicates)) {
      return CollectionUtils.andFilter(result, LinkedHashSet::new, predicates);
    }
    return result;
  }
}
