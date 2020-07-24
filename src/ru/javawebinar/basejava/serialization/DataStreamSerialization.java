package ru.javawebinar.basejava.serialization;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

//            contacts.entrySet().forEach(throwingConsumerWrapper(i -> writeContact(dos, i)));
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
        switch (section.getClass().getSimpleName()) {
            case ("TextSection"):
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ("ListSection"):
                writeListSection(dos, (ListSection) section);
                break;
            case ("OrganizationSection"):
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
            dos.writeUTF(writeValue(organization.getHomePage().getUrl()));
            dos.writeInt(organization.getPositions().size());
            writeOrganizationPosition(dos, organization);
        }
    }

    public void writeOrganizationPosition(DataOutputStream dos, Organization organizations) throws IOException {
        for (Organization.Position position : organizations.getPositions()) {
            dos.writeUTF(position.getStartData().toString());
            dos.writeUTF(position.getEndData().toString());
            dos.writeUTF(position.getSubTitel());
            dos.writeUTF(writeValue(position.getDescription()));
        }
    }

    public String writeValue(String value) {
        return value != null ? value : "null";
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
        int count = dis.readInt();
        while (count > 0) {
            readOrganization(dis, organizations);
            count--;
        }
        resume.addSection(sectionType, new OrganizationSection(organizations));
    }

    public void readOrganization(DataInputStream dis, List<Organization> organizations) throws IOException {
        List<Organization.Position> organizationPositions = new ArrayList<>();
        String name = dis.readUTF();
        String url = readValue(dis.readUTF());
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
        String description = readValue(dis.readUTF());
        organizationPositions.add(new Organization.Position(startData, endData, subTitel, description));
    }

    public String readValue(String value) {
        return value.equals("null") ? null : value;
    }

    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }

    static <T> Consumer<T> throwingConsumerWrapper(
            ThrowingConsumer<T, Exception> throwingConsumer) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

}
