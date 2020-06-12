package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Organization {

    private final Link homePage;
    private final YearMonth startData;
    private final YearMonth endData;
    private final String subTitel;
    private final String description;

    public Organization(String name, String url, YearMonth startData, YearMonth endData, String subTitel, String description) {
        Objects.requireNonNull(startData, "start must not be null");
        Objects.requireNonNull(endData, "start must not be null");
        Objects.requireNonNull(subTitel, "start must not be null");
        this.homePage = new Link(name, url);
        this.startData = startData;
        this.endData = endData;
        this.subTitel = subTitel;
        this.description = description;
    }

    public Organization(String name, YearMonth startData, YearMonth endData, String subTitel, String description) {
        this(name, "", startData, endData, subTitel, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        if (!startData.equals(that.startData)) return false;
        if (!endData.equals(that.endData)) return false;
        return subTitel.equals(that.subTitel);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + startData.hashCode();
        result = 31 * result + endData.hashCode();
        result = 31 * result + subTitel.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "homePage='" + homePage + '\'' +
                ", start=" + startData +
                ", end=" + endData +
                ", subTitel='" + subTitel + '\'' +
                ", text='" + description + '\'' +
                '}' + '\n';
    }
}
