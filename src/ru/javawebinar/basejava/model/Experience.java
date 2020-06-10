package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class Experience {

    private String titel;
    private YearMonth start;
    private YearMonth end;
    private String subTitel;
    private String text;

    public Experience(String titel, YearMonth start, YearMonth end, String subTitel, String text) {
        this.titel = titel;
        this.start = start;
        this.end = end;
        this.subTitel = subTitel;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "titel='" + titel + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", subTitel='" + subTitel + '\'' +
                ", text='" + text + '\'' +
                '}' + '\n';
    }
}
