package ru.javawebinar.basejava.serialization;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.*;

public class DataStreamSerialization implements SerializationStrategy {

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String FullName = dis.readUTF();
            Resume resume = new Resume(uuid, FullName);
            readMapWithException(dis, () -> readContact(resume, dis));
            readMapWithException(dis, () -> readSection(resume, dis));
            return resume;
        }
    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> writeContact(dos, entry));
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeWithException(dos, sections.entrySet(), entry -> writeSection(dos, entry));
        }
    }

    protected void writeContact(DataOutputStream dos, Map.Entry<ContactType, String> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(entry.getValue());
    }

    protected void writeSection(DataOutputStream dos, Map.Entry<SectionType, AbstractSection> entry) throws IOException {
        SectionType sectionType = entry.getKey();
        dos.writeUTF(sectionType.name());
        AbstractSection section = entry.getValue();
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                writeTextSection(dos, (TextSection) section);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeWithException(dos, ((ListSection) section).getItems(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeWithException(dos, ((OrganizationSection) section).getOrganizations(),
                        organization -> writeOrganizationSection(dos, organization));
                break;
            default:
                throw new StorageException("Error write resume");
        }
    }

    protected void writeTextSection(DataOutputStream dos, TextSection textSection) throws IOException {
        dos.writeUTF(textSection.getContent());
    }

    protected void writeOrganizationSection(DataOutputStream dos, Organization organization) throws IOException {
        dos.writeUTF(organization.getHomePage().getName());
        dos.writeUTF(writeValue(organization.getHomePage().getUrl()));
        writeWithException(dos, organization.getPositions(), position -> writeOrganizationPosition(dos, position));
    }

    protected void writeOrganizationPosition(DataOutputStream dos, Organization.Position position) throws IOException {
        dos.writeUTF(position.getStartData().toString());
        dos.writeUTF(position.getEndData().toString());
        dos.writeUTF(position.getSubTitel());
        dos.writeUTF(writeValue(position.getDescription()));
    }

    protected String writeValue(String value) {
        return value != null ? value : "";
    }

    @FunctionalInterface
    protected interface ConsumerWithExceptions<T> {
        void accept(T t) throws IOException;
    }

    protected <T> void writeWithException(DataOutputStream dos, Collection<T> source,
                                          ConsumerWithExceptions<T> function) throws IOException {
        dos.writeInt(source.size());
        for (T t : source) {
            function.accept(t);
        }
    }

    protected void readContact(Resume resume, DataInputStream dis) throws IOException {
        resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
    }

    protected void readSection(Resume resume, DataInputStream dis) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                readTextSection(resume, dis, sectionType);
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

    protected void readTextSection(Resume resume, DataInputStream dis, SectionType sectionType) throws IOException {
        resume.addSection(sectionType, new TextSection(dis.readUTF()));
    }

    protected void readListSection(Resume resume, DataInputStream dis, SectionType sectionType) throws IOException {
        List<String> items = readWithException(dis, dis::readUTF);
        resume.addSection(sectionType, new ListSection(items));
    }

    protected void readOrganizationSection(Resume resume, DataInputStream dis, SectionType sectionType)
            throws IOException {
        List<Organization> organizations = readWithException(dis, () -> readOrganization(dis));
        resume.addSection(sectionType, new OrganizationSection(organizations));
    }

    protected Organization readOrganization(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String url = readValue(dis.readUTF());
        List<Organization.Position> organizationPositions = readWithException(dis, () -> readOrganizationPosition(dis));
        return (new Organization(name, url, organizationPositions));
    }

    protected Organization.Position readOrganizationPosition(DataInputStream dis) throws IOException {
        YearMonth startData = YearMonth.parse(dis.readUTF());
        YearMonth endData = YearMonth.parse(dis.readUTF());
        String subTitel = dis.readUTF();
        String description = readValue(dis.readUTF());
        return (new Organization.Position(startData, endData, subTitel, description));
    }

    protected String readValue(String value) {
        return value.equals("") ? null : value;
    }

    @FunctionalInterface
    protected interface SupplierWithExceptions<T> {
        T accept() throws IOException;
    }

    protected <T> List<T> readWithException(DataInputStream dis,
                                            SupplierWithExceptions<T> function) throws IOException {
        int count = dis.readInt();
        List<T> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(function.accept());
        }
        return result;
    }

    @FunctionalInterface
    protected interface FunctionReadWithExceptions {
        void accept() throws IOException;
    }

    protected void readMapWithException(DataInputStream dis,
                                        FunctionReadWithExceptions function)
            throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            function.accept();
        }
    }
}
