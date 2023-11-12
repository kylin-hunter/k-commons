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
package io.github.kylinhunter.commons.utils.cache.guava;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.util.ObjectValues;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 12:27
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CacheKey {

  @EqualsAndHashCode.Include private String key;
  private Object[] params;

  public CacheKey(Object... params) {
    ExceptionChecker.checkArgument(params != null && params.length > 0, "param eror");
    this.params = params;
  }

  public String getString(int index) {
    ExceptionChecker.checkNum(index, 0, params.length);
    return ObjectValues.getString(params[index]);
  }

  public int getInt(int index) {
    ExceptionChecker.checkNum(index, 0, params.length);
    return ObjectValues.getInt(params[index]);
  }
}
