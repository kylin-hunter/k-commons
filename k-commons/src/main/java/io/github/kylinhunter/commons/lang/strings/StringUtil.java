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
package io.github.kylinhunter.commons.lang.strings;

import java.util.ArrayList;
import java.util.List;

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
  /**
   * @title split
   * @description split
   * @author BiJi'an
   * @param str str
   * @param separatorChar separatorChar
   * @date 2023-06-11 00:16
   * @return java.lang.String[]
   */
  public static String[] split(final String str, final char separatorChar) {
    return splitWorker(str, separatorChar, false);
  }
  /**
   * @title splitWorker
   * @description splitWorker
   * @author BiJi'an
   * @param str str
   * @param separatorChar separatorChar
   * @param preserveAllTokens preserveAllTokens
   * @date 2023-06-11 00:16
   * @return java.lang.String[]
   */
  private static String[] splitWorker(
      final String str, final char separatorChar, final boolean preserveAllTokens) {
    // Performance tuned for 2.0 (JDK1.4)

    if (str == null) {
      return null;
    }
    final int len = str.length();
    if (len == 0) {
      return EMPTY_STRING_ARRAY;
    }
    final List<String> list = new ArrayList<>();
    int i = 0;
    int start = 0;
    boolean match = false;
    boolean lastMatch = false;
    while (i < len) {
      if (str.charAt(i) == separatorChar) {
        if (match || preserveAllTokens) {
          list.add(str.substring(start, i));
          match = false;
          lastMatch = true;
        }
        start = ++i;
        continue;
      }
      lastMatch = false;
      match = true;
      i++;
    }
    if (match || preserveAllTokens && lastMatch) {
      list.add(str.substring(start, i));
    }
    return list.toArray(EMPTY_STRING_ARRAY);
  }
}
