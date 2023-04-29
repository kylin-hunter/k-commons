package io.github.kylinhunter.commons.collections;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class ArrayUtils {

  /**
   * @param array array
   * @return boolean
   * @title isEmpty
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:42
   */
  public static boolean isEmpty(final Object[] array) {
    return array == null || array.length == 0;
  }
}
