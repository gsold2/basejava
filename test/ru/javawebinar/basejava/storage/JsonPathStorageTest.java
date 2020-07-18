package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serialization.JsonStreamSerialization;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toPath(), new JsonStreamSerialization()));
    }
}