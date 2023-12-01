package ru.businesscloud.vin39.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DjangoHelper {

    private static DateTimeFormatter getFormater(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
    }

    public static String formatDjangoDateToFormatString(String date) {
        LocalDate result = LocalDate.parse(date, getFormater("yyyy-MM-dd"));
        return result.format(getFormater("dd.MM.yyyy"));
    }
}
