package io.github.kylinhunter.commons.collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 23:46
 */
@NoArgsConstructor
public class MultiValueMap<K, V> {

  private final Map<K, List<V>> map = new HashMap<>();
  private boolean removeduplicates;

  public MultiValueMap(boolean removeduplicates) {
    this.removeduplicates = removeduplicates;
  }

  /**
   * @param key   key
   * @param value value
   * @return void
   * @title add
   * @description
   * @author BiJi'an
   * @date 2023-03-19 11:02
   */
  public void add(K key, V value) {
    if (key != null && value != null) {
      map.compute(
          key,
          (k, v) -> {
            if (v == null) {
              v = ListUtils.newArrayList();
            }
            if (removeduplicates) {
              if (!v.contains(value)) {
                v.add(value);
              }
            } else {
              v.add(value);
            }
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
    return map.get(key);
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

  /**
   * @param key key
   * @return void
   * @title remove
   * @description
   * @author BiJi'an
   * @date 2023-05-09 17:22
   */
  public List<V> remove(K key) {
    return this.map.remove(key);
  }

  public boolean remove(K key, V v) {
    List<V> values = this.getValues(key);
    if (!CollectionUtils.isEmpty(values)) {
      return values.remove(v);
    }
    return false;
  }
}
