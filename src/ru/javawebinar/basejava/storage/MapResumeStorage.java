package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getCursor(String uuid) {
        return new Resume(uuid, "false name");
    }

    @Override
    protected Resume getItem(Resume searchKey) {
        return storage.get(searchKey.getUuid());
    }

    @Override
    protected void saveItem(Resume searchKey, Resume resume) {
        storage.put(searchKey.getUuid(), resume);
    }

    @Override
    protected void deleteItem(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected void updateItem(Resume searchKey, Resume resume) {
        storage.replace(searchKey.getUuid(), resume);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected boolean isItemExist(Resume searchKey) {
        return storage.containsKey(searchKey.getUuid());
    }
}
