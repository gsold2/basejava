package ru.javawebinar.basejava.model;

public enum SectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String titel;

    SectionType(String titel) {
        this.titel = titel;
    }

//    @Override
//    public String toString() {
//        return "SectionType{" +
//                "titel='" + titel + '\'' +
//                '}' + '\n';
//    }
}
