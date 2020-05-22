package ru.javawebinar.basejava.storage;

import static org.junit.Assert.fail;

import org.junit.Test;
import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void stackOverFlow() {
        storage.clear();
        try {
            for (int i = 1; i <= AbstractArrayStorage.STORAGE_LIMIT + 1; i++) {
                storage.save(new Resume("fullName" + i));
            }
            fail("Not a Stack overflow exception");
        } catch (StorageException thrown) {
            System.out.println("This exception was expected from " + storage.getClass().toString());
        }
    }
}