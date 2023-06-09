package io.github.kylinhunter.commons.util;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-30 18:25
 */
public class FilenameUtils {
  private static final Map<String, String> ACTUAL_EXTENSIONS = MapUtils.newHashMap();

  static {
    ACTUAL_EXTENSIONS.put("z", "tar.z");
    ACTUAL_EXTENSIONS.put("gz", "tar.gz");
  }

  public static String getExtension(final String fileName) {

    String extension = org.apache.commons.io.FilenameUtils.getExtension(fileName);
    if (extension != null && extension.length() > 0) {
      String actualExtension = ACTUAL_EXTENSIONS.get(extension.toLowerCase());
      if (actualExtension != null) {
        if (fileName.toLowerCase().endsWith(actualExtension)) {
          return actualExtension;
        }
      }

      return extension.toLowerCase();
    }
    return StringUtil.EMPTY;
  }
}
