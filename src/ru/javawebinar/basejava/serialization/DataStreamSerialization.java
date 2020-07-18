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
            SectionType sectionType;
            String section;
            for (int i = 0; i < size; i++) {
                sectionType = SectionType.valueOf(dis.readUTF());
                section = dis.readUTF();
                if (section.equals("TextSection")) {
                    resume.addSection(sectionType, new TextSection(dis.readUTF()));
                }
                if (section.equals("ListSection")) {
                    List<String> items = new ArrayList<>();
                    int count = dis.readInt();
                    while (count > 0) {
                        items.add(dis.readUTF());
                        count--;
                    }
                    resume.addSection(sectionType, new ListSection(items));
                }
                if (section.equals("OrganizationSection")) {
                    List<Organization> organizations = new ArrayList<>();
                    int count = dis.readInt();
                    while (count > 0) {
                        readOrganizationSection(dis, organizations);
                        count--;
                    }
                    resume.addSection(sectionType, new OrganizationSection(organizations));
                }
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
            contacts.entrySet().forEach(entry -> writeContact(dos, entry));
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            sections.entrySet().forEach(entry -> writeSection(dos, entry));
        }
    }

    public void writeContact(DataOutputStream dos, Map.Entry<ContactType, String> entry) {
        try {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        } catch (IOException e) {
            throw new StorageException("Can't serialize Contact", e);
        }
    }

    public void writeSection(DataOutputStream dos, Map.Entry<SectionType, AbstractSection> entry) {
        try {
            dos.writeUTF(entry.getKey().name());
            AbstractSection section = entry.getValue();
            dos.writeUTF(section.getClass().getSimpleName());
            if (section instanceof TextSection) {
                writeTextSection(dos, (TextSection) section);
            } else if (section instanceof ListSection) {
                writeListSection(dos, (ListSection) section);
            } else if (section instanceof OrganizationSection) {
                writeOrganizationSection(dos, (OrganizationSection) section);
            }
        } catch (IOException e) {
            throw new StorageException("Can't serialize Section", e);
        }

    }

    public void writeTextSection(DataOutputStream dos, TextSection textSection) {
        try {
            dos.writeUTF(textSection.getContent());
        } catch (IOException e) {
            throw new StorageException("Can't serialize TextSection", e);
        }
    }

    public void writeListSection(DataOutputStream dos, ListSection listSection) {
        try {
            dos.writeInt(listSection.getItems().size());
            for (String str : listSection.getItems()) {
                dos.writeUTF(str);
            }
        } catch (IOException e) {
            throw new StorageException("Can't serialize ListSection", e);
        }
    }

    public void writeOrganizationSection(DataOutputStream dos, OrganizationSection organizationSection) {
        try {
            dos.writeInt(organizationSection.getOrganizations().size());
            for (Organization organizations : organizationSection.getOrganizations()) {
                dos.writeUTF(organizations.getHomePage().getName());
                dos.writeUTF(organizations.getHomePage().getUrl());
                dos.writeInt(organizations.getPositions().size());
                writeOrganizationPosition(dos, organizations);
            }
        } catch (IOException e) {
            throw new StorageException("Can't serialize OrganizationSection", e);
        }
    }

    public void writeOrganizationPosition(DataOutputStream dos, Organization organizations) {
        for (Organization.Position position : organizations.getPositions()) {
            try {
                dos.writeUTF(position.getStartData().toString());
                dos.writeUTF(position.getEndData().toString());
                dos.writeUTF(position.getSubTitel());
                dos.writeUTF(position.getDescription());
            } catch (IOException e) {
                throw new StorageException("Can't serialize Position", e);
            }
        }
    }

    public void readOrganizationPosition(DataInputStream dis, List<Organization.Position> organizationPositions) {
        try {
            YearMonth startData = YearMonth.parse(dis.readUTF());
            YearMonth endData = YearMonth.parse(dis.readUTF());
            String subTitel = dis.readUTF();
            String description = dis.readUTF();
            organizationPositions.add(new Organization.Position(startData, endData, subTitel, description));
        } catch (IOException e) {
            throw new StorageException("Can't deserialize Position", e);
        }
    }

    public void readOrganizationSection(DataInputStream dis, List<Organization> organizations) {
        List<Organization.Position> organizationPositions = new ArrayList<>();
        try {
            String name = dis.readUTF();
            String url = dis.readUTF();
            int count = dis.readInt();
            while (count > 0) {
                readOrganizationPosition(dis, organizationPositions);
                count--;
            }
            organizations.add(new Organization(name, url, organizationPositions));
        } catch (IOException e) {
            throw new StorageException("Can't deserialize OrganizationSection", e);
        }
    }
}
