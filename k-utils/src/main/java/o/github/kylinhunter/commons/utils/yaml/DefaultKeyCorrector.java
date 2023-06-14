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
package o.github.kylinhunter.commons.utils.yaml;

import io.github.kylinhunter.commons.name.NameRule;
import io.github.kylinhunter.commons.name.NameUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
  private static final Pattern PATTERN_PROP_NAME =
      Pattern.compile("^(\\s*-*\\s*)(\\w+-*\\w+)\\s*:");

  @Override
  public String correct(String text, NameRule nameRule) {
    String[] lines = text.split("(\\r?\\n)+");
    StringBuilder stringBuilder = new StringBuilder();
    for (String line : lines) {
      stringBuilder.append(correntLine(line, nameRule)).append("\n");
    }
    return stringBuilder.toString();
  }

  private String correntLine(String line, NameRule nameRule) {
    if (nameRule != null) {
      Matcher matcher = PATTERN_PROP_NAME.matcher(line);
      if (matcher.find()) {
        if (matcher.groupCount() > 1) {
          String group1 = matcher.group(1);
          String group2 = matcher.group(2);
          return matcher.replaceAll(group1 + NameUtils.convert(group2, nameRule) + ":");
        }
      }
    }
    return line;
  }
}
