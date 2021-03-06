package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection implements Serializable {

    public static final ListSection EMPTY = new ListSection("");

    private List<String> items;

    public ListSection() {
    }

    public ListSection(String... items) {
        Objects.requireNonNull(items, "list must not be null");
        this.items = Arrays.asList(items);
    }

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "list must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
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
