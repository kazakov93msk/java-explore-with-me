package ru.practicum.ewmservice.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);

    public static LocalDateTime convertDateTime(String strDateTime) {
        if (strDateTime == null) {
            return null;
        }
        return LocalDateTime.parse(strDateTime, formatter);
    }

    public static String convertDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(formatter);
    }

    public static  <T> T getValOrOld(T val, T newVal) {
        if (newVal == null) {
            return val;
        }
        return newVal;
    }
}
