package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.serialization.XmlStreamSerialization;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toPath(), new XmlStreamSerialization()));
    }
}