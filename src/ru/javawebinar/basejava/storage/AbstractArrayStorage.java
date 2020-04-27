package ru.javawebinar.basejava.storage;

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
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void setItem(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getItem(int index) {
        return storage[index];
    }

    @Override
    protected void decreaseSize() {
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void increaseSize() {
        size++;
    }

    @Override
    protected boolean isFreePlace() {
        return (size < STORAGE_LIMIT);
    }
}
