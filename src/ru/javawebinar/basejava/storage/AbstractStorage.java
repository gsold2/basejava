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
        String uuid = resume.getUuid();
        if (isItemExist(uuid)) {
            updateItem(getCursor(uuid), resume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (!isItemExist(uuid)) {
            saveItem(getCursor(uuid), resume);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (isItemExist(uuid)) {
            return getItem(getCursor(uuid));
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (isItemExist(uuid)) {
            deleteItem(getCursor(uuid));
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected boolean isItemExist(String uuid) {
        return ((Integer) getCursor(uuid) > -1);
    }

    protected abstract Object getCursor(String uuid);

    protected abstract Resume getItem(Object cursor);

    protected abstract void saveItem(Object cursor, Resume resume);

    protected abstract void deleteItem(Object cursor);

    protected abstract void updateItem(Object cursor, Resume resume);
}
