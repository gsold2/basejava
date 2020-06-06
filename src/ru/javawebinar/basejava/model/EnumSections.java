package ru.javawebinar.basejava.model;

public enum EnumSections {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String titel;

    EnumSections(String titel) {
        this.titel = titel;
    }

    public String getTitel() {
        return titel;
    }
}
