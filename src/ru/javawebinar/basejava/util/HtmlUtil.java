package ru.javawebinar.basejava.util;

import java.time.YearMonth;

public class HtmlUtil {

    public static String formatDate(YearMonth yearMonth) {
        String formatDate = "";
        if (yearMonth != null) {
            formatDate = yearMonth.toString().replace('-', '/');
        }
        return formatDate;
    }

    public static YearMonth parse(String date) {
        YearMonth yearMonth;
        if (YearMonth.parse(date.replace('/', '-')).equals(YearMonth.now())) {
            yearMonth = YearMonth.now();
        } else {
            yearMonth = YearMonth.parse(date.replace('/', '-'));
        }
        return yearMonth;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String specialChars(String string) {
        return (string != null)
                ? (string.replace("\\", "")).replace("\"", "&quot")
                : "";
    }
}
