package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.collections.ArrayUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-10 23:08
 */
public class InterfaceUtils {

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
      Set<Class<?>> result = SetUtils.newHashSet();
      Class<?>[] interfaces = c.getInterfaces();
      if (!ArrayUtils.isEmpty(interfaces)) {
        for (Class<?> anInterface : interfaces) {
          result.add(anInterface);
        }
      }
      return result;

    });

  }

  /**
   * @param clazz clazz
   * @return java.util.Collection<java.lang.Class < ?>>
   * @title getAll
   * @descriptionInterfaceUtils
   * @author BiJi'an
   * @date 2023-05-11 00:14
   */
  public static Collection<Class<?>> getAll(Class<?> clazz) {
    return CACHE_GET_ALL.computeIfAbsent(clazz, (c) -> {
      Queue<Class<?>> queue = new LinkedList();
      Set<Class<?>> result = SetUtils.newHashSet();

      get(clazz).forEach(queue::add);
      SuperClassUtils.getAll(clazz).stream().forEach(c1 -> {
        Collection<Class<?>> classes = get(c1);
        classes.forEach(queue::add);
        while (!queue.isEmpty()) {
          Class<?> removeClass = queue.remove();
          result.add(removeClass);
          Class<?>[] superInterfaces = removeClass.getInterfaces();
          if (!ArrayUtils.isEmpty(superInterfaces)) {
            for (Class<?> anInterface : superInterfaces) {
              queue.add(anInterface);
            }
          }
        }
      });
      return result;
    });


  }
}
