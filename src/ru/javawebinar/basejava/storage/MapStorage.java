package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage implements Storage {

    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        if (isExistedKey(resume.getUuid())) {
            storage.replace(resume.getUuid(), resume);
        }
    }

    @Override
    public void save(Resume resume) {
        if (!isNotExistedKey(resume.getUuid())) {
            storage.put(resume.getUuid(), resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (isExistedKey(uuid)) {
            return storage.get(uuid);
        }
        return null;
    }

    @Override
    public void delete(String uuid) {
        if (isExistedKey(uuid)) {
            storage.remove(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(storage.values());
        Collections.sort(list);
        return list;
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected boolean isExistedKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return true;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected boolean isNotExistedKey(String uuid) {
        if (!storage.containsKey(uuid)) {
            return false;
        } else {
            throw new ExistStorageException(uuid);
        }
    }
}
