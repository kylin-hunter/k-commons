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

import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.lang.reflect.Method;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class CMethod {

  @EqualsAndHashCode.Include
  private final Method method;
  private final Object compObject;
  private boolean primary;
  private int depLevel;
  private int order;

  private String name;

  public CMethod(Method method, Object compObject, C c) {
    String name = c.name();
    if (StringUtil.isEmpty(name)) {
      this.name = c.value();
    }
    if (StringUtil.isEmpty(this.name)) {
      this.name = method.getName();
    }
    this.method = method;
    this.compObject = compObject;
    this.primary = c.primary();
    this.order = c.order();
  }
}
