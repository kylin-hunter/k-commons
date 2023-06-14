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
package io.github.kylinhunter.commons.util;

import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.regex.Pattern;

/**
 * @author BiJi'an
 * @description
 * @date 2022/10/22
 */
public class HexUtils {

  private static final String HEX_STR = "0123456789ABCDEF";
  private static final char[] HEX_ARRAY = HEX_STR.toCharArray();
  private static final Pattern PATTERN_HEX = Pattern.compile("^[A-Fa-f0-9]+$");

  /**
   * @param bytes bytes
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2022-11-23 22:06
   */
  public static String toString(byte[] bytes) {
    return toString(bytes, 0, bytes.length);
  }

  /**
   * @param bytes bytes
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2022-10-04 00:06
   */
  public static String toString(byte[] bytes, int off, int len) {

    if (check(bytes, off, len)) {
      return StringUtil.EMPTY;
    }
    char[] hexChars = new char[len * 2];
    int j;
    for (int i = 0; i < len; i++) {
      j = off + i;
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }

  /**
   * @param hex hex
   * @return byte[]
   * @title hexStringToByte
   * @description
   * @author BiJi'an
   * @date 2022-10-30 00:29
   */
  public static byte[] toBytes(String hex) {
    int len = (hex.length() / 2);
    byte[] result = new byte[len];
    for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = (byte) (Str2Hex(hex.charAt(pos)) << 4 | Str2Hex(hex.charAt(pos + 1)));
    }
    return result;
  }

  static byte Str2Hex(char ch) {
    if ((ch >= '0') & (ch <= '9')) {
      return (byte) (ch - '0');
    } else if ((ch >= 'a') & (ch <= 'f')) {
      return (byte) (ch - 'a' + 10);
    } else if ((ch >= 'A') & (ch <= 'F')) {
      return (byte) (ch - 'A' + 10);
    }
    return 0;
  }

  /**
   * @param bytes bytes
   * @param off off
   * @param len len
   * @return boolean
   * @title check
   * @description
   * @author BiJi'an
   * @date 2022-10-27 16:33
   */
  private static boolean check(byte[] bytes, int off, int len) {
    if (bytes == null) {
      throw new NullPointerException();
    } else if ((off < 0)
        || (off > bytes.length)
        || (len < 0)
        || ((off + len) > bytes.length)
        || ((off + len) < 0)) {
      throw new IndexOutOfBoundsException();
    } else {
      return len == 0;
    }
  }

  /**
   * @param str str
   * @return boolean
   * @title isHexStr
   * @description
   * @author BiJi'an
   * @date 2022-10-27 16:33
   */
  public static boolean isHex(String str) {
    return PATTERN_HEX.matcher(str).matches();
  }
}
