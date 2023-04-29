package io.github.kylinhunter.commons.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class SetUtils {
  private SetUtils() {}

  /**
   * @return java.util.HashSet<E>
   * @title newHashSet
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:44
   */
  public static <E> HashSet<E> newHashSet() {
    return new HashSet<>();
  }

  /**
   * @param elements elements
   * @return java.util.HashSet<E>
   * @title newHashSet
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:44
   */
  @SafeVarargs
  public static <E> HashSet<E> newHashSet(E... elements) {
    HashSet<E> set = new HashSet<>(MapUtils.capacity(elements.length));
    Collections.addAll(set, elements);
    return set;
  }

  /**
   * @param elements elements
   * @return java.util.HashSet<E>
   * @title newHashSet
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:45
   */
  @SuppressWarnings("unchecked")
  public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
    if (elements instanceof Collection) {
      return new HashSet<>((Collection<? extends E>) elements);
    } else {
      HashSet<E> set = newHashSet();
      Iterators.addAll(set, elements.iterator());
      return set;
    }
  }

  /**
   * @return java.util.TreeSet<E>
   * @title newTreeSet
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:04
   */
  public static <E extends Comparable> TreeSet<E> newTreeSet() {
    return new TreeSet<E>();
  }
}
