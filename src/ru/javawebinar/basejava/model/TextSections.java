package ru.javawebinar.basejava.model;

import java.util.ArrayList;

public class TextSections extends AbstractSection {

    private ArrayList<String> list;

    public TextSections(String sectionTitel, ArrayList<String> list) {
        super(sectionTitel);
        this.list = list;
    }
}
