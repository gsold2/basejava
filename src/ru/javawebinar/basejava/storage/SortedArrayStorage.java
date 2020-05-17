package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getCursor(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
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

    @Override
    protected List<Resume> getList(){
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
