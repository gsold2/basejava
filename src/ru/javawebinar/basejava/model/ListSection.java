package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection implements Serializable {

    private final List<String> items;

    public ListSection(String... items) {
        Objects.requireNonNull(items, "list must not be null");
        this.items = Arrays.asList(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }


    @Override
    public String toString() {
        return "ListSection{" +
                "list=" + items +
                '}' + '\n';
    }
}
