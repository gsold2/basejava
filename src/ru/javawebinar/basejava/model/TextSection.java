package ru.javawebinar.basejava.model;

public class TextSection extends AbstractSection {

    private String text;

    public TextSection(String sectionTitel, String text) {
        super(sectionTitel);
        this.text = text;
    }
}
