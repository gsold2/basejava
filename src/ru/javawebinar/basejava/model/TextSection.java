package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Objects;

public class TextSection extends AbstractSection implements Serializable {

    public static final TextSection EMPTY = new TextSection("");

    private String content;

    public TextSection() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextSection(String content) {
        Objects.requireNonNull(content, "text must not be null");
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "text='" + content + '\'' +
                '}' + '\n';
    }
}
