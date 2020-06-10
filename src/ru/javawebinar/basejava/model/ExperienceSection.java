package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Objects;

public class ExperienceSection extends AbstractSection {

    private ArrayList<Experience> list;

    public ExperienceSection(String sectionTitel, ArrayList<Experience> list) {
        super(sectionTitel);
        Objects.requireNonNull(list);
        this.list = list;
    }

    @Override
    public String toString() {
        return "ExperienceSection{" +
                "list=" + list +
                '}' + '\n';
    }
}
