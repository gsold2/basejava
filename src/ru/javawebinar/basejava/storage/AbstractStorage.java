package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

/**
 * Abstract based storage for Resumes
 */
public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistedKey(resume.getUuid());
        updateItem(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        SK searchKey = getNotExistedKey(resume.getUuid());
        saveItem(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getExistedKey(uuid);
        return getItem(searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistedKey(uuid);
        deleteItem(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        Collections.sort(list);
        return list;
    }

    protected boolean isItemExist(String uuid) {
        return ((Integer) getCursor(uuid) >= 0);
    }

    protected SK getExistedKey(String uuid) {
        if (isItemExist(uuid)) {
            return getCursor(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected SK getNotExistedKey(String uuid) {
        if (!isItemExist(uuid)) {
            return getCursor(uuid);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract SK getCursor(String uuid);

    protected abstract Resume getItem(SK cursor);

    protected abstract void saveItem(SK cursor, Resume resume);

    protected abstract void deleteItem(SK cursor);

    protected abstract void updateItem(SK cursor, Resume resume);

    protected abstract List<Resume> getList();
}
