package io.github.kylinhunter.commons.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  void testParseAndFormat() {

    String dayStr = "1970-01-02";
    String bareDayStr = "19700102";
    LocalDateTime localDateTime = DateUtils.parse(dayStr, DateFormats.DATE);
    assertEquals(dayStr, DateUtils.format(localDateTime, DateFormats.DATE));
    localDateTime = DateUtils.parse(bareDayStr, DateFormats.DATE_NO_SEP);
    assertEquals(bareDayStr, DateUtils.format(localDateTime, DateFormats.DATE_NO_SEP));

    String hourStr = "1970-01-02 13";
    String bareHourStrBare = "1970010213";
    localDateTime = DateUtils.parse(hourStr, DateFormats.HOUR);
    assertEquals(hourStr, DateUtils.format(localDateTime, DateFormats.HOUR));
    localDateTime = DateUtils.parse(bareHourStrBare, DateFormats.HOUR_NO_SEP);
    assertEquals(bareHourStrBare, DateUtils.format(localDateTime, DateFormats.HOUR_NO_SEP));

    String dayTimeStr = "1970-01-02 13:14:15";
    String bareDayTimeStr = "19700102131415";
    localDateTime = DateUtils.parse(dayTimeStr, DateFormats.DATE_TIME);
    assertEquals(dayTimeStr, DateUtils.format(localDateTime, DateFormats.DATE_TIME));
    localDateTime = DateUtils.parse(bareDayTimeStr, DateFormats.DATE_TIME_NO_SEP);
    assertEquals(bareDayTimeStr, DateUtils.format(localDateTime, DateFormats.DATE_TIME_NO_SEP));

    String dayMillisStr = "1970-01-02 13:14:15:999";
    String bareDayMillisStr = "19700102131415999";
    localDateTime = DateUtils.parse(dayMillisStr, DateFormats.DATE_TIME_MILLIS);
    assertEquals(dayMillisStr, DateUtils.format(localDateTime, DateFormats.DATE_TIME_MILLIS));
    localDateTime = DateUtils.parse(bareDayMillisStr, DateFormats.DATE_TIME_MILLIS_NO_SEP);
    assertEquals(
        bareDayMillisStr, DateUtils.format(localDateTime, DateFormats.DATE_TIME_MILLIS_NO_SEP));

    localDateTime = DateUtils.parse(dayTimeStr);
    assertEquals(dayTimeStr, DateUtils.format(localDateTime));

    localDateTime = DateUtils.parseWithDate(dayStr);
    assertEquals(dayStr, DateUtils.formatWithDate(localDateTime));

    assertEquals(19, DateUtils.formatNow().length());
    assertEquals(10, DateUtils.formatNowWithDate().length());
  }

  @Test
  void test2() {

    String dayMillisStr = "1970-01-02 13:14:15:999";

    LocalDateTime localDateTime = DateUtils.parse(dayMillisStr, DateFormats.DATE_TIME_MILLIS);
    System.out.println(DateUtils.format(localDateTime));
    Date date = DateUtils.toDate(localDateTime);

    assertEquals(localDateTime, DateUtils.toLocalDateTime(date));

    assertEquals(date.getTime(), DateUtils.toMilli(localDateTime));

    assertEquals(localDateTime, DateUtils.toLocalDateTime(date.getTime()));

    ZoneOffset zoneOffset = ZoneOffset.UTC;
    date = DateUtils.toDate(localDateTime, zoneOffset);
    assertEquals(localDateTime, DateUtils.toLocalDateTime(date, zoneOffset));
    assertEquals(date.getTime(), DateUtils.toMilli(localDateTime, zoneOffset));
    assertEquals(localDateTime, DateUtils.toLocalDateTime(date.getTime(), zoneOffset));

    System.out.println(DateUtils.format(DateUtils.toLocalDateTime(date.getTime())));
  }
}
