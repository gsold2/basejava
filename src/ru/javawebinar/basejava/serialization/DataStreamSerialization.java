package ru.javawebinar.basejava.serialization;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
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
            writeWithException(contacts.entrySet(), entry -> writeContact(dos, entry));
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            writeWithException(sections.entrySet(), entry -> writeSection(dos, entry));
        }
    }

    public void writeContact(DataOutputStream dos, Map.Entry<ContactType, String> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(entry.getValue());
    }

    public void writeSection(DataOutputStream dos, Map.Entry<SectionType, AbstractSection> entry) throws IOException {
        SectionType sectionType = entry.getKey();
        dos.writeUTF(sectionType.name());
        AbstractSection section = entry.getValue();
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeListSection(dos, (ListSection) section);
                break;
            case EXPERIENCE:
            case EDUCATION:
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

    @FunctionalInterface
    public interface FunctionWithExceptions<T, E extends Throwable> {
        void accept(T t) throws E;
    }

    public static <T, E extends Throwable> void writeWithException(Collection<T> source,
                                                    FunctionWithExceptions<T, E> function) throws E {
        for (T t : source) {
            function.accept(t);
        }
    }

    public void readSection(Resume resume, DataInputStream dis) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                resume.addSection(sectionType, new TextSection(dis.readUTF()));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                readListSection(resume, dis, sectionType);
                break;
            case EXPERIENCE:
            case EDUCATION:
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
}
