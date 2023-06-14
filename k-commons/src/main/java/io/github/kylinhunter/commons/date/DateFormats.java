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

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-20 23:53
 */
public class DateFormats {
  public static final DateFormat DATE = new DateFormat(DatePatterns.DATE);

  public static final DateFormat DATE_SLASH = new DateFormat(DatePatterns.SLASH_DATE);

  public static final DateFormat DATE_NO_SEP = new DateFormat(DatePatterns.BARE_DATE);

  public static final DateFormat HOUR = new DateFormat(DatePatterns.DATE_HOUR);

  public static final DateFormat HOUR_NO_SEP = new DateFormat(DatePatterns.BARE_DATE_HOUR);

  public static final DateFormat DATE_TIME = new DateFormat(DatePatterns.DATE_TIME);

  public static final DateFormat DATE_TIME_NO_SEP = new DateFormat(DatePatterns.BARE_DATE_TIME);

  public static final DateFormat DATE_TIME_MILLIS = new DateFormat(DatePatterns.DATE_MILLIS);

  public static final DateFormat DATE_TIME_MILLIS_NO_SEP =
      new DateFormat(DatePatterns.BARE_DATE_MILLIS);
}
