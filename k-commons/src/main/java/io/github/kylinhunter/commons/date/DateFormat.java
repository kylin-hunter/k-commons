package io.github.kylinhunter.commons.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

import io.github.kylinhunter.commons.exception.embed.ParamException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class DateFormat {

    private String pattern;
    private DateTimeFormatter dateTimeFormatter;

    private boolean parseByLocalDate;

    DateFormat(String pattern) {
        this.pattern = pattern;

        if (pattern.equals(DatePatterns.BARE_DATE_MILLIS)) {
            this.dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern(DatePatterns.BARE_DATE_TIME)
                    .appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
        } else {
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        }
        this.parseByLocalDate = pattern.equals(DatePatterns.DATE) || pattern.equals(DatePatterns.SLASH_DATE) || pattern
                .equals(DatePatterns.BARE_DATE);

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
