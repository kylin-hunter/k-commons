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

import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.lang.strings.StringUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-21 19:55
 */
public class NameUtils {

  /**
   * @param str str
   * @return io.github.kylinhunter.commons.name.NamePair
   * @title toNamePair
   * @description
   * @author BiJi'an
   * @date 2023-02-08 01:11
   */
  public static NamePair toNamePair(String str) {
    return toNamePair(str, CamelFormat.LOWER, SnakeFormat.LOWWER_UNDERSCORE);
  }

  /**
   * @param str str
   * @param camelFormat camelFormat
   * @return io.github.kylinhunter.commons.name.NamePair
   * @title toNamePair
   * @description
   * @author BiJi'an
   * @date 2023-02-08 01:11
   */
  public static NamePair toNamePair(String str, CamelFormat camelFormat) {
    return toNamePair(str, camelFormat, SnakeFormat.LOWWER_UNDERSCORE);
  }

  /**
   * @param str str
   * @param snakeFormat snakeFormat
   * @return io.github.kylinhunter.commons.name.NamePair
   * @title toNamePair
   * @description
   * @author BiJi'an
   * @date 2023-02-08 01:11
   */
  public static NamePair toNamePair(String str, SnakeFormat snakeFormat) {
    return toNamePair(str, CamelFormat.LOWER, snakeFormat);
  }

  /**
   * @param str str
   * @return io.github.kylinhunter.commons.name.NamePair
   * @title toNamePair
   * @description
   * @author BiJi'an
   * @date 2022-11-22 01:23
   */
  public static NamePair toNamePair(String str, CamelFormat camelFormat, SnakeFormat snakeFormat) {

    if (!StringUtil.isEmpty(str)) {
      NamePair namePair = new NamePair();
      if (SnakeToCamelUtils.isSnake(str)) {
        namePair.setSnake(str);
        namePair.setCamel(SnakeToCamelUtils.convert(str, camelFormat));
      } else {
        namePair.setCamel(str);
        namePair.setSnake(CamelToSnakeUtils.convert(str, snakeFormat));
      }

      return namePair;
    }
    throw new ParamException("str is emtpty");
  }

  /**
   * @param name name
   * @param nameRule nameRule
   * @return java.lang.String
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2023-03-19 20:34
   */
  public static String convert(String name, NameRule nameRule) {
    if (nameRule.isCamel()) {
      return SnakeToCamelUtils.convert(name, nameRule.getCamelFormat());
    } else {
      return CamelToSnakeUtils.convert(name, nameRule.getSnakeFormat());
    }
  }
}
