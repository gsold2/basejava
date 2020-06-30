package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.*;

public class Organization implements Serializable {

    private final Link homePage;
    private final List<Position> positions;

    public Organization(String name, Position... positions) {
        this(name, "", positions);
    }

    public Organization(String name, String url, Position... positions) {
        this.homePage = new Link(name, url);
        this.positions = Arrays.asList(positions);
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

    public static class Position implements Serializable {

        private final YearMonth startData;
        private final YearMonth endData;
        private final String subTitel;
        private final String description;

        public Position(YearMonth startData, YearMonth endData, String subTitel) {
            this(startData, endData, subTitel, "");
        }

        public Position(YearMonth startData, YearMonth endData, String subTitel, String description) {
            Objects.requireNonNull(startData, "start must not be null");
            Objects.requireNonNull(endData, "start must not be null");
            Objects.requireNonNull(subTitel, "start must not be null");
            this.startData = startData;
            this.endData = endData;
            this.subTitel = subTitel;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (!startData.equals(position.startData)) return false;
            if (!endData.equals(position.endData)) return false;
            if (!subTitel.equals(position.subTitel)) return false;
            return description.equals(position.description);
        }

        @Override
        public int hashCode() {
            int result = startData.hashCode();
            result = 31 * result + endData.hashCode();
            result = 31 * result + subTitel.hashCode();
            result = 31 * result + description.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "startData=" + startData +
                    ", endData=" + endData +
                    ", subTitel='" + subTitel + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
