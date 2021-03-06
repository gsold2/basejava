package ru.javawebinar.basejava.serialization;

import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public interface SerializationStrategy {

    Resume read(InputStream is) throws IOException;

    void write(OutputStream os, Resume resume) throws IOException;
}
