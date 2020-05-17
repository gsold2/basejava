package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getCursor(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getItem(Object cursor) {
        return storage.get((Integer) cursor);
    }

    @Override
    protected void saveItem(Object cursor, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void deleteItem(Object cursor) {
        storage.remove(((Integer) cursor).intValue());
    }

    @Override
    protected void updateItem(Object cursor, Resume resume) {
        storage.set((Integer) cursor, resume);
    }

    @Override
    protected List<Resume> getList(){
        return storage;
    }
}
