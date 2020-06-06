package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class CompositeSection {

    private String titel;
    private YearMonth start;
    private YearMonth end;
    private String subTitel;
    private String text;

    public CompositeSection(String titel, YearMonth start, YearMonth end, String subTitel, String text) {
        this.titel = titel;
        this.start = start;
        this.end = end;
        this.subTitel = subTitel;
        this.text = text;
    }
}
