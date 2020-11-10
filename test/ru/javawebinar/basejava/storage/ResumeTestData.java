package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = createResumeInstance("uuid", "Григорий Кислин");
        System.out.println(resume.toString());
    }

    public static Resume createResumeInstance(String uuid, String name) {
        Resume resume = new Resume(uuid, name);

        resume.getContacts().put(ContactType.CELLPHONE, "+7(921) 123-45");
        resume.getContacts().put(ContactType.SKYPE, "skype");
        resume.getContacts().put(ContactType.EMAIL, "mail@yandex.ru");
        resume.getContacts().put(ContactType.LINKENDIN, "");
        resume.getContacts().put(ContactType.GITHUB, "");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "");
        resume.getContacts().put(ContactType.HOMEPAGE, "");

        resume.getSections().put(SectionType.PERSONAL, new TextSection(
                "Личные качества"));
        resume.getSections().put(SectionType.OBJECTIVE, new TextSection(
                "Позиция"));

        resume.getSections().put(SectionType.ACHIEVEMENT,
                new ListSection(
                        ("Достижения ListSection1"),
                        ("Достижения ListSection2"),
                        ("Достижения ListSection3")
                ));

        resume.getSections().put(SectionType.QUALIFICATIONS,
                new ListSection(
                        ("Квалификация ListSection1"),
                        ("Квалификация ListSection2"),
                        ("Квалификация ListSection3")));

        resume.getSections().put(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Опыт Organization1", "https://dwg.ru/",
                        new Organization.Position(YearMonth.parse("2013-10"), YearMonth.now(), "Заголовок1",
                                "Описание1")),
                new Organization("Опыт Organization2", new Organization.Position(YearMonth.parse("2014-10"),
                        YearMonth.parse("2016-01"), "Заголовок2",
                        "Описание2")),
                new Organization("Опыт Organization3", new Organization.Position(YearMonth.parse("2012-04"),
                        YearMonth.parse("2014-10"), "Заголовок3", "Описание3"))));

        resume.getSections().put(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Обучение Organization1", new Organization.Position(
                                YearMonth.parse("2013-03"), YearMonth.parse("2013-05"),
                                "Это перед ковычками \"Заголовок1\" это после ковычек")),
                        new Organization("Обучение Organization2", new Organization.Position(
                                YearMonth.parse("2011-03"), YearMonth.parse("2011-04"),
                                "Это перед ковычками \"Заголовок2\" это после ковычек")),
                        new Organization("Обучение Organization3",
                                new Organization.Position(YearMonth.parse("1993-09"), YearMonth.parse("1996-07"),
                                        "Заголовок3_1"),
                                new Organization.Position(YearMonth.parse("1987-09"),
                                        YearMonth.parse("1993-07"), "Заголовок3_2"))));
        return resume;
    }
}
