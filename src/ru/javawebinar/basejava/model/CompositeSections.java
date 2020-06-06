package ru.javawebinar.basejava.model;

import java.util.ArrayList;

public class CompositeSections extends AbstractSection {

    private ArrayList<CompositeSection> list;

    public CompositeSections(String sectionTitel, ArrayList<CompositeSection> list) {
        super(sectionTitel);
        this.list = list;
    }
}
