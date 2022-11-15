package com.kylinhunter.plat.commons.util.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    void test1() {

        LocalDateTime localDateTime = DateUtils.parse("1970-01-02", DateFormats.DATE);
        assertEquals("1970-01-02", DateUtils.format(localDateTime, DateFormats.DATE));
        localDateTime = DateUtils.parse("19700102", DateFormats.DATE_NO_SEP);
        assertEquals("19700102", DateUtils.format(localDateTime, DateFormats.DATE_NO_SEP));

        localDateTime = DateUtils.parse("1970-01-02 13", DateFormats.HOUR);
        assertEquals("1970-01-02 13", DateUtils.format(localDateTime, DateFormats.HOUR));
        localDateTime = DateUtils.parse("1970010213", DateFormats.HOUR_NO_SEP);
        assertEquals("1970010213", DateUtils.format(localDateTime, DateFormats.HOUR_NO_SEP));

        localDateTime = DateUtils.parse("1970-01-02 13:14:15", DateFormats.DATE_TIME);
        assertEquals("1970-01-02 13:14:15", DateUtils.format(localDateTime, DateFormats.DATE_TIME));
        localDateTime = DateUtils.parse("19700102131415", DateFormats.DATE_TIME_NO_SEP);
        assertEquals("19700102131415", DateUtils.format(localDateTime, DateFormats.DATE_TIME_NO_SEP));

        localDateTime = DateUtils.parse("1970-01-02 13:14:15:999", DateFormats.DATE_TIME_MILLIS);

        assertEquals("1970-01-02 13:14:15:999",
                DateUtils.format(localDateTime, DateFormats.DATE_TIME_MILLIS));

        localDateTime = DateUtils.parse("19700102131415999", DateFormats.DATE_TIME_MILLIS_NO_SEP);

        assertEquals("19700102131415999",
                DateUtils.format(localDateTime, DateFormats.DATE_TIME_MILLIS_NO_SEP));

        localDateTime = DateUtils.parse("1970-01-02 13:14:15");
        assertEquals("1970-01-02 13:14:15", DateUtils.format(localDateTime));

        assertEquals(19, DateUtils.format().length());

        localDateTime = DateUtils.parse("1970-01-02", DateFormats.DATE);
        assertEquals("1970-01-02", DateUtils.formatDate(localDateTime));

        assertEquals(10, DateUtils.formatDate().length());

    }

    @Test
    void test2() {
        LocalDateTime localDateTime = DateUtils.parse("1970-01-02 13:14:15:999", DateFormats.DATE_TIME_MILLIS);

        Date date = DateUtils.toDate(localDateTime);

        Assertions.assertEquals(localDateTime, DateUtils.toLocalDateTime(date));

        Assertions.assertEquals(date.getTime(), DateUtils.toMilli(localDateTime));

        Assertions.assertEquals(localDateTime, DateUtils.toLocalDateTime(date.getTime()));

    }
}