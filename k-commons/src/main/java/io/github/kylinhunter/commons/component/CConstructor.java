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
package io.github.kylinhunter.commons.component;

import java.lang.reflect.Constructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class CConstructor {
  @EqualsAndHashCode.Include private Class<?> clazz;
  private Constructor<?> constructor;
  private boolean primary;
  private int order;
  private int depLevel;

  public CConstructor(Class<?> clazz, C c) {
    this.clazz = clazz;
    this.constructor = clazz.getConstructors()[0];
    this.primary = c.primary();
    this.order = c.order();
  }
}
