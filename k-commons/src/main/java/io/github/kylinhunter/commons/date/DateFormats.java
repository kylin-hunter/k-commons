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
