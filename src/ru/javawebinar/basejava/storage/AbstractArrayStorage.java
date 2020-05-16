package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume getItem(Object cursor) {
        return storage[(Integer) cursor];
    }

    @Override
    protected void saveItem(Object cursor, Resume resume) {
        if (size < STORAGE_LIMIT) {
            keepElement((Integer) cursor, resume);
            size++;
        } else {
            throw new StorageException("Array Storage is overflow", resume.getUuid());
        }
    }

    @Override
    protected void deleteItem(Object cursor) {
        removeElement((Integer) cursor);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateItem(Object cursor, Resume resume) {
        storage[(Integer) cursor] = resume;
    }

    protected abstract void removeElement(int index);

    protected abstract void keepElement(int index, Resume resume);
}
