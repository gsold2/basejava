package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void deleteItem(int index) {
        Resume[] shiftPart = Arrays.copyOfRange(storage, index + 1, size);
        System.arraycopy(shiftPart, 0, storage, index, shiftPart.length);
    }

    protected void saveItem(int index, Resume resume) {
        if (index == size) {
            storage[size] = resume;
        } else {
            index = -index - 1;
            Resume[] shiftPart = Arrays.copyOfRange(storage, index, size);
            System.arraycopy(shiftPart, 0, storage, (index + 1), shiftPart.length);
            storage[index] = resume;
        }
    }
}
