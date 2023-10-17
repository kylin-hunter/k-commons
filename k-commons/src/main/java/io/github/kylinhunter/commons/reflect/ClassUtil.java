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
package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:05
 */
public class ClassUtil {

  public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

  public static final char PACKAGE_SEPARATOR_CHAR = '.';

  private static final Map<String, String> abbreviationMap;

  /**
   * Maps an abbreviation used in array class names to corresponding primitive class name.
   */
  private static final Map<String, String> reverseAbbreviationMap;

  // Feed abbreviation maps
  static {
    final Map<String, String> m = new HashMap<>();
    m.put("int", "I");
    m.put("boolean", "Z");
    m.put("float", "F");
    m.put("long", "J");
    m.put("short", "S");
    m.put("byte", "B");
    m.put("double", "D");
    m.put("char", "C");
    final Map<String, String> r = new HashMap<>();
    for (final Map.Entry<String, String> e : m.entrySet()) {
      r.put(e.getValue(), e.getKey());
    }
    abbreviationMap = Collections.unmodifiableMap(m);
    reverseAbbreviationMap = Collections.unmodifiableMap(r);
  }

  private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();

  static {
    primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
    primitiveWrapperMap.put(Byte.TYPE, Byte.class);
    primitiveWrapperMap.put(Character.TYPE, Character.class);
    primitiveWrapperMap.put(Short.TYPE, Short.class);
    primitiveWrapperMap.put(Integer.TYPE, Integer.class);
    primitiveWrapperMap.put(Long.TYPE, Long.class);
    primitiveWrapperMap.put(Double.TYPE, Double.class);
    primitiveWrapperMap.put(Float.TYPE, Float.class);
    primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
  }

  private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<>();

  static {
    for (final Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperMap.entrySet()) {
      final Class<?> primitiveClass = entry.getKey();
      final Class<?> wrapperClass = entry.getValue();
      if (!primitiveClass.equals(wrapperClass)) {
        wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
      }
    }
  }

  /**
   * @param className className
   * @return java.lang.Class<T>
   * @title loadClass
   * @description loadClass
   * @author BiJi'an
   * @date 2023-06-11 01:04
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> loadClass(String className) {
    try {
      Objects.requireNonNull(className);
      return (Class<T>) Class.forName(className);
    } catch (Exception e) {
      throw new GeneralException("loadClass error ", e);
    }
  }

  public static <T> Class<T> findClass(String className) {
    try {

      return (Class<T>) Class.forName(className);
    } catch (Exception e) {
      // load class error
    }
    return null;
  }

  /**
   * @param className className
   * @return java.lang.String
   * @title getShortClassName
   * @description getShortClassName
   * @author BiJi'an
   * @date 2023-06-11 01:06
   */
  public static String getShortClassName(String className) {
    if (StringUtil.isEmpty(className)) {
      return StringUtil.EMPTY;
    }

    final StringBuilder arrayPrefix = new StringBuilder();

    // Handle array encoding
    if (className.startsWith("[")) {
      while (className.charAt(0) == '[') {
        className = className.substring(1);
        arrayPrefix.append("[]");
      }
      // Strip Object type encoding
      if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
        className = className.substring(1, className.length() - 1);
      }

      if (reverseAbbreviationMap.containsKey(className)) {
        className = reverseAbbreviationMap.get(className);
      }
    }

    final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
    final int innerIdx =
        className.indexOf(INNER_CLASS_SEPARATOR_CHAR, lastDotIdx == -1 ? 0 : lastDotIdx + 1);
    String out = className.substring(lastDotIdx + 1);
    if (innerIdx != -1) {
      out = out.replace(INNER_CLASS_SEPARATOR_CHAR, PACKAGE_SEPARATOR_CHAR);
    }
    return out + arrayPrefix;
  }

  /**
   * @param cls cls
   * @return java.lang.String
   * @title getShortClassName
   * @description getShortClassName
   * @author BiJi'an
   * @date 2023-06-11 01:16
   */
  public static String getShortClassName(final Class<?> cls) {
    if (cls == null) {
      return StringUtil.EMPTY;
    }
    return getShortClassName(cls.getName());
  }

  /**
   * @param cls cls
   * @return java.lang.String
   * @title getPackageName
   * @description getPackageName
   * @author BiJi'an
   * @date 2023-06-11 01:36
   */
  public static String getPackageName(final Class<?> cls) {
    if (cls == null) {
      return StringUtil.EMPTY;
    }
    return getPackageName(cls.getName());
  }

  /**
   * @param className className
   * @return java.lang.String
   * @title getPackageName
   * @description getPackageName
   * @author BiJi'an
   * @date 2023-06-11 01:36
   */
  public static String getPackageName(String className) {
    if (StringUtil.isEmpty(className)) {
      return StringUtil.EMPTY;
    }

    // Strip array encoding
    while (className.charAt(0) == '[') {
      className = className.substring(1);
    }
    // Strip Object type encoding
    if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
      className = className.substring(1);
    }

    final int i = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
    if (i == -1) {
      return StringUtil.EMPTY;
    }
    return className.substring(0, i);
  }

  /**
   * @param type type
   * @return boolean
   * @title isPrimitiveOrWrapper
   * @description isPrimitiveOrWrapper
   * @author BiJi'an
   * @date 2023-06-11 01:50
   */
  public static boolean isPrimitiveOrWrapper(final Class<?> type) {
    if (type == null) {
      return false;
    }
    return type.isPrimitive() || isPrimitiveWrapper(type);
  }

  /**
   * @param type type
   * @return boolean
   * @title isPrimitiveWrapper
   * @description isPrimitiveWrapper
   * @author BiJi'an
   * @date 2023-06-11 01:50
   */
  public static boolean isPrimitiveWrapper(final Class<?> type) {
    return wrapperPrimitiveMap.containsKey(type);
  }
}
