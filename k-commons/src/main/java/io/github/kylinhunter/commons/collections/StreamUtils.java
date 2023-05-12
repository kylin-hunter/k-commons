package io.github.kylinhunter.commons.collections;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author BiJi'an
 * @description
 * @date 2023-05-12 15:48
 */
public class StreamUtils {

  /**
   * @param stream stream
   * @param predicates predicates
   * @return java.util.stream.Stream<T>
   * @title filter
   * @description
   * @author BiJi'an
   * @date 2023-05-12 15:51
   */
  public static <T> Stream<T> andFilter(final Stream<T> stream, Predicate<T>... predicates) {
    ExceptionChecker.checkNotNull(stream);
    ExceptionChecker.checkNotEmpty(predicates);
    return stream.filter(Arrays.stream(predicates).reduce(t -> true, Predicate::and));
  }

  /**
   * @param stream stream
   * @param predicates predicates
   * @return java.util.stream.Stream<T>
   * @title orFilter
   * @description
   * @author BiJi'an
   * @date 2023-05-12 16:14
   */
  public static <T> Stream<T> orFilter(final Stream<T> stream, Predicate<T>... predicates) {
    ExceptionChecker.checkNotNull(stream);
    ExceptionChecker.checkNotEmpty(predicates);
    return stream.filter(Arrays.stream(predicates).reduce(t -> false, Predicate::or));
  }
}
