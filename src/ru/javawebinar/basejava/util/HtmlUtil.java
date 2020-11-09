package ru.javawebinar.basejava.util;

import java.time.YearMonth;

public class HtmlUtil {

    public static String formatDate(YearMonth yearMonth) {
        String formatDate = "";
        if (yearMonth != null & !YearMonth.now().equals(yearMonth)) {
            formatDate = yearMonth.toString().replace('-', '/');
        } else if (yearMonth != null & YearMonth.now().equals(yearMonth)) {
            formatDate = "Сейчас";
        }
        return formatDate;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static YearMonth parse(String date) {
        YearMonth yearMonth;
        if (date.equals("Сейчас") | YearMonth.parse(date.replace('/', '-')).equals(YearMonth.now())) {
            yearMonth = YearMonth.now();
        } else {
            yearMonth = YearMonth.parse(date.replace('/', '-'));
        }
        return yearMonth;
    }
}
