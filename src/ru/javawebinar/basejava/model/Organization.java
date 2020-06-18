package ru.javawebinar.basejava.model;

import java.lang.reflect.Array;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Organization {

    private final Link homePage;
    private final List<Position> positions;

    public Organization(String name, List<Position> positions) {
        this(name, "", positions);
    }

    public Organization(String name, String url, List<Position> positions) {
        this.homePage = new Link(name, url);
        this.positions = positions;
    }

    public Organization(String name, Position... positions) {
        this(name, "", positions);
    }

    public Organization(String name, String url, Position...positions) {
        this.homePage = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    public Organization(String name, YearMonth startData, YearMonth endData, String subTitel) {
        this(name, "", startData, endData, subTitel, "");
    }

    public Organization(String name, YearMonth startData, YearMonth endData, String subTitel, String description) {
        this(name, "", startData, endData, subTitel, description);
    }

    public Organization(String name, String url, YearMonth startData, YearMonth endData, String subTitel,
                        String description) {
        this.homePage = new Link(name, url);
        this.positions = Collections.singletonList(new Position(startData, endData, subTitel, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", periods=" + positions +
                '}' + '\n';
    }
}
