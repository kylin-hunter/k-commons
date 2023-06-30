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
package io.github.kylinhunter.commons.utils.properties;

import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.name.NameRule;
import io.github.kylinhunter.commons.name.NameUtils;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-08 15:20
 */
@Setter
@RequiredArgsConstructor
public class DefaultKeyCorrector implements KeyCorrector {

  @Override
  public Object correct(Object key, NameRule nameRule) {
    if (key instanceof String) {
      String newKey = (String) key;
      String[] keyPath = StringUtil.split(newKey, '.');
      if (keyPath.length <= 1) {
        newKey = NameUtils.convert(newKey, nameRule);
      } else {
        StringJoiner stringJoiner = new StringJoiner(".");
        for (String s : keyPath) {
          stringJoiner.add(NameUtils.convert(s, nameRule));
        }
        newKey = stringJoiner.toString();
      }
      return newKey;
    }

    return key;
  }
}
