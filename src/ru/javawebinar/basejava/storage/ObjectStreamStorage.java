package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage implements Serializable {

    protected SerializationStrategy strategy;

    protected ObjectStreamStorage(File directory, SerializationStrategy strategy) {
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
