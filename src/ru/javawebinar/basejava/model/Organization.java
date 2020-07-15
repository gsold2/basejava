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

    private Link homePage;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(String name, Position... positions) {
        this(name, "", positions);
    }

    public Organization(String name, String url, Position... positions) {
        this.homePage = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    public Organization(String name, String url, List<Position> positions) {
        this.homePage = new Link(name, url);
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private YearMonth startData;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private YearMonth endData;
        private String subTitel;
        private String description;

        public Position() {
        }

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
