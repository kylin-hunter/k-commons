package io.github.kylinhunter.commons.i18n;

import io.github.kylinhunter.commons.collections.ArrayUtils;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-23 10:03
 */
public class I18nUtils {

  @Getter
  public static Locale currentLocale;
  public static ResourceBundle bundle;

  /**
   * @param baseName baseName
   * @param language language
   * @param country  country
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-06-23 10:21
   */
  public static void init(String baseName, String language, String country) {
    bundle = ResourceBundle.getBundle(baseName, new Locale(language, country));
    currentLocale = bundle.getLocale();
  }

  /**
   * @param language language
   * @param country  country
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-06-23 10:21
   */
  public static void init(String language, String country) {
    init("i18n/k-i18n", language, country);
  }

  /**
   * @param key key
   * @return java.lang.String
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2023-06-23 10:23
   */
  public static String get(String key) {
    return get(key, null);
  }

  /**
   * @param key   key
   * @param param param
   * @return java.lang.String
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2023-06-23 10:45
   */
  public static String get(String key, Object[] param) {
    if (StringUtil.isEmpty(key)) {
      return StringUtil.EMPTY;
    }
    String value = bundle.getString(key);

    if (!ArrayUtils.isEmpty(param)) {
      return MessageFormat.format(value, param);

    }
    return value;
  }

}