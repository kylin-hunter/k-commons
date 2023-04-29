package io.github.kylinhunter.commons.io;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-22 19:42
 */
@Getter
public enum IOCase implements Serializable {
  SENSITIVE("Sensitive", true),

  INSENSITIVE("Insensitive", false),

  SYSTEM("System", !IOHelper.isSystemWindows());

  private final String name;

  private final transient boolean sensitive;

  private static final long serialVersionUID = -7584598702463792081L;

  IOCase(final String name, final boolean sensitive) {
    this.name = name;
    this.sensitive = sensitive;
  }

  /**
   * @param str1 str1
   * @param str2 str2
   * @return int
   * @title checkCompareTo
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:07
   */
  public int checkCompareTo(final String str1, final String str2) {
    Objects.requireNonNull(str1, "str1");
    Objects.requireNonNull(str2, "str2");
    return sensitive ? str1.compareTo(str2) : str1.compareToIgnoreCase(str2);
  }

  /**
   * @param str1 str1
   * @param str2 str2
   * @return boolean
   * @title checkEquals
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:07
   */
  public boolean checkEquals(final String str1, final String str2) {
    Objects.requireNonNull(str1, "str1");
    Objects.requireNonNull(str2, "str2");
    return sensitive ? str1.equals(str2) : str1.equalsIgnoreCase(str2);
  }

  /**
   * @param str str
   * @param start start
   * @return boolean
   * @title checkStartsWith
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:07
   */
  public boolean checkStartsWith(final String str, final String start) {
    return str != null
        && start != null
        && str.regionMatches(!sensitive, 0, start, 0, start.length());
  }

  /**
   * @param str str
   * @param end end
   * @return boolean
   * @title checkEndsWith
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:08
   */
  public boolean checkEndsWith(final String str, final String end) {
    if (str == null || end == null) {
      return false;
    }
    final int endLen = end.length();
    return str.regionMatches(!sensitive, str.length() - endLen, end, 0, endLen);
  }

  /**
   * @param str str
   * @param strStartIndex strStartIndex
   * @param search search
   * @return int
   * @title checkIndexOf
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:08
   */
  public int checkIndexOf(final String str, final int strStartIndex, final String search) {
    final int endIndex = str.length() - search.length();
    if (endIndex >= strStartIndex) {
      for (int i = strStartIndex; i <= endIndex; i++) {
        if (checkRegionMatches(str, i, search)) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * @param str str
   * @param strStartIndex strStartIndex
   * @param search search
   * @return boolean
   * @title checkRegionMatches
   * @description
   * @author BiJi'an
   * @date 2023-04-22 20:08
   */
  public boolean checkRegionMatches(
      final String str, final int strStartIndex, final String search) {
    return str.regionMatches(!sensitive, strStartIndex, search, 0, search.length());
  }
}
