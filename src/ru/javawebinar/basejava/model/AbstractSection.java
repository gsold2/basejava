package ru.javawebinar.basejava.model;

import java.util.Objects;

public abstract class AbstractSection {

    private String sectionTitel;

    public AbstractSection(String sectionTitel) {
        Objects.requireNonNull(sectionTitel);
        this.sectionTitel = sectionTitel;
    }
}
