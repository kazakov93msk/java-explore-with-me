package ru.practicum.ewmstats.utility;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class StatUtil {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
}
