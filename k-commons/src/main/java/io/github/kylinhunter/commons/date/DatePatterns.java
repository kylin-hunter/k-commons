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
package io.github.kylinhunter.commons.date;

import io.github.kylinhunter.commons.sys.KGenerated;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-20 23:12
 */
@KGenerated
public class DatePatterns {
  public static final String DATE = "yyyy-MM-dd";
  public static final String DATE_HOUR = "yyyy-MM-dd HH";
  public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_MILLIS = "yyyy-MM-dd HH:mm:ss:SSS";

  public static final String BARE_DATE = "yyyyMMdd";
  public static final String BARE_DATE_HOUR = "yyyyMMddHH";
  public static final String BARE_DATE_TIME = "yyyyMMddHHmmss";
  public static final String BARE_DATE_MILLIS = "yyyyMMddHHmmssSSS";

  public static final String SLASH_DATE = "yyyy/MM/dd";
}
