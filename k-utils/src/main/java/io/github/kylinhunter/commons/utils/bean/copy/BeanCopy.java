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
package io.github.kylinhunter.commons.utils.bean.copy;

import io.github.kylinhunter.commons.utils.bean.copy.convertor.ConvertExcetion;
import io.github.kylinhunter.commons.utils.bean.copy.convertor.FieldConvertor;
import java.util.List;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
public class BeanCopy {

  @Setter private List<FieldConvertor> fieldConvertors;

  public boolean isEmpty() {
    return (fieldConvertors == null || fieldConvertors.isEmpty());
  }

  /**
   * @param source source
   * @param target target
   * @return void
   * @title copy
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:14
   */
  public void copy(Object source, Object target) throws ConvertExcetion {

    if (fieldConvertors != null && !fieldConvertors.isEmpty()) {
      for (FieldConvertor fieldConvertor : fieldConvertors) {
        fieldConvertor.convert(source, target);
      }
    }
  }
}
