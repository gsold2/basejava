package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size < STORAGE_LIMIT) {
            if (index == size) {
                storage[size] = resume;
                size++;
            } else if (!storage[index].equals(resume)) {
                Resume[] shiftRight = Arrays.copyOfRange(storage, index, size);
                System.arraycopy(shiftRight, 0, storage, (index + 1), shiftRight.length);
                storage[index] = resume;
                size++;
            } else {
                System.out.println("Resume " + resume.getUuid() + " exists");
            }
        } else {
            System.out.println("Array Storage is overflow");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (storage[index].getUuid().equals(uuid)) {
            Resume[] shiftRight = Arrays.copyOfRange(storage, index + 1, size);
            System.arraycopy(shiftRight, 0, storage, index, shiftRight.length);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume " + uuid + " doesn't exist");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        int index = Arrays.binarySearch(storage, 0, size, searchKey);
        if (index >= 0) {
            return index;
        } else {
            return Math.abs(-index - 1);
        }
    }
}
