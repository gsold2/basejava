package ru.javawebinar.basejava.model;

public enum SectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String titel;

    SectionType(String titel) {
        this.titel = titel;
    }

    public String getTitel() {
        return titel;
    }

    @Override
    public String toString() {
        return "SectionType{" +
                "titel='" + titel + '\'' +
                '}' + '\n';
    }
}
