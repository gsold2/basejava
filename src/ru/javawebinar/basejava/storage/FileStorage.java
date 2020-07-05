package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.serialization.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> implements Serializable {

    protected File directory;
    protected SerializationStrategy strategy;

    protected FileStorage(File directory, SerializationStrategy strategy) {
        Objects.requireNonNull(directory, " directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.toString() + " is not directory");
        } else if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable|writebal");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    protected File getCursor(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getItem(File file) {
        try {
            return strategy.read(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Can't read " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void saveItem(File file, Resume resume) {
        try {
            if (file.createNewFile()) {
                updateItem(file, resume);
            }
        } catch (IOException e) {
            throw new StorageException("Can't create " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void deleteItem(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error ", file.getName());
        }
    }

    @Override
    protected void updateItem(File file, Resume resume) {
        try {
            strategy.write(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("Can't update " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getFiles()) {
            resumes.add(getItem(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        for (File file : getFiles()) {
            deleteItem(file);
        }
    }

    @Override
    public int size() {
        return getFiles().size();
    }

    @Override
    protected boolean isItemExist(File file) {
        return file.exists();
    }

    protected List<File> getFiles() {
        List<File> getFiles = new ArrayList<>();
        if (directory.listFiles() != null) {
            for (File file : directory.listFiles())
                if (file.isFile()) {
                    getFiles.add(file);
                }
        }
        return getFiles;
    }
}
