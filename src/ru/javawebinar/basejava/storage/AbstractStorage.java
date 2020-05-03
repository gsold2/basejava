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
        int cursor = getIndex(resume.getUuid());
        if (cursor > -1) {
            updateItem(cursor, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int cursor = getIndex(resume.getUuid());
        if (cursor <= -1) {
            saveItem(cursor, resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int cursor = getIndex(uuid);
        if (cursor > -1) {
            return getItem(cursor, uuid);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int cursor = getIndex(uuid);
        if (cursor > -1) {
            deleteItem(cursor, uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract Resume getItem(int cursor, String uuid);

    protected abstract void saveItem(int cursor, Resume resume);

    protected abstract void deleteItem(int cursor, String uuid);

    protected abstract void updateItem(int cursor, Resume resume);
}
