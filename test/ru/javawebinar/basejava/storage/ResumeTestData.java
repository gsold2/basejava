package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = createResumeInstance("uuid", "Григорий Кислин");
        System.out.println(resume.toString());
    }

    public static Resume createResumeInstance(String uuid, String name) {
        Resume resume = new Resume(uuid, name);

        resume.getContact().put(ContactType.CELLPHONE, "+7(921) 855-0482");
        resume.getContact().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContact().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContact().put(ContactType.LINKENDIN, "");
        resume.getContact().put(ContactType.GITHUB, "");
        resume.getContact().put(ContactType.STACKOVERFLOW, "");
        resume.getContact().put(ContactType.HOMEPAGE, "");

        resume.getSections().put(SectionType.PERSONAL, new TextSection(
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.getSections().put(SectionType.OBJECTIVE, new TextSection(
                "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры."));

        List<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о " +
                "состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                "мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.getSections().put(SectionType.ACHIEVEMENT,
                new ListSection(achievements));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, " +
                "DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, " +
                "OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                "Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        resume.getSections().put(SectionType.QUALIFICATIONS,
                new ListSection(qualifications));

        ArrayList<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization("Java Online Projects",
                YearMonth.parse("2013-10"), YearMonth.now(), "Автор проекта.", "Создание, " +
                "организация и проведение Java онлайн проектов и стажировок."));
        organizations.add(new Organization("Wrike", YearMonth.parse("2014-10"), YearMonth.parse("2016-01"),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы " +
                "управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                "Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        organizations.add(new Organization("RIT Center", YearMonth.parse("2012-04"), YearMonth.parse("2014-10"),
                "Java архитектор", "Организация процесса разработки системы ERP для разных " +
                "окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
                "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, " +
                "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, " +
                "xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, " +
                "PL/Python"));
        organizations.add(new Organization("Luxoft (Deutsche Bank)", YearMonth.parse("2010-12"),
                YearMonth.parse("2012-04"), "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, " +
                        "Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной " +
                        "части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа " +
                        "результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, " +
                        "ExtGWT (GXT), Highstock, Commet, HTML5."));
        organizations.add(new Organization("Yota", YearMonth.parse("2008-06"), YearMonth.parse("2010-12"),
                "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела" +
                " \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, " +
                "JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. " +
                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        organizations.add(new Organization("Enkata", YearMonth.parse("2007-03"), YearMonth.parse("2008-06"),
                "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, " +
                "Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        organizations.add(new Organization("Siemens AG", YearMonth.parse("2005-01"), YearMonth.parse("2007-02"),
                "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, " +
                "реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        organizations.add(new Organization("Alcatel", YearMonth.parse("1997-09"), YearMonth.parse("2005-01"),
                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, " +
                "внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        resume.getSections().put(SectionType.EXPERIENCE,
                new OrganizationSection(organizations));

        ArrayList<Organization> educations = new ArrayList<>();
        educations.add(new Organization("Coursera",
                YearMonth.parse("2013-03"), YearMonth.parse("2013-05"), "\"Functional Programming Principles " +
                "in Scala\" by Martin Odersky"));
        educations.add(new Organization("Luxoft",
                YearMonth.parse("2011-03"), YearMonth.parse("2011-04"), "Курс \"Объектно-ориентированный " +
                "анализ ИС. Концептуальное моделирование на UML.\""));
        educations.add(new Organization("Siemens AG",
                YearMonth.parse("2005-01"), YearMonth.parse("2005-04"), "3 месяца обучения мобильным " +
                "IN сетям (Берлин)"));
        educations.add(new Organization("Alcatel",
                YearMonth.parse("1998-09"), YearMonth.parse("1998-09"), "6 месяцев обучения цифровым " +
                "телефонным сетям (Москва)"));
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(YearMonth.parse("1993-09"), YearMonth.parse("1996-07"), "Аспирантура " +
                "(программист С, С++)"));
        positions.add(new Position(YearMonth.parse("1987-09"), YearMonth.parse("1993-07"), "Инженер " +
                "(программист Fortran, C)"));
        educations.add(new Organization("Санкт-Петербургский национальный исследовательский " +
                "университет информационных технологий, механики и оптики", positions));
        educations.add(new Organization("Заочная физико-техническая школа при МФТИ",
                YearMonth.parse("1984-09"), YearMonth.parse("1987-06"), "Закончил с отличием"));
        resume.getSections().put(SectionType.EDUCATION,
                new OrganizationSection(educations));
        return resume;
    }
}
