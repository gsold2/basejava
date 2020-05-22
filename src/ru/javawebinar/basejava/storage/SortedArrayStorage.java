package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getCursor(String uuid) {
        Resume searchKey = new Resume(uuid, "false name");
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void removeElement(int index) {
        System.arraycopy(storage, (index + 1), storage, index, (size - (index + 1)));
    }

    @Override
    protected void keepElement(int index, Resume resume) {
        if (index < 0) {
            index = -index - 1;
        }
        System.arraycopy(storage, index, storage, (index + 1), (size - index));
        storage[index] = resume;
    }
}
