package ru.javawebinar.basejava.serialization;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerialization implements SerializationStrategy {

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String FullName = dis.readUTF();
            Resume resume = new Resume(uuid, FullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                readSection(resume, dis);
            }
            return resume;
        }
    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContact();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                writeContact(dos, entry);
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                writeSection(dos, entry);
            }
        }
    }

    public void writeContact(DataOutputStream dos, Map.Entry<ContactType, String> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(entry.getValue());
    }

    public void writeSection(DataOutputStream dos, Map.Entry<SectionType, AbstractSection> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
        AbstractSection section = entry.getValue();
        dos.writeUTF(section.getClass().getSimpleName());
        switch (section.getClass().getName()) {
            case ("ru.javawebinar.basejava.model.TextSection"):
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ("ru.javawebinar.basejava.model.ListSection"):
                writeListSection(dos, (ListSection) section);
                break;
            case ("ru.javawebinar.basejava.model.OrganizationSection"):
                writeOrganizationSection(dos, (OrganizationSection) section);
                break;
            default:
                throw new StorageException("Error write resume");
        }
    }

    public void writeListSection(DataOutputStream dos, ListSection listSection) throws IOException {
        dos.writeInt(listSection.getItems().size());
        for (String str : listSection.getItems()) {
            dos.writeUTF(str);
        }
    }

    public void writeOrganizationSection(DataOutputStream dos, OrganizationSection organizationSection) throws IOException {
        dos.writeInt(organizationSection.getOrganizations().size());
        for (Organization organization : organizationSection.getOrganizations()) {
            dos.writeUTF(organization.getHomePage().getName());
            dos.writeUTF(organization.getHomePage().getUrl());
            dos.writeInt(organization.getPositions().size());
            writeOrganizationPosition(dos, organization);
        }
    }

    public void writeOrganizationPosition(DataOutputStream dos, Organization organizations) throws IOException {
        for (Organization.Position position : organizations.getPositions()) {
            dos.writeUTF(position.getStartData().toString());
            dos.writeUTF(position.getEndData().toString());
            dos.writeUTF(position.getSubTitel());
            dos.writeUTF(position.getDescription());
        }
    }

    public void readSection(Resume resume, DataInputStream dis) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        switch (dis.readUTF()) {
            case ("TextSection"):
                resume.addSection(sectionType, new TextSection(dis.readUTF()));
                break;
            case ("ListSection"):
                readListSection(resume, dis, sectionType);
                break;
            case ("OrganizationSection"):
                readOrganizationSection(resume, dis, sectionType);
                break;
            default:
                throw new StorageException("Error read resume");
        }
    }

    public void readListSection(Resume resume, DataInputStream dis, SectionType sectionType) throws IOException {
        List<String> items = new ArrayList<>();
        int count = dis.readInt();
        while (count > 0) {
            items.add(dis.readUTF());
            count--;
        }
        resume.addSection(sectionType, new ListSection(items));
    }

    public void readOrganizationSection(Resume resume, DataInputStream dis, SectionType sectionType)
            throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int count2 = dis.readInt();
        while (count2 > 0) {
            readOrganization(dis, organizations);
            count2--;
        }
        resume.addSection(sectionType, new OrganizationSection(organizations));
    }

    public void readOrganization(DataInputStream dis, List<Organization> organizations) throws IOException {
        List<Organization.Position> organizationPositions = new ArrayList<>();
        String name = dis.readUTF();
        String url = dis.readUTF();
        int count = dis.readInt();
        while (count > 0) {
            readOrganizationPosition(dis, organizationPositions);
            count--;
        }
        organizations.add(new Organization(name, url, organizationPositions));
    }

    public void readOrganizationPosition(DataInputStream dis, List<Organization.Position> organizationPositions) throws IOException {
        YearMonth startData = YearMonth.parse(dis.readUTF());
        YearMonth endData = YearMonth.parse(dis.readUTF());
        String subTitel = dis.readUTF();
        String description = dis.readUTF();
        organizationPositions.add(new Organization.Position(startData, endData, subTitel, description));
    }
}
