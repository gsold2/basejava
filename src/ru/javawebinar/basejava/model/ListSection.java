package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "list must not be null");
        this.items = items;
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
