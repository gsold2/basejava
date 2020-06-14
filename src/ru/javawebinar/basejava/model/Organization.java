package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

public class Organization {

    private final Link homePage;
    private final List<Period> periods;

    public Organization(String name, YearMonth startData, YearMonth endData, String subTitel) {
        this(name, "", startData, endData, subTitel, "");
    }

    public Organization(String name, YearMonth startData, YearMonth endData, String subTitel, String description) {
        this(name, "", startData, endData, subTitel, description);
    }

    public Organization(String name, String url, YearMonth startData, YearMonth endData, String subTitel,
                        String description) {
        this.homePage = new Link(name, url);
        this.periods = Collections.singletonList(new Period(startData, endData, subTitel, description));
    }

    public Organization(String name, String url, List<Period> periods) {
        this.homePage = new Link(name, url);
        this.periods = periods;
    }

    public Organization(String name, List<Period> periods) {
        this(name, "", periods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", periods=" + periods +
                '}' + '\n';
    }
}
