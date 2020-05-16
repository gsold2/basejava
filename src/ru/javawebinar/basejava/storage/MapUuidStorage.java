package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
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

    @Override
    protected boolean isItemExist(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected String getCursor(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getItem(Object uuid) {
        return storage.get(uuid.toString());
    }

    @Override
    protected void saveItem(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    protected void deleteItem(Object uuid) {
        storage.remove(uuid.toString());
    }

    @Override
    protected void updateItem(Object searchKey, Resume resume) {
        storage.replace(searchKey.toString(), resume);
    }
}