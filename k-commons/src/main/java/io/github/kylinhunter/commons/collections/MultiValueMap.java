package io.github.kylinhunter.commons.collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 23:46
 */
public class MultiValueMap<K, V> {
  private final Map<K, List<V>> configs = new HashMap<>();

  /**
   * @param key key
   * @param value value
   * @return void
   * @title add
   * @description
   * @author BiJi'an
   * @date 2023-03-19 11:02
   */
  public void add(K key, V value) {
    if (key != null && value != null) {
      configs.compute(
          key,
          (k, v) -> {
            if (v == null) {
              v = ListUtils.newArrayList();
            }
            v.add(value);
            return v;
          });
    }
  }

  /**
   * @param key key
   * @return java.util.List<java.lang.String>
   * @title getValues
   * @description
   * @author BiJi'an
   * @date 2023-03-19 11:04
   */
  public List<V> getValues(K key) {
    return configs.get(key);
  }

  /**
   * @param key key
   * @return java.lang.String
   * @title getValue
   * @description
   * @author BiJi'an
   * @date 2023-03-19 11:04
   */
  public V getValue(K key) {
    List<V> values = this.getValues(key);
    if (!CollectionUtils.isEmpty(values)) {
      return values.get(0);
    }
    return null;
  }
}
