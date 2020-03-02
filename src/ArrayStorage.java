import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int index = -1;

    void clear() {
        Arrays.fill(storage, 0, (index + 1), null);
        index = -1;
    }

    void save(Resume r) {
        boolean isResumeExist = false;
        for (int number = 0; number <= index; number++) {
            if (storage[number].toString().equals(r.toString())) {
                isResumeExist = true;
                number = (index + 1);
            }
        }
        if (!isResumeExist) {
            index++;
            storage[index] = r;
        }
    }

    Resume get(String uuid) {
        Resume uuidInStorage = null;
        for (int number = 0; number <= index; number++) {
            if (storage[number].uuid.equals(uuid)) {
                uuidInStorage = storage[number];
                number = (index + 1);
            }
        }
        return uuidInStorage;
    }

    void delete(String uuid) {
        for (int number = 0; number <= index; number++) {
            if (storage[number].toString().equals(uuid)) {
                storage[number] = storage[index];
                storage[index] = null;
                number = (index + 1);
                index--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, (index + 1));
    }

    int size() {
        return (index + 1);
    }
}
