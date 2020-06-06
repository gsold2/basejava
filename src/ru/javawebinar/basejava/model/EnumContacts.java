package ru.javawebinar.basejava.model;

public enum EnumContacts {
    CELLPHONE("Тел.:"),
    SKYPE("Skype:"),
    EMAIL("Почта:"),
    LINKENDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String titel;

    EnumContacts(String titel) {
        this.titel = titel;
    }
}
