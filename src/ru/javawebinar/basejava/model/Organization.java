package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    public static final Organization EMPTY = new Organization("", "", Position.EMPTY);
    private Link homePage;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(String name, Position... positions) {
        this(name, null, positions);
    }

    public Organization(String name, String url, Position... positions) {
        this.homePage = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    public Organization(String name, String url, List<Position> positions) {
        this.homePage = new Link(name, url);
        this.positions = positions;
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", periods=" + positions +
                '}' + '\n';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {


        public static final Position EMPTY = new Position();
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private YearMonth startData;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private YearMonth endData;
        private String subTitel;
        private String description;

        public Position() {
        }

        public Position(YearMonth startData, YearMonth endData, String subTitel) {
            this(startData, endData, subTitel, null);
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

        public YearMonth getStartData() {
            return startData;
        }

        public YearMonth getEndData() {
            return endData;
        }

        public String getSubTitel() {
            return subTitel;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(startData, position.startData) &&
                    Objects.equals(endData, position.endData) &&
                    Objects.equals(subTitel, position.subTitel) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startData, endData, subTitel, description);
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
