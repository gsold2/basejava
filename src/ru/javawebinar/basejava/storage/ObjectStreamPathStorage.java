package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage implements Serializable {

    protected SerializationStrategy strategy;

    protected ObjectStreamPathStorage(Path directory, SerializationStrategy strategy) {
        super(directory);
        this.strategy = strategy;
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        return strategy.read(is);
    }

    @Override
    protected void write(OutputStream os, Resume resume) throws IOException {
        strategy.write(os, resume);
    }
}
