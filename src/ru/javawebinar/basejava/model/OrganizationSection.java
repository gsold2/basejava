package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection implements Serializable {

    private final List<Organization> organizations;

    public OrganizationSection(Organization... organizations) {
        Objects.requireNonNull(organizations, "list must not be null");
        this.organizations = Arrays.asList(organizations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return "ExperienceSection{" +
                "list=" + organizations +
                '}' + '\n';
    }
}
