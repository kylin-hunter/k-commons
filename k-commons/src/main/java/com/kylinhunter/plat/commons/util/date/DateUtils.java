package com.kylinhunter.plat.commons.util.date;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 01:23
 **/
@Slf4j
public class DateUtils {

    public static long toMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long date) {
        return new Date(date).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
    }

    public static String format(LocalDateTime localDateTime, DateFormat format) {
        return format.format(localDateTime);
    }

    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, DateFormats.DATE_TIME);
    }

    public static String format() {
        return format(LocalDateTime.now(), DateFormats.DATE_TIME);
    }

    public static String formatDate(LocalDateTime localDateTime) {
        return format(localDateTime, DateFormats.DATE);
    }

    public static String formatDate() {
        return format(LocalDateTime.now(), DateFormats.DATE);
    }

    public static LocalDateTime parse(String date, DateFormat format) {
        return format.parse(date);
    }

    public static LocalDateTime parse(String date) {
        return parse(date, DateFormats.DATE_TIME);
    }

    public static LocalDateTime parseDate(String date) {
        return parse(date, DateFormats.DATE);
    }

}
