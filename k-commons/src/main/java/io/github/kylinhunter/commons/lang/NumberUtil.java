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
package io.github.kylinhunter.commons.lang;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-10 00:53
 */
public class NumberUtil {
  /**
   * @param str str
   * @return short
   * @title toShort
   * @description toShort
   * @author BiJi'an
   * @date 2023-06-10 00:57
   */
  public static short toShort(final String str) {
    return toShort(str, (short) 0);
  }

  /**
   * @param str str
   * @param defaultValue defaultValue
   * @return short
   * @title toShort
   * @description toShort
   * @author BiJi'an
   * @date 2023-06-10 00:57
   */
  public static short toShort(final String str, final short defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    try {
      return Short.parseShort(str);
    } catch (final NumberFormatException nfe) {
      return defaultValue;
    }
  }

  /**
   * @param str str
   * @return float
   * @title toFloat
   * @description toFloat
   * @author BiJi'an
   * @date 2023-06-10 00:56
   */
  public static float toFloat(final String str) {
    return toFloat(str, 0.0f);
  }

  /**
   * @param str str
   * @param defaultValue defaultValue
   * @return float
   * @title toFloat
   * @description toFloat
   * @author BiJi'an
   * @date 2023-06-10 00:56
   */
  public static float toFloat(final String str, final float defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    try {
      return Float.parseFloat(str);
    } catch (final NumberFormatException nfe) {
      return defaultValue;
    }
  }

  /**
   * @param str str
   * @return double
   * @title toDouble
   * @description toDouble
   * @author BiJi'an
   * @date 2023-06-10 00:54
   */
  public static double toDouble(final String str) {
    return toDouble(str, 0.0d);
  }

  /**
   * @param str str
   * @param defaultValue defaultValue
   * @return double
   * @title toDouble
   * @description toDouble
   * @author BiJi'an
   * @date 2023-06-10 00:54
   */
  public static double toDouble(final String str, final double defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    try {
      return Double.parseDouble(str);
    } catch (final NumberFormatException nfe) {
      return defaultValue;
    }
  }

  /**
   * @param str str
   * @return int
   * @title toInt
   * @description toInt
   * @author BiJi'an
   * @date 2023-06-10 00:54
   */
  public static int toInt(final String str) {
    return toInt(str, 0);
  }

  /**
   * @param str str
   * @param defaultValue defaultValue
   * @return int
   * @title toInt
   * @description toInt
   * @author BiJi'an
   * @date 2023-06-10 00:54
   */
  public static int toInt(final String str, final int defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(str);
    } catch (final NumberFormatException nfe) {
      return defaultValue;
    }
  }

  /**
   * @param str str
   * @return long
   * @title toLong
   * @description toLong
   * @author BiJi'an
   * @date 2023-06-10 00:54
   */
  public static long toLong(final String str) {
    return toLong(str, 0L);
  }

  /**
   * @param str str
   * @param defaultValue defaultValue
   * @return long
   * @title toLong
   * @description toLong
   * @author BiJi'an
   * @date 2023-06-10 00:55
   */
  public static long toLong(final String str, final long defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    try {
      return Long.parseLong(str);
    } catch (final NumberFormatException nfe) {
      return defaultValue;
    }
  }
}
