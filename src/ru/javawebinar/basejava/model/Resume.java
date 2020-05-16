package ru.javawebinar.basejava.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    public Resume() {
        this.uuid = UUID.randomUUID().toString();
        this.fullName = "";
    }

    public Resume(String uuid) {
        this.uuid = uuid;
        this.fullName = "";
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return this.uuid;
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
        return this.uuid.hashCode();
    }

    @Override
    public String toString() {
        return this.uuid;
    }

    @Override
    public int compareTo(Resume o) {
        if (!o.fullName.equals("")) {
            int result = this.fullName.compareTo(o.fullName);
            if (result == 0) {
                return this.uuid.compareTo(o.uuid);
            }
            return result;
        }
        return this.uuid.compareTo(o.uuid);
    }
}
