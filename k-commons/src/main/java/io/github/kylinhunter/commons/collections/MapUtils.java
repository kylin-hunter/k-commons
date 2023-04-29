package io.github.kylinhunter.commons.collections;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class MapUtils {
  public static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

  private MapUtils() {}

  /**
   * @return java.util.HashMap<K, V>
   * @title newHashMap
   * @description
   * @author BiJi'an
   * @date 2023-03-19 16:06
   */
  public static <K, V> HashMap<K, V> newHashMap() {
    return new HashMap<>();
  }

  /**
   * @return java.util.concurrent.ConcurrentMap<K, V>
   * @title newConcurrentMap
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:12
   */
  public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
    return new ConcurrentHashMap<>();
  }

  /**
   * @return java.util.LinkedHashMap<K, V>
   * @title newLinkedHashMap
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:12
   */
  public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
    return new LinkedHashMap<>();
  }

  /**
   * @param expectedSize expectedSize
   * @return int
   * @title capacity
   * @description
   * @author BiJi'an
   * @date 2023-03-19 16:49
   */
  static int capacity(int expectedSize) {
    if (expectedSize >= 0) {
      if (expectedSize < 3) {
        return expectedSize + 1;
      }
      if (expectedSize < MAX_POWER_OF_TWO) {
        // This is the calculation used in JDK8 to resize when a putAll
        // happens; it seems to be the most conservative calculation we
        // can make.  0.75 is the default load factor.
        return (int) ((float) expectedSize / 0.75F + 1.0F);
      }
      return Integer.MAX_VALUE; // any large value
    } else {
      return 1;
    }
  }

  /**
   * @param map map
   * @return boolean
   * @title isEmpty
   * @description
   * @author BiJi'an
   * @date 2023-03-19 15:19
   */
  public static boolean isEmpty(Map<?, ?> map) {
    return (map == null || map.isEmpty());
  }

  public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
    return new TreeMap<>();
  }

  public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
    return new TreeMap<>(map);
  }

  public static <C, K extends C, V> TreeMap<K, V> newTreeMap(Comparator<C> comparator) {

    return new TreeMap<>(comparator);
  }
}
