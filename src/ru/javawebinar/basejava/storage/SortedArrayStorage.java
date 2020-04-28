package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveItem(int index, Resume resume) {
        if (index < 0) {
            index = -index - 1;
        }
        System.arraycopy(storage, index, storage, (index + 1), (size - index));
        storage[index] = resume;
    }

    @Override
    protected void deleteItem(int index) {
        System.arraycopy(storage, (index + 1), storage, index, (size - (index + 1)));
    }
}
