package io.github.kylinhunter.commons.strings;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 11:11
 */
public class StringUtil {
  public static final String EMPTY = "";
  public static final String[] EMPTY_STRING_ARRAY = {};

  /**
   * @param cs cs
   * @return boolean
   * @title isEmpty
   * @description
   * @author BiJi'an
   * @date 2023-04-22 11:17
   */
  public static boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  /**
   * @param str str
   * @param defaultStr defaultStr
   * @return T
   * @title defaultIfBlank
   * @description
   * @author BiJi'an
   * @date 2023-04-22 11:17
   */
  public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
    return isBlank(str) ? defaultStr : str;
  }

  /**
   * @param cs cs
   * @return boolean
   * @title isBlank
   * @description
   * @author BiJi'an
   * @date 2023-04-22 11:17
   */
  public static boolean isBlank(final CharSequence cs) {
    final int strLen = length(cs);
    if (strLen == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * @param cs cs
   * @return int
   * @title length
   * @description
   * @author BiJi'an
   * @date 2023-04-22 11:17
   */
  public static int length(final CharSequence cs) {
    return cs == null ? 0 : cs.length();
  }

  /**
   * @param str str
   * @param defaultStr defaultStr
   * @return T
   * @title defaultIfEmpty
   * @description
   * @author BiJi'an
   * @date 2023-04-22 11:17
   */
  public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
    return isEmpty(str) ? defaultStr : str;
  }

  /**
   * @param str str
   * @return java.lang.String
   * @title defaultString
   * @description
   * @author BiJi'an
   * @date 2023-04-22 11:17
   */
  public static String defaultString(final String str) {
    return defaultString(str, EMPTY);
  }

  /**
   * @param str str
   * @param defaultStr defaultStr
   * @return java.lang.String
   * @title defaultString
   * @description
   * @author BiJi'an
   * @date 2023-04-22 11:17
   */
  public static String defaultString(final String str, final String defaultStr) {
    return str == null ? defaultStr : str;
  }
}
