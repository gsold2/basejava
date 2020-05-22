package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
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
    protected Resume getItem(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void saveItem(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void deleteItem(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void updateItem(String searchKey, Resume resume) {
        storage.replace(searchKey, resume);
    }

    @Override
    protected List<Resume> getList(){
        return new ArrayList<>(storage.values());
    }
}
