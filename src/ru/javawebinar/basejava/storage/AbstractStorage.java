package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

/**
 * Abstract based storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedKey(resume.getUuid());
        updateItem(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistedKey(resume.getUuid());
        saveItem(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedKey(uuid);
        return getItem(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey= getExistedKey(uuid);
        deleteItem(searchKey);
    }

    protected boolean isItemExist(String uuid) {
        return ((Integer) getCursor(uuid) >= 0);
    }

    protected Object getExistedKey(String uuid) {
        if (isItemExist(uuid)) {
            return getCursor(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected Object getNotExistedKey(String uuid) {
        if (!isItemExist(uuid)) {
            return getCursor(uuid);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract Object getCursor(String uuid);

    protected abstract Resume getItem(Object cursor);

    protected abstract void saveItem(Object cursor, Resume resume);

    protected abstract void deleteItem(Object cursor);

    protected abstract void updateItem(Object cursor, Resume resume);
}
