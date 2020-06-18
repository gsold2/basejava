package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Position {

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
        return subTitel.equals(position.subTitel);
    }

    @Override
    public int hashCode() {
        int result = startData.hashCode();
        result = 31 * result + endData.hashCode();
        result = 31 * result + subTitel.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Period{" +
                "startData=" + startData +
                ", endData=" + endData +
                ", subTitel='" + subTitel + '\'' +
                ", description='" + description + '\'' +
                '}' + '\n';
    }
}
