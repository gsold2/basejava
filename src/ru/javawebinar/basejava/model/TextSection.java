package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection extends AbstractSection {

    private String text;

    public TextSection(String sectionTitel, String text) {
        super(sectionTitel);
        Objects.requireNonNull(text);
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "text='" + text + '\'' +
                '}' + '\n';
    }
}
