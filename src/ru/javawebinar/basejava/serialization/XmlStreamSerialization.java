package ru.javawebinar.basejava.serialization;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerialization implements SerializationStrategy {

    private XmlParser xmlParser;

    public XmlStreamSerialization() {
        xmlParser = new XmlParser(Resume.class, OrganizationSection.class, ListSection.class, TextSection.class,
                Organization.class, Link.class, Organization.Position.class);
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return (Resume) xmlParser.unmarshall(r);
        }
    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }
}
