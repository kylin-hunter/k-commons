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

import io.github.kylinhunter.commons.exception.embed.ParamException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateFormat {

  private String pattern;
  private DateTimeFormatter dateTimeFormatter;

  private boolean parseByLocalDate;

  DateFormat(String pattern) {
    this.pattern = pattern;

    if (pattern.equals(DatePatterns.BARE_DATE_MILLIS)) {
      this.dateTimeFormatter =
          new DateTimeFormatterBuilder()
              .appendPattern(DatePatterns.BARE_DATE_TIME)
              .appendValue(ChronoField.MILLI_OF_SECOND, 3)
              .toFormatter();
    } else {
      this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    }
    this.parseByLocalDate =
        pattern.equals(DatePatterns.DATE)
            || pattern.equals(DatePatterns.SLASH_DATE)
            || pattern.equals(DatePatterns.BARE_DATE);
  }

  public String format(TemporalAccessor temporal) {
    try {
      return dateTimeFormatter.format(temporal);
    } catch (Exception e) {
      throw new ParamException("format error:" + temporal, e);
    }
  }

  public LocalDateTime parse(CharSequence text) {
    try {
      if (parseByLocalDate) {
        return LocalDate.parse(text, this.dateTimeFormatter).atStartOfDay();
      } else {
        return LocalDateTime.parse(text, this.dateTimeFormatter);
      }

    } catch (Exception e) {
      throw new ParamException("parse error:" + text, e);
    }
  }
}
