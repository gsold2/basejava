package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exists", uuid);
    }
    public ExistStorageException(String message, String uuid) {
        super(message, uuid, null);
    }
}
