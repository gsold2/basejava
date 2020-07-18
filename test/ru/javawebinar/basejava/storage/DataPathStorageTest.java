package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serialization.DataStreamSerialization;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toPath(), new DataStreamSerialization()));
    }
}