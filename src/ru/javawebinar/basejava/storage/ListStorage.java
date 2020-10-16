package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

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
        return null;
    }

    @Override
    protected Resume getItem(Integer cursor) {
        return storage.get(cursor);
    }

    @Override
    protected void saveItem(Integer cursor, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void deleteItem(Integer cursor) {
        storage.remove(cursor.intValue());
    }

    @Override
    protected void updateItem(Integer cursor, Resume resume) {
        storage.set(cursor, resume);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(storage);
    }

    @Override
    protected boolean isItemExist(Integer cursor) {
        return cursor != null;
    }
}
