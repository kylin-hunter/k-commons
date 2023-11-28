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
public class SqlFileReader {

  /**
   * @param path path
   * @return java.util.List<java.lang.String>
   * @title read
   * @description read
   * @author BiJi'an
   * @date 2023-11-29 00:30
   */
  public static List<String> read(String path) {
    List<String> sqlLines = ListUtils.newArrayList();
    List<String> lines = ResourceHelper.readLines(path, PathType.CLASSPATH, Charsets.UTF_8);
    if (!CollectionUtils.isEmpty(lines)) {
      StringBuilder buf = new StringBuilder();
      for (String line : lines) {
        if (!StringUtil.isEmpty(line)) {
          line = line.trim();
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

}