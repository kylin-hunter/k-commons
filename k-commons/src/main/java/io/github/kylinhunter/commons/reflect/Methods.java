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
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-10 23:08
 */
public class Methods {

  private static final Map<Class<?>, Set<Method>> CACHE_GET = MapUtils.newHashMap();

  private static final Map<Class<?>, Set<Method>> CACHE_GET_ALL = MapUtils.newHashMap();

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.reflect.Method>
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-13 00:27
   */
  @SafeVarargs
  public static Set<Method> get(Class<?> clazz, Predicate<Method>... predicates) {
    Set<Method> result =
        CACHE_GET.computeIfAbsent(
            clazz,
            (c) ->
                Stream.of(c.getDeclaredMethods())
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
    if (!CollectionUtils.isEmpty(result) && !ArrayUtils.isEmpty(predicates)) {
      return CollectionUtils.andFilter(result, LinkedHashSet::new, predicates);
    }
    return result;
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.reflect.Method>
   * @title getAll
   * @description
   * @author BiJi'an
   * @date 2023-05-13 00:27
   */
  @SafeVarargs
  public static Set<Method> getAll(Class<?> clazz, Predicate<Method>... predicates) {
    Set<Method> result =
        CACHE_GET_ALL.computeIfAbsent(
            clazz,
            (c) -> {
              Set<Method> tmpResult = SetUtils.newHashSet();
              tmpResult.addAll(get(clazz));
              SuperClazzes.getAll(clazz).forEach(superclazz -> tmpResult.addAll(get(superclazz)));
              Interfaces.getAll(clazz).forEach(i -> tmpResult.addAll(get(i)));
              return tmpResult;
            });

    if (!CollectionUtils.isEmpty(result) && !ArrayUtils.isEmpty(predicates)) {
      return CollectionUtils.andFilter(result, LinkedHashSet::new, predicates);
    }
    return result;
  }
}
