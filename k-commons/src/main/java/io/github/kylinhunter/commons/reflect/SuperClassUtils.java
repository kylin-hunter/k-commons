package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-10 23:08
 */
public class SuperClassUtils {

  private static final Map<Class<?>, Set<Class<?>>> CACHE_GET = MapUtils.newHashMap();

  private static final Map<Class<?>, Set<Class<?>>> CACHE_GET_ALL = MapUtils.newHashMap();

  /**
   * @param clazz clazz
   * @return java.util.Collection<java.lang.Class < ?>>
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-11 00:14
   */
  public static Collection<Class<?>> get(Class<?> clazz) {
    return CACHE_GET.computeIfAbsent(clazz, (c) -> {
      Class<?> superclass = c.getSuperclass();
      return superclass != null && !superclass.equals(Object.class) ? Collections
          .singleton(superclass) : Collections.emptySet();
    });

  }

  /**
   * @param clazz clazz
   * @return java.util.Collection<java.lang.Class < ?>>
   * @title getAll
   * @description
   * @author BiJi'an
   * @date 2023-05-11 00:14
   */
  public static Collection<Class<?>> getAll(Class<?> clazz) {
    return CACHE_GET_ALL.computeIfAbsent(clazz, (c) -> {
      Set<Class<?>> result = SetUtils.newHashSet();
      Class<?> superclass = c.getSuperclass();
      while (superclass != null && !superclass.equals(Object.class)) {
        result.add(superclass);
        superclass = superclass.getSuperclass();
      }
      return result;
    });
  }
}
