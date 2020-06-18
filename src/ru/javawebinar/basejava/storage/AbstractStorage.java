package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Abstract based storage for Resumes
 */
public abstract class AbstractStorage<SK> implements Storage {

    private final static Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume resume) {
        LOGGER.info("Update " + resume);
        SK searchKey = getExistedKey(resume.getUuid());
        updateItem(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        LOGGER.info("Save " + resume);
        SK searchKey = getNotExistedKey(resume.getUuid());
        saveItem(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("Get " + uuid);
        SK searchKey = getExistedKey(uuid);
        return getItem(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("Delete " + uuid);
        SK searchKey = getExistedKey(uuid);
        deleteItem(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOGGER.info("GetAllSorted");
        List<Resume> list = getList();
        Collections.sort(list);
        return list;
    }

    private SK getExistedKey(String uuid) {
        SK cursor = getCursor(uuid);
        if (isItemExist(cursor)) {
            return cursor;
        } else {
            LOGGER.warning("Resume " + uuid + " doesn't exist");
            throw new NotExistStorageException(uuid);
        }
    }

    private SK getNotExistedKey(String uuid) {
        SK cursor = getCursor(uuid);
        if (!isItemExist(cursor)) {
            return cursor;
        } else {
            LOGGER.warning("Resume " + uuid + " already exists");
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract SK getCursor(String uuid);

    protected abstract Resume getItem(SK cursor);

    protected abstract void saveItem(SK cursor, Resume resume);

    protected abstract void deleteItem(SK cursor);

    protected abstract void updateItem(SK cursor, Resume resume);

    protected abstract List<Resume> getList();

    protected abstract boolean isItemExist(SK cursor);
}
