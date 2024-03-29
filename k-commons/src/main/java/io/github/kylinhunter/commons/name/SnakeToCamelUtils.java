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

import io.github.kylinhunter.commons.lang.strings.CharConst;
import io.github.kylinhunter.commons.lang.strings.StringConst;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Arrays;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 14:39
 */
@Data
public class SnakeToCamelUtils {

  /**
   * @param name name
   * @return java.lang.String
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2022-11-22 01:25
   */
  public static String convert(String name) {
    return convert(name, CamelFormat.LOWER);
  }

  /**
   * @param name name
   * @param camelFormat camelFormat
   * @return java.lang.String
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2023-02-08 00:25
   */
  public static String convert(String name, CamelFormat camelFormat) {
    if (StringUtil.isBlank(name)) {
      return StringConst.EMPTY;
    }
    if (!SnakeToCamelUtils.isSnake(name)) {
      if (camelFormat == CamelFormat.LOWER) {
        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
      } else {
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
      }
      return name;
    }

    StringBuilder result = new StringBuilder();

    char sep = name.indexOf(StringConst.UNDERSCORE) > 0 ? CharConst.UNDERSCORE : CharConst.HYPHEN;
    String[] camels = name.split(sep + "");
    Arrays.stream(camels)
        .filter(camel -> !StringUtil.isBlank(camel))
        .forEach(
            camel ->
                result
                    .append(Character.toUpperCase(camel.charAt(0)))
                    .append(camel.substring(1).toLowerCase()));
    if (camelFormat == CamelFormat.LOWER) {
      result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
    }
    return result.toString();
  }

  /**
   * @param name name
   * @return boolean
   * @title isSnake
   * @description
   * @author BiJi'an
   * @date 2023-02-08 00:44
   */
  public static boolean isSnake(String name) {
    return name.indexOf(CharConst.UNDERSCORE) > 0 || name.indexOf(CharConst.HYPHEN) > 0;
  }
}
