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
package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.collections.ArrayUtils;
import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-10 23:08
 */
public class Interfaces {

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
  @SafeVarargs
  public static Set<Class<?>> get(Class<?> clazz, Predicate<Class<?>>... predicates) {
    Set<Class<?>> result =
        CACHE_GET.computeIfAbsent(
            clazz,
            (c) ->
                Stream.of(c.getInterfaces()).collect(Collectors.toCollection(LinkedHashSet::new)));
    if (!CollectionUtils.isEmpty(result) && !ArrayUtils.isEmpty(predicates)) {
      return CollectionUtils.andFilter(result, LinkedHashSet::new, predicates);
    }
    return result;
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
              Queue<Class<?>> queue = new LinkedList<>(get(clazz));
              SuperClazzes.getAll(clazz).forEach(superclazz -> queue.addAll(get(superclazz)));
              while (!queue.isEmpty()) {
                Class<?> removeInterface = queue.remove();
                tmpResult.add(removeInterface);
                Class<?>[] superInterfaces = removeInterface.getInterfaces();
                if (!ArrayUtils.isEmpty(superInterfaces)) {
                  Collections.addAll(queue, superInterfaces);
                }
              }
              return tmpResult;
            });

    if (!CollectionUtils.isEmpty(result) && !ArrayUtils.isEmpty(predicates)) {
      return CollectionUtils.andFilter(result, LinkedHashSet::new, predicates);
    }
    return result;
  }
}
