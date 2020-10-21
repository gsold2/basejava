package ru.javawebinar.basejava.util;

import org.junit.Test;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ResumeTestData;

import static org.junit.Assert.*;

public class JsonParserTest {
    private static final Resume RESUME_1 = ResumeTestData.createResumeInstance("UUID_1", "name1");

    @Test
    public void read() {
        String json = JsonParser.write(RESUME_1);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        ContactType contactTypeExpected = ContactType.valueOf("CELLPHONE");
        String json = JsonParser.write(contactTypeExpected);
        ContactType contactType = JsonParser.read(json, ContactType.class);
        assertEquals(contactTypeExpected, contactType);
    }
}