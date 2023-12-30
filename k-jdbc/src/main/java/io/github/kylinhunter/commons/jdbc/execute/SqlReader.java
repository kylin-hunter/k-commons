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
package io.github.kylinhunter.commons.jdbc.execute;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.io.Charsets;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.ResourceHelper.PathType;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-29 00:27
 */
public class SqlReader {

  /**
   * @param path path
   * @return java.util.List<java.lang.String>
   * @title read
   * @description read
   * @author BiJi'an
   * @date 2023-11-29 00:30
   */
  public static List<String> readSqls(String path) {
    List<String> sqlLines = ListUtils.newArrayList();
    List<String> lines = ResourceHelper.readLines(path, PathType.CLASSPATH, Charsets.UTF_8);
    if (!CollectionUtils.isEmpty(lines)) {
      StringBuilder buf = new StringBuilder();
      for (String line : lines) {
        if (!StringUtil.isEmpty(line)) {
          line = " " + line.trim();
          if (line.endsWith(";")) {
            buf.append(line, 0, line.length() - 1);
            sqlLines.add(buf.toString());
            buf.setLength(0);
          } else {
            buf.append(line);
          }
        }
      }
    }
    return sqlLines;
  }

  /**
   * @param path path
   * @return java.lang.String
   * @title readFirst
   * @description readFirst
   * @author BiJi'an
   * @date 2023-12-10 16:39
   */
  public static String readSql(String path) {
    List<String> sqls = readSqls(path);
    if (!CollectionUtils.isEmpty(sqls)) {
      return sqls.get(0);
    }
    return StringUtil.EMPTY;
  }
}
