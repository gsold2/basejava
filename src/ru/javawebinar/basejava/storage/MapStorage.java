package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    protected LinkedHashMap<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }

    @Override
    protected Resume getItem(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void saveItem(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteItem(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void updateItem(int index, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }
}
