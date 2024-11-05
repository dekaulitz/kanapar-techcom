package com.github.dekalitz.kanaparktechcom.domain.staticvariable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateConstant {
    public static final String DATE_FORMAT_WITHOUT_DASH = "dd MM yyyy";
    public static final String DATE_FORMAT_CONCATED = "ddMMyyyy";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_WITHOUT_SECONDS = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_FORMAT_ORDER = "dd-MM-yyyy HH:mm";
    public static final String DATE_TIME_FORMAT_ES = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String TIME_FORMAT_WITH_SECOND = "HH:mm:ss";
    public static final String DATE_DISPLAY_FORMAT = "EEEE, dd MMMM yyyy";
    public static final String DATE_DISPLAY_FORMAT_WITHOUT_DAY = "dd MMMM yyyy";
    public static final String DATE_TIME_DISPLAY_FORMAT_WITHOUT_DAY = "dd MMMM yyyy HH:mm";
    public static final String DATE_FORMAT_WITH_THREE_DIGIT_MONTH = "dd MMM yyyy";
    public static final String DATE_TIME_DISPLAY_FORMAT = "HH:mm, EEEE, dd MMMM yyyy";
    public static final String DATE_TIME_DISPLAY_FORMAT2 = "EEEE, dd MMMM yyyy HH:mm";
    public static final String DATE_FORMAT_WITH_FULL_TEXT = "EEEE, dd MMMM yyyy";
    public static final String DATE_FORMAT_WITH_THREE_DIGIT_DAY = "E, dd MMM yyyy";
    public static final String DATE_TIME_WITH_THREE_DIGIT_DAY = "E, dd MMM yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_WITH_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_FORMAT_WITH_THREE_DIGIT_DAY_WITHOUT_YEAR = "E, dd MMM";
    public static final String DATE_FORMAT_WITH_THREE_DIGIT_MONTH_WITH_GMT = "dd MMM yyyy, HH:mm O";
    public static final LocalDate MAX_LOCAL_DATE = LocalDate.of(9999, 12, 31);
    public static final LocalDateTime MAX_LOCAL_DATE_TIME = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String UTC_ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss+00:00";
    public static final String TIME_FORMAT_12H = "h:mm a";

    private DateConstant() {
    }
}
