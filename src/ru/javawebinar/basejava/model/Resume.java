package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private EnumMap<EnumContacts, String> contactSection;
    private EnumMap<EnumSections, AbstractSection> dataSection;

    public Resume(String fullName) {
        Objects.requireNonNull(fullName, "fullName must be not null");
        this.fullName = fullName;
        this.uuid = UUID.randomUUID().toString();
        this.contactSection = new EnumMap<>(EnumContacts.class);
        this.dataSection = new EnumMap<>(EnumSections.class);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(fullName, "fullName must be not null");
        Objects.requireNonNull(uuid, "uuid must be not null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contactSection = new EnumMap<>(EnumContacts.class);
        this.dataSection = new EnumMap<>(EnumSections.class);
    }

    public String getUuid() {
        return this.uuid;
    }

    public EnumMap<EnumContacts, String> getContactSection() {
        return this.contactSection;
    }

    public EnumMap<EnumSections, AbstractSection> getDataSection() {
        return this.dataSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return this.uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.uuid + '(' + this.fullName + ')';
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
