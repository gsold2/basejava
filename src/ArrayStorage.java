import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int index = 0;

    void clear() {
        Arrays.fill(storage, 0, index, null);
        index = 0;
    }

    void save(Resume r) {
        boolean isResumeExist = false;
        if (index < 1000) {
            for (int i = 0; i < index; i++) {
                if (storage[i].toString().equals(r.toString())) {
                    isResumeExist = true;
                    i = index;
                }
            }
        }
        if (!isResumeExist & index < 1000) {
            storage[index] = r;
            index++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < index; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < index; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = storage[index - 1];
                storage[index - 1] = null;
                i = index;
                index--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, index);
    }

    int size() {
        return index;
    }
}
