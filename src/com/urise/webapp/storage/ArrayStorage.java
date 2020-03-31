package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int index = 0;

    public void clear() {
        Arrays.fill(storage, 0, index, null);
        index = 0;
    }

    public void update(Resume r) {
        if (isResumeExist(r.getUuid())) {
            System.out.println("Update");
        } else {
            System.out.println("Resume doesn't exist");
        }

    }

    public void save(Resume r) {
        if (index < 10000 & !isResumeExist(r.getUuid())) {
            storage[index] = r;
            index++;
        } else {
            System.out.println("Resume exists");
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < index; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Resume doesn't exist");
        return null;
    }

    public void delete(String uuid) {
        if (isResumeExist(uuid)) {
            for (int i = 0; i < index; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    storage[i] = storage[index - 1];
                    storage[index - 1] = null;
                    index--;
                    break;
                }
            }
        } else {
            System.out.println("Resume doesn't exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, index);
    }

    public int size() {
        return index;
    }

    public boolean isResumeExist(String uuid) {
        if (index > 0) {
            for (int i = 0; i < index; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return true;
                }
            }
        }
        return false;
    }
}
