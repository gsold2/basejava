package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getItem(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void saveItem(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void deleteItem(Object index) {
        int cursor = (Integer) index;
        storage.remove(cursor);
    }

    @Override
    protected void updateItem(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }
}
