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
  private static Locale currentLocale;
  private static ResourceBundle bundle;

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
