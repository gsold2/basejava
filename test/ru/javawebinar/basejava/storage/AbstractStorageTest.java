package ru.javawebinar.basejava.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.*;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("D:/basejava/storage");
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = ResumeTestData.createResumeInstance(UUID_1, "name1");

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = ResumeTestData.createResumeInstance(UUID_2, "name2");

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = ResumeTestData.createResumeInstance(UUID_3, "name3");

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = ResumeTestData.createResumeInstance(UUID_4, "name2");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        storage.update(RESUME_2);
        assertResume(RESUME_2);
        assertSize(3);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertResume(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        assertResume(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("fail_uuid");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(actualResumes);
        assertEquals(actualResumes, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    protected void assertResume(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}
