package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Period {

    private final YearMonth startData;
    private final YearMonth endData;
    private final String subTitel;
    private final String description;

    public Period(YearMonth startData, YearMonth endData, String subTitel, String description) {
        Objects.requireNonNull(startData, "start must not be null");
        Objects.requireNonNull(endData, "start must not be null");
        Objects.requireNonNull(subTitel, "start must not be null");
        this.startData = startData;
        this.endData = endData;
        this.subTitel = subTitel;
        this.description = description;
    }

    public Period(YearMonth startData, YearMonth endData, String subTitel) {
        this(startData, endData, subTitel, "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        if (!startData.equals(period.startData)) return false;
        if (!endData.equals(period.endData)) return false;
        return subTitel.equals(period.subTitel);
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
