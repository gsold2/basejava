package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serialization.StreamSerialization;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toPath(), new StreamSerialization()));
    }
}