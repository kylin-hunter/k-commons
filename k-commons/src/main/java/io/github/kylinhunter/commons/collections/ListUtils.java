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
package io.github.kylinhunter.commons.collections;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class ListUtils {

  private ListUtils() {
  }

  /**
   * @return java.util.ArrayList<E>
   * @title newArrayList
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:51
   */
  public static <E> ArrayList<E> newArrayList() {
    return new ArrayList<>();
  }

  @SuppressWarnings("unchecked")
  public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
    ExceptionChecker.checkNonnegative(initialArraySize, "initialArraySize");
    return new ArrayList(initialArraySize);
  }

  /**
   * @param elements elements
   * @return java.util.ArrayList<E>
   * @title newArrayList
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:51
   */
  @SuppressWarnings("unchecked")
  public static <E> ArrayList<E> newArrayList(E... elements) {
    if (elements != null) {
      int length = elements.length;
      int capacity = 5 + length + (length / 10);
      ArrayList<E> list = new ArrayList<>(capacity);
      Collections.addAll(list, elements);
      return list;
    }
    return new ArrayList<>();
  }

  @SuppressWarnings("unchecked")
  public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
    if (elements instanceof Collection) {
      return new ArrayList<>((Collection<? extends E>) elements);
    } else {
      ArrayList<E> set = newArrayList();
      Iterators.addAll(set, elements.iterator());
      return set;
    }
  }

  /**
   * @param list list
   * @return java.lang.String
   * @title toString
   * @description toString
   * @author BiJi'an
   * @date 2023-10-18 17:19
   */
  public static <E> String toString(List<E> list) {
    return list.stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
  }
}
