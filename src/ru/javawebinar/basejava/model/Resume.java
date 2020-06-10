package ru.javawebinar.basejava.model;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;
    private String fullName;
    private EnumMap<ContactType, String> contactSection;
    private EnumMap<SectionType, AbstractSection> dataSection;

    public Resume() {
        this.contactSection = new EnumMap<>(ContactType.class);
        this.dataSection = new EnumMap<>(SectionType.class);
    }

    public Resume(String fullName) {
        this();
        Objects.requireNonNull(fullName, "fullName must be not null");
        this.fullName = fullName;
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String uuid, String fullName) {
        this(fullName);
        Objects.requireNonNull(uuid, "uuid must be not null");
        this.uuid = uuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    public EnumMap<ContactType, String> getContactSection() {
        return this.contactSection;
    }

    public EnumMap<SectionType, AbstractSection> getDataSection() {
        return this.dataSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!contactSection.equals(resume.contactSection)) return false;
        return dataSection.equals(resume.dataSection);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contactSection.hashCode();
        result = 31 * result + dataSection.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" + '\n' +
                ", uuid='" + uuid + '\'' + '\n' +
                ", fullName='" + fullName + '\'' + '\n' +
                ", contactSection=" + contactSection + '\n' +
                ", dataSection=" + dataSection +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        int result = this.fullName.compareTo(o.fullName);
        if (result == 0) {
            return this.uuid.compareTo(o.uuid);
        }
        return result;
    }
}
