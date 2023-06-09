package io.github.kylinhunter.commons.lang.strings;

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
   * @title defaultIfEmpty
   * @description
   * @author BiJi'an
   * @date 2023-04-22 11:17
   */
  public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
    return isEmpty(str) ? defaultStr : str;
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

  /**
   * @param str str
   * @param start start
   * @return java.lang.String
   * @title substring
   * @description substring
   * @author BiJi'an
   * @date 2023-06-10 00:31
   */
  public static String substring(final String str, int start) {
    if (str == null) {
      return null;
    }

    // handle negatives, which means last n characters
    if (start < 0) {
      start = str.length() + start; // remember start is negative
    }

    if (start < 0) {
      start = 0;
    }
    if (start > str.length()) {
      return EMPTY;
    }

    return str.substring(start);
  }

  /**
   * @param str str
   * @param start start
   * @param end end
   * @return java.lang.String
   * @title substring
   * @description substring
   * @author BiJi'an
   * @date 2023-06-10 00:31
   */
  public static String substring(final String str, int start, int end) {
    if (str == null) {
      return null;
    }

    // handle negatives
    if (end < 0) {
      end = str.length() + end; // remember end is negative
    }
    if (start < 0) {
      start = str.length() + start; // remember start is negative
    }

    // check length next
    if (end > str.length()) {
      end = str.length();
    }

    // if start is greater than end, return ""
    if (start > end) {
      return EMPTY;
    }

    if (start < 0) {
      start = 0;
    }
    if (end < 0) {
      end = 0;
    }

    return str.substring(start, end);
  }
}
