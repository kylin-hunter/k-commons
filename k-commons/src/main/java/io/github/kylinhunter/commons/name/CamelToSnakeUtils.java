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
package io.github.kylinhunter.commons.name;

import io.github.kylinhunter.commons.lang.strings.StringUtil;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 */
@Data
public class CamelToSnakeUtils {

  /**
   * @param name name
   * @return java.lang.String
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2022-11-22 01:25
   */
  public static String convert(String name) {
    return convert(name, SnakeFormat.LOWWER_UNDERSCORE);
  }

  /**
   * @param name name
   * @param snakeFormat snakeFormat
   * @return java.lang.String
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2023-02-26 11:04
   */
  public static String convert(String name, SnakeFormat snakeFormat) {
    if (StringUtil.isBlank(name)) {
      return StringUtil.EMPTY;
    }
    if (SnakeToCamelUtils.isSnake(name)) {
      return name;
    }

    if (snakeFormat == null) {
      snakeFormat = SnakeFormat.LOWWER_UNDERSCORE;
    }
    int len = name.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = name.charAt(i);
      if (Character.isUpperCase(c) && i > 0) {
        sb.append(snakeFormat.seperator);
      }
      if (snakeFormat.lower) {
        sb.append(Character.toLowerCase(c));
      } else {
        sb.append(Character.toUpperCase(c));
      }
    }
    return sb.toString();
  }
}
