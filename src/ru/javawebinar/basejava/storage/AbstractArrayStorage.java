package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected Resume getItem(Integer cursor) {
        return storage[cursor];
    }

    @Override
    protected void saveItem(Integer cursor, Resume resume) {
        if (size < STORAGE_LIMIT) {
            keepElement(cursor, resume);
            size++;
        } else {
            throw new StorageException("Array Storage is overflow", resume.getUuid());
        }
    }

    @Override
    protected void deleteItem(Integer cursor) {
        removeElement(cursor);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateItem(Integer cursor, Resume resume) {
        storage[cursor] = resume;
    }

    @Override
    protected List<Resume> getList(){
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    protected abstract void removeElement(int index);

    protected abstract void keepElement(int index, Resume resume);
}
