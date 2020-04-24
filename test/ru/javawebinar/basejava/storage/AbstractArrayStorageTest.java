package ru.javawebinar.basejava.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        storage.update(RESUME_2);
        assertResume(RESUME_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertResume(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test
    public void get() throws Exception {
        assertResume(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("fail_uuid");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resume = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        assertEquals(resume.length, storage.getAll().length);
        assertArrayEquals(resume, storage.getAll());
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void stackOverFlow() throws Exception {
        storage.clear();
        try {
            for (int i = 1; i <= AbstractArrayStorage.STORAGE_LIMIT + 1; i++) {
                storage.save(new Resume("uuid" + i));
            }
            fail("Not a Stack overflow exception");
        } catch (StorageException thrown) {
            System.out.println("This exception was expected from " + storage.getClass().toString());
        }
    }

    protected void assertResume(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }

}