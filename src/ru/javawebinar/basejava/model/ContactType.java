package ru.javawebinar.basejava.model;

public enum ContactType {
    CELLPHONE("Тел.:"),
    SKYPE("Skype:"),
    EMAIL("Почта:"),
    LINKENDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private final String titel;

    ContactType(String titel) {
        this.titel = titel;
    }

//    @Override
//    public String toString() {
//        return "ContactType{" +
//                "titel='" + titel + '\'' +
//                '}' + '\n';
//    }
}
