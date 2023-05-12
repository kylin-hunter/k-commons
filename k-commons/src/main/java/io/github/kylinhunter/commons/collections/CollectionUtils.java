package io.github.kylinhunter.commons.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class CollectionUtils {

  /**
   * @param results results
   * @return java.util.List<T>
   * @title merge body
   * @description
   * @author BiJi'an
   * @date 2022/1/1 8:30
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> merge(boolean reuse, List<T>... results) {

    List<T> dist = reuse ? null : ListUtils.newArrayList();
    for (List<T> result : results) {
      if (result != null && result.size() > 0) {
        if (dist == null) {
          dist = result;
        } else {
          dist.addAll(result);
        }
      }
    }
    return dist != null ? dist : Collections.EMPTY_LIST;
  }

  /**
   * @param c c
   * @return boolean
   * @title isEmpty
   * @description
   * @author BiJi'an
   * @date 2023-03-19 00:59
   */
  public static boolean isEmpty(final Collection<?> c) {
    return c == null || c.isEmpty();
  }

  /**
   * @param c c
   * @param collectionFactory collectionFactory
   * @param predicates predicates
   * @return java.util.Set<T>
   * @title andFilter
   * @description
   * @author BiJi'an
   * @date 2023-05-12 16:20
   */
  public static <T, C extends Collection<T>> C andFilter(
      final Collection<T> c, Supplier<C> collectionFactory, Predicate<T>... predicates) {
    return StreamUtils.andFilter(c.stream(), predicates)
        .collect(Collectors.toCollection(collectionFactory));
  }

  /**
   * @param stream stream
   * @param collectionFactory collectionFactory
   * @param predicates predicates
   * @return C
   * @title orFilter
   * @description
   * @author BiJi'an
   * @date 2023-05-12 16:24
   */
  public static <T, C extends Collection<T>> C orFilter(
      final Stream<T> stream, Supplier<C> collectionFactory, Predicate<T>... predicates) {
    return StreamUtils.orFilter(stream, predicates)
        .collect(Collectors.toCollection(collectionFactory));
  }
}
