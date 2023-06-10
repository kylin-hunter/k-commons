package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.exception.embed.GeneralException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 01:05
 */
public class ClassUtil {
  public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

  public static final char PACKAGE_SEPARATOR_CHAR = '.';

  private static final Map<String, String> abbreviationMap;

  /** Maps an abbreviation used in array class names to corresponding primitive class name. */
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

  /**
   * @title loadClass
   * @description loadClass
   * @author BiJi'an
   * @param className className
   * @date 2023-06-11 01:04
   * @return java.lang.Class<T>
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
  /**
   * @title getShortClassName
   * @description getShortClassName
   * @author BiJi'an
   * @param className className
   * @date 2023-06-11 01:06
   * @return java.lang.String
   */
  public static String getShortClassName(String className) {
    if (StringUtils.isEmpty(className)) {
      return StringUtils.EMPTY;
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
   * @title getShortClassName
   * @description getShortClassName
   * @author BiJi'an
   * @param cls cls
   * @date 2023-06-11 01:16
   * @return java.lang.String
   */
  public static String getShortClassName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    }
    return getShortClassName(cls.getName());
  }
  /**
   * @title getPackageName
   * @description getPackageName
   * @author BiJi'an
   * @param cls cls
   * @date 2023-06-11 01:36
   * @return java.lang.String
   */
  public static String getPackageName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    }
    return getPackageName(cls.getName());
  }
  /**
   * @title getPackageName
   * @description getPackageName
   * @author BiJi'an
   * @param className className
   * @date 2023-06-11 01:36
   * @return java.lang.String
   */
  public static String getPackageName(String className) {
    if (StringUtils.isEmpty(className)) {
      return StringUtils.EMPTY;
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
      return StringUtils.EMPTY;
    }
    return className.substring(0, i);
  }
}
