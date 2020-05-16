package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
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
    protected Resume getCursor(String uuid) {
        return new Resume(uuid);
    }

    @Override
    protected Resume getItem(Object searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    protected void saveItem(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    protected void deleteItem(Object searchKey) {
        storage.remove(searchKey.toString());
    }

    @Override
    protected void updateItem(Object searchKey, Resume resume) {
        storage.replace(searchKey.toString(), resume);
    }
}