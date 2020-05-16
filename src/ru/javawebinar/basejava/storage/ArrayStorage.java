package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public List<Resume> getAllSorted() {
        return Arrays.stream(Arrays.copyOf(storage, size)).sorted().collect(Collectors.toList());
    }

    @Override
    protected Integer getCursor(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void removeElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void keepElement(int index, Resume resume) {
        storage[size] = resume;
    }
}
