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

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.exception.embed.InitException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 14:07
 */
@Data
public class CObjects {
  private int order;
  private boolean hasPrimary;

  private List<Object> objects = ListUtils.newArrayList();
  private List<CObject> cobjects = ListUtils.newArrayList();

  public void add(CObject cobject) {

    if (cobject.isPrimary()) {
      if (!hasPrimary) {
        hasPrimary = true;
      } else {
        throw new InitException("duplicate primary object");
      }
    }

    if (!cobjects.contains(cobject)) {
      cobjects.add(cobject);
      Collections.sort(cobjects, Comparator.comparingInt(CObject::getOrder));
      objects = cobjects.stream().map(e -> e.getObject()).collect(Collectors.toList());
    }
  }

  /**
   * @return boolean
   * @title isEmpty
   * @description
   * @author BiJi'an
   * @date 2023-02-12 15:17
   */
  public boolean isEmpty() {
    return objects.isEmpty();
  }

  /**
   * @return java.lang.Object
   * @title getCompObject
   * @description
   * @author BiJi'an
   * @date 2023-02-12 15:19
   */
  public Object getObject() {
    if (objects != null) {
      return objects.get(0);
    } else {
      return null;
    }
  }
}
