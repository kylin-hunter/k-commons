package io.github.kylinhunter.commons.date;

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

    /*  format part */

    /**
     * @param localDateTime localDateTime
     * @param format        format
     * @return java.lang.String
     * @title format
     * @description
     * @author BiJi'an
     * @date 2022-11-21 11:21
     */
    public static String format(LocalDateTime localDateTime, DateFormat format) {
        return format.format(localDateTime);
    }

    /**
     * @param localDateTime localDateTime
     * @return java.lang.String
     * @title format
     * @description
     * @author BiJi'an
     * @date 2022-11-21 11:21
     */
    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, DateFormats.DATE_TIME);
    }

    /**
     * @return java.lang.String
     * @title format
     * @description
     * @author BiJi'an
     * @date 2022-11-21 11:20
     */
    public static String formatNow() {
        return format(LocalDateTime.now(), DateFormats.DATE_TIME);
    }

    /**
     * @param localDateTime localDateTime
     * @return java.lang.String
     * @title formatDate
     * @description
     * @author BiJi'an
     * @date 2022-11-21 11:22
     */
    public static String formatWithDate(LocalDateTime localDateTime) {
        return format(localDateTime, DateFormats.DATE);
    }

    /**
     * @return java.lang.String
     * @title formatDate
     * @description
     * @author BiJi'an
     * @date 2022-11-21 11:22
     */
    public static String formatNowWithDate() {
        return format(LocalDateTime.now(), DateFormats.DATE);
    }



    /*  parse part */

    /**
     * @param date   date
     * @param format format
     * @return java.time.LocalDateTime
     * @title parse
     * @description
     * @author BiJi'an
     * @date 2022-11-21 11:32
     */
    public static LocalDateTime parse(String date, DateFormat format) {
        return format.parse(date);
    }

    /**
     * @param date date
     * @return java.time.LocalDateTime
     * @title parse
     * @description
     * @author BiJi'an
     * @date 2022-11-21 11:32
     */
    public static LocalDateTime parse(String date) {
        return parse(date, DateFormats.DATE_TIME);
    }

    /**
     * @param date date
     * @return java.time.LocalDateTime
     * @title parseDate
     * @title parseDate
     * @description
     * @author BiJi'an
     * @date 2022-11-21 11:33
     */
    public static LocalDateTime parseWithDate(String date) {
        return parse(date, DateFormats.DATE);
    }

    // ==========
    static ZoneOffset zoneOffset = ZoneOffset.ofHours(8);

    public static long toMilli(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return localDateTime.toInstant(zoneOffset).toEpochMilli();
    }

    public static long toMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(zoneOffset).toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long date, ZoneOffset zoneOffset) {
        return new Date(date).toInstant().atOffset(zoneOffset).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(long date) {
        return new Date(date).toInstant().atOffset(zoneOffset).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(Date date, ZoneOffset zoneOffset) {
        return date.toInstant().atOffset(zoneOffset).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atOffset(zoneOffset).toLocalDateTime();
    }

    public static Date toDate(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return Date.from(localDateTime.atZone(zoneOffset).toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(zoneOffset).toInstant());
    }

}
