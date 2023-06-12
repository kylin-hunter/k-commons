package io.github.kylinhunter.commons.io.file;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.io.File;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-30 18:25
 */
public class FilenameUtils {

  private static final String EMPTY_STRING = "";

  private static final int NOT_FOUND = -1;

  private static final char UNIX_SEPARATOR = '/';

  private static final char WINDOWS_SEPARATOR = '\\';
  public static final char EXTENSION_SEPARATOR = '.';
  private static final char SYSTEM_SEPARATOR = File.separatorChar;
  private static final char OTHER_SEPARATOR;

  static {
    if (isSystemWindows()) {
      OTHER_SEPARATOR = UNIX_SEPARATOR;
    } else {
      OTHER_SEPARATOR = WINDOWS_SEPARATOR;
    }
  }
  
  private static final Map<String, String> ACTUAL_EXTENSIONS = MapUtils.newHashMap();

  static {
    ACTUAL_EXTENSIONS.put("z", "tar.z");
    ACTUAL_EXTENSIONS.put("gz", "tar.gz");
  }

  /**
   * @param fileName fileName
   * @return java.lang.String
   * @title getExtension
   * @description getExtension
   * @author BiJi'an
   * @date 2023-06-11 21:26
   */
  public static String getExtension(final String fileName) {

    String extension = _getExtension(fileName);
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

  /**
   * @param fileName fileName
   * @return java.lang.String
   * @title _getExtension
   * @description _getExtension
   * @author BiJi'an
   * @date 2023-06-11 21:26
   */
  private static String _getExtension(final String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      return null;
    }
    final int index = indexOfExtension(fileName);
    if (index == NOT_FOUND) {
      return EMPTY_STRING;
    }
    return fileName.substring(index + 1);
  }

  /**
   * @param fileName fileName
   * @return int
   * @title indexOfExtension
   * @description indexOfExtension
   * @author BiJi'an
   * @date 2023-06-11 21:26
   */
  public static int indexOfExtension(final String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      return NOT_FOUND;
    }
    if (isSystemWindows()) {
      // Special handling for NTFS ADS: Don't accept colon in the fileName.
      final int offset = fileName.indexOf(':', getAdsCriticalOffset(fileName));
      if (offset != -1) {
        throw new IllegalArgumentException("NTFS ADS separator (':') in file name is forbidden.");
      }
    }
    final int extensionPos = fileName.lastIndexOf(EXTENSION_SEPARATOR);
    final int lastSeparator = indexOfLastSeparator(fileName);
    return lastSeparator > extensionPos ? NOT_FOUND : extensionPos;
  }

  /**
   * @param fileName fileName
   * @return int
   * @title getAdsCriticalOffset
   * @description getAdsCriticalOffset
   * @author BiJi'an
   * @date 2023-06-11 21:26
   */
  private static int getAdsCriticalOffset(final String fileName) {
    // Step 1: Remove leading path segments.
    final int offset1 = fileName.lastIndexOf(SYSTEM_SEPARATOR);
    final int offset2 = fileName.lastIndexOf(OTHER_SEPARATOR);
    if (offset1 == -1) {
      if (offset2 == -1) {
        return 0;
      }
      return offset2 + 1;
    }
    if (offset2 == -1) {
      return offset1 + 1;
    }
    return Math.max(offset1, offset2) + 1;
  }

  /**
   * @param fileName fileName
   * @return int
   * @title indexOfLastSeparator
   * @description indexOfLastSeparator
   * @author BiJi'an
   * @date 2023-06-11 21:26
   */
  public static int indexOfLastSeparator(final String fileName) {
    if (fileName == null) {
      return NOT_FOUND;
    }
    final int lastUnixPos = fileName.lastIndexOf(UNIX_SEPARATOR);
    final int lastWindowsPos = fileName.lastIndexOf(WINDOWS_SEPARATOR);
    return Math.max(lastUnixPos, lastWindowsPos);
  }

  /**
   * @return boolean
   * @title isSystemWindows
   * @description isSystemWindows
   * @author BiJi'an
   * @date 2023-06-11 21:26
   */
  public static boolean isSystemWindows() {
    return SYSTEM_SEPARATOR == WINDOWS_SEPARATOR;
  }
}
