package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private ArrayList<String> list;

    public ListSection(String sectionTitel, ArrayList<String> list) {
        super(sectionTitel);
        Objects.requireNonNull(list);
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "list=" + list +
                '}' + '\n';
    }
}
