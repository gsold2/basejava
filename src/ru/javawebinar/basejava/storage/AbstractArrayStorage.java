package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Abstract Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
            System.out.println("Resume " + resume.getUuid() + " was updated");
        } else {
            System.out.println("Resume " + resume.getUuid() + " doesn't exist");
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index <= -1) {
            if (size < STORAGE_LIMIT) {
                saveItem(index, resume);
                size++;
            } else {
                System.out.println("Array Storage is overflow");
            }
        } else {
            System.out.println("Resume " + resume.getUuid() + " exists");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            return storage[index];
        }
        System.out.println("Resume " + uuid + " doesn't exist");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            deleteItem(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume " + uuid + " doesn't exist");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void deleteItem(int index);

    protected abstract void saveItem(int index, Resume resume);
}
