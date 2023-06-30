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
package io.github.kylinhunter.commons.reflect.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 20:32
 */
@Data
public class ActualType {
  private final Class<?> rawType;
  private final Class<?>[] types;

  /**
   * @param index index
   * @return java.lang.Class<T>
   * @title getType
   * @description
   * @author BiJi'an
   * @date 2023-02-11 20:50
   */
  @SuppressWarnings("unchecked")
  public <T> Class<T> getType(int index) {
    if (types != null && index >= 0 && index < types.length) {
      return (Class<T>) types[index];
    }
    return null;
  }
}
