package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.ArrayList;

public class ResumeTestData {

    public static void main(String[] args) {

        Resume resume = new Resume("uuid", "Григорий Кислин");

        resume.getContactSection().put(ContactType.CELLPHONE, "+7(921) 855-0482");
        resume.getContactSection().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContactSection().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContactSection().put(ContactType.LINKENDIN, "");
        resume.getContactSection().put(ContactType.GITHUB, "");
        resume.getContactSection().put(ContactType.STACKOVERFLOW, "");
        resume.getContactSection().put(ContactType.HOMEPAGE, "");

        resume.getDataSection().put(SectionType.PERSONAL, new TextSection(SectionType.PERSONAL.getTitel(),
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.getDataSection().put(SectionType.OBJECTIVE, new TextSection(SectionType.OBJECTIVE.getTitel(),
                "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры."));

        ArrayList<String> listAchievement = new ArrayList<>();
        listAchievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        listAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        listAchievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        listAchievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о " +
                "состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                "мониторинга системы по JMX (Jython/ Django).");
        listAchievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, " +
                "Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.getDataSection().put(SectionType.ACHIEVEMENT,
                new ListSection(SectionType.ACHIEVEMENT.getTitel(), listAchievement));

        ArrayList<String> listQualifications = new ArrayList<>();
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        listQualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        listQualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        listQualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        listQualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        listQualifications.add("Python: Django.");
        listQualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        listQualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        listQualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, " +
                "XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, " +
                "OAuth2, JWT.");
        listQualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        listQualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, " +
                "OpenCmis, Bonita, pgBouncer.");
        listQualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        listQualifications.add("Родной русский, английский \"upper intermediate\"");
        resume.getDataSection().put(SectionType.QUALIFICATIONS,
                new ListSection(SectionType.QUALIFICATIONS.getTitel(), listQualifications));

        ArrayList<Experience> compositeSectionsExperience = new ArrayList<>();
        compositeSectionsExperience.add(new Experience("Java Online Projects",
                YearMonth.parse("2013-10"), YearMonth.now(), "Автор проекта.", "Создание, " +
                "организация и проведение Java онлайн проектов и стажировок."));
        compositeSectionsExperience.add(new Experience("Wrike", YearMonth.parse("2014-10"), YearMonth.parse("2016-01"),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы " +
                        "управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                        "Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        compositeSectionsExperience.add(new Experience("RIT Center", YearMonth.parse("2012-04"), YearMonth.parse("2014-10"),
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: " +
                        "релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
                        "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                        "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, " +
                        "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                        "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                        "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, " +
                        "xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, " +
                        "PL/Python"));
        compositeSectionsExperience.add(new Experience("Luxoft (Deutsche Bank)", YearMonth.parse("2010-12"), YearMonth.parse("2012-04"),
                "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, " +
                        "Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной " +
                        "части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа " +
                        "результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, " +
                        "ExtGWT (GXT), Highstock, Commet, HTML5."));
        compositeSectionsExperience.add(new Experience("Yota", YearMonth.parse("2008-06"), YearMonth.parse("2010-12"),
                "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела" +
                        " \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, " +
                        "JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. " +
                        "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        compositeSectionsExperience.add(new Experience("Enkata", YearMonth.parse("2007-03"), YearMonth.parse("2008-06"),
                "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, " +
                        "Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        compositeSectionsExperience.add(new Experience("Siemens AG", YearMonth.parse("2005-01"), YearMonth.parse("2007-02"),
                "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, " +
                        "реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        compositeSectionsExperience.add(new Experience("Alcatel", YearMonth.parse("1997-09"), YearMonth.parse("2005-01"),
                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, " +
                        "внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        resume.getDataSection().put(SectionType.EXPERIENCE,
                new ExperienceSection(SectionType.EXPERIENCE.getTitel(), compositeSectionsExperience));

        ArrayList<Experience> compositeSectionsEducation = new ArrayList<>();
        compositeSectionsEducation.add(new Experience("Coursera",
                YearMonth.parse("2013-03"), YearMonth.parse("2013-05"), "\"Functional Programming Principles " +
                "in Scala\" by Martin Odersky", ""));
        compositeSectionsEducation.add(new Experience("Luxoft",
                YearMonth.parse("2011-03"), YearMonth.parse("2011-04"), "Курс \"Объектно-ориентированный " +
                "анализ ИС. Концептуальное моделирование на UML.\"", ""));
        compositeSectionsEducation.add(new Experience("Siemens AG",
                YearMonth.parse("2005-01"), YearMonth.parse("2005-04"), "3 месяца обучения мобильным " +
                "IN сетям (Берлин)", ""));
        compositeSectionsEducation.add(new Experience("Alcatel",
                YearMonth.parse("1998-09"), YearMonth.parse("1998-09"), "6 месяцев обучения цифровым " +
                "телефонным сетям (Москва)", ""));
        compositeSectionsEducation.add(new Experience("Санкт-Петербургский национальный исследовательский " +
                "университет информационных технологий, механики и оптики",
                YearMonth.parse("1993-09"), YearMonth.parse("1996-07"), "Аспирантура (программист С, С++)",
                ""));
        compositeSectionsEducation.add(new Experience("Санкт-Петербургский национальный исследовательский " +
                "университет информационных технологий, механики и оптики",
                YearMonth.parse("1987-09"), YearMonth.parse("1993-07"), "Инженер (программист Fortran, C)",
                ""));
        compositeSectionsEducation.add(new Experience("Заочная физико-техническая школа при МФТИ",
                YearMonth.parse("1984-09"), YearMonth.parse("1987-06"), "Закончил с отличием",
                ""));
        resume.getDataSection().put(SectionType.EDUCATION,
                new ExperienceSection(SectionType.EDUCATION.getTitel(), compositeSectionsEducation));
        System.out.println(resume.toString());
    }
}
