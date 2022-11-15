package com.kylinhunter.plat.commons.util.date;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-20 23:53
 **/
public class DateFormats {
    public static DateFormat DATE = new DateFormat(DatePatterns.DATE);

    public static DateFormat DATE_SLASH = new DateFormat(DatePatterns.DATE_SLASH);

    public static DateFormat DATE_NO_SEP = new DateFormat(DatePatterns.DATE_NO_SEP);

    public static DateFormat HOUR = new DateFormat(DatePatterns.HOUR);

    public static DateFormat HOUR_NO_SEP = new DateFormat(DatePatterns.HOUR_NO_SEP);

    public static DateFormat DATE_TIME = new DateFormat(DatePatterns.DATE_TIME);

    public static DateFormat DATE_TIME_NO_SEP = new DateFormat(DatePatterns.DATE_TIME_NO_SEP);

    public static DateFormat DATE_TIME_MILLIS = new DateFormat(DatePatterns.DATE_TIME_MILLIS);

    public static DateFormat DATE_TIME_MILLIS_NO_SEP = new DateFormat(DatePatterns.DATE_TIME_MILLIS_NO_SEP);
}
