package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ArrayStorage;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for ru.javawebinar.basejava.storage.ArrayStorage and ru.javawebinar.basejava.storage.SortedArrayStorage
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();
//    private static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.setUuid("uuid1");
        final Resume r2 = new Resume();
        r2.setUuid("uuid2");
        final Resume r3 = new Resume();
        r3.setUuid("uuid3");
        final Resume r4 = new Resume();
        r4.setUuid("uuid4");
        final Resume r5 = new Resume();
        r5.setUuid("uuid5");
        final Resume r6 = new Resume();
        r6.setUuid("uuid6");
        final Resume rError = new Resume();
        rError.setUuid("uuid2");

        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r3);
        printAll();
        ARRAY_STORAGE.save(r5);
        printAll();
        ARRAY_STORAGE.save(r6);
        printAll();
        ARRAY_STORAGE.save(r1);
        printAll();
        ARRAY_STORAGE.save(r2);
        printAll();
        ARRAY_STORAGE.delete(r4.getUuid());
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();

        System.out.println("Update r2");
        ARRAY_STORAGE.update(rError);
        if (ARRAY_STORAGE.get("uuid2").getUuid().equals("uuid2")) {
            System.out.println("Update worked");
        } else {
            System.out.println("Update didn't work");
        }
        ARRAY_STORAGE.update(r4);
        printAll();

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
