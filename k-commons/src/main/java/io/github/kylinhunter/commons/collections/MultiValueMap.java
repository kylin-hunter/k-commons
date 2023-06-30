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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 23:46
 */
@NoArgsConstructor
public class MultiValueMap<K, V> {

  private final Map<K, Collection<V>> map = new HashMap<>();
  private boolean removeduplicates = true;

  public MultiValueMap(boolean removeduplicates) {
    this.removeduplicates = removeduplicates;
  }

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
      map.compute(
          key,
          (k, v) -> {
            if (v == null) {
              if (removeduplicates) {
                v = SetUtils.newHashSet();
              } else {
                v = ListUtils.newArrayList();
              }
              v.add(value);

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
  public Collection<V> getValues(K key) {
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
    Collection<V> values = this.getValues(key);
    if (!CollectionUtils.isEmpty(values)) {
      return values.iterator().next();
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
  public Collection<V> remove(K key) {
    return this.map.remove(key);
  }

  /**
   * @param key key
   * @param v v
   * @return boolean
   * @throws
   * @title remove
   * @description
   * @author BiJi'an
   * @date 2023-05-11 00:08
   */
  public boolean remove(K key, V v) {
    Collection<V> values = this.getValues(key);
    if (!CollectionUtils.isEmpty(values)) {
      return values.remove(v);
    }
    return false;
  }

  /**
   * @param key key
   * @param mappingFunction mappingFunction
   * @return java.util.Collection<V>
   * @throws
   * @title computeIfAbsent
   * @description
   * @author BiJi'an
   * @date 2023-05-11 00:08
   */
  public Collection<V> computeIfAbsent(K key, Function<K, Collection<V>> mappingFunction) {
    return map.computeIfAbsent(key, mappingFunction);
  }
}
