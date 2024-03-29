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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 14:07
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CObject {

  private CConstructor cconstructor;
  private CMethod cmethod;

  private boolean primary;
  private int order;
  private String name;

  @EqualsAndHashCode.Include private Object object;

  public CObject(String name, Object object) {
    this.name = name;
    this.object = object;
  }

  public CObject(boolean primary, int order, String name, Object object) {
    this.primary = primary;
    if (primary) {
      this.order = Integer.MIN_VALUE;
    } else {
      this.order = order;
    }
    this.name = name;
    this.object = object;
  }

  public CObject(CConstructor cconstructor, Object object) {
    this(cconstructor.isPrimary(), cconstructor.getOrder(), cconstructor.getName(), object);
    this.cconstructor = cconstructor;
  }

  public CObject(CMethod cmethod, Object object) {
    this(cmethod.isPrimary(), cmethod.getOrder(), cmethod.getName(), object);
    this.cmethod = cmethod;
  }
}
