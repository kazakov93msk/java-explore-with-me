package ru.practicum.ewmservice.utility;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Utility {
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);


    public static  <T> T getValOrOld(T val, T newVal) {
        if (newVal == null) {
            return val;
        } else if (newVal instanceof String && ((String) newVal).isBlank()) {
            return val;
        } else if (newVal instanceof Number && newVal.equals(0) && val != null && val.equals(0)) {
            return val;
        }
        return newVal;
    }

    public static <T> T nvl(T val, T valIfNull) {
        if (val == null) {
            return valIfNull;
        }
        return val;
    }
}
