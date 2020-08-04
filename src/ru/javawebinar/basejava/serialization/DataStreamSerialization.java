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
            readMapWithException(resume, dis, this::readContact);
            readMapWithException(resume, dis, this::readSection);
            return resume;
        }
    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContact();
            writeWithException(dos, contacts.entrySet(), entry -> writeContact(dos, entry));
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeWithException(dos, sections.entrySet(), entry -> writeSection(dos, entry));
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

    public void writeOrganizationSection(DataOutputStream dos, Organization organization) throws IOException {
        dos.writeUTF(organization.getHomePage().getName());
        dos.writeUTF(writeValue(organization.getHomePage().getUrl()));
        writeWithException(dos, organization.getPositions(), position -> writeOrganizationPosition(dos, position));
    }

    public void writeOrganizationPosition(DataOutputStream dos, Organization.Position position) throws IOException {
        dos.writeUTF(position.getStartData().toString());
        dos.writeUTF(position.getEndData().toString());
        dos.writeUTF(position.getSubTitel());
        dos.writeUTF(writeValue(position.getDescription()));
    }

    public String writeValue(String value) {
        return value != null ? value : "";
    }

    @FunctionalInterface
    public interface ConsumerWithExceptions<T> {
        void accept(T t) throws IOException;
    }

    public static <T> void writeWithException(DataOutputStream dos, Collection<T> source,
                                              ConsumerWithExceptions<T> function) throws IOException {
        dos.writeInt(source.size());
        for (T t : source) {
            function.accept(t);
        }
    }

    public void readContact(Resume resume, DataInputStream dis) throws IOException {
        resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
    }

    public void readSection(Resume resume, DataInputStream dis) throws IOException {
        SectionType sectionType = SectionType.valueOf(dis.readUTF());
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                readTextSection(resume, dis, sectionType);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> items = readWithException(dis, dis::readUTF);
                resume.addSection(sectionType, new ListSection(items));
                break;
            case EXPERIENCE:
            case EDUCATION:
                readOrganizationSection(resume, dis, sectionType);
                break;
            default:
                throw new StorageException("Error read resume");
        }
    }

    public void readTextSection(Resume resume, DataInputStream dis, SectionType sectionType) throws IOException {
        resume.addSection(sectionType, new TextSection(dis.readUTF()));
    }

    public void readOrganizationSection(Resume resume, DataInputStream dis, SectionType sectionType)
            throws IOException {
        List<Organization> organizations = readWithException(dis, () -> readOrganization(dis));
        resume.addSection(sectionType, new OrganizationSection(organizations));
    }

    public Organization readOrganization(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String url = readValue(dis.readUTF());
        List<Organization.Position> organizationPositions = readWithException(dis, () -> readOrganizationPosition(dis));
        return (new Organization(name, url, organizationPositions));
    }

    public Organization.Position readOrganizationPosition(DataInputStream dis) throws IOException {
        YearMonth startData = YearMonth.parse(dis.readUTF());
        YearMonth endData = YearMonth.parse(dis.readUTF());
        String subTitel = dis.readUTF();
        String description = readValue(dis.readUTF());
        return (new Organization.Position(startData, endData, subTitel, description));
    }

    public String readValue(String value) {
        return value.equals("") ? null : value;
    }

    @FunctionalInterface
    public interface SupplierWithExceptions<T> {
        T accept() throws IOException;
    }

    public <T> List<T> readWithException(DataInputStream dis,
                                         SupplierWithExceptions<T> function) throws IOException {
        int count = dis.readInt();
        List<T> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(function.accept());
        }
        return result;
    }

    @FunctionalInterface
    public interface FunctionReadWithExceptions<R, D> {
        void accept(R r, D d) throws IOException;
    }

    public void readMapWithException(Resume resume, DataInputStream dis,
                                     FunctionReadWithExceptions<Resume, DataInputStream> function)
            throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            function.accept(resume, dis);
        }
    }
}
