package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    protected File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        } else if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable|writebal");
        }
        this.directory = directory;
    }

    @Override
    protected File getCursor(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getItem(File file) {
        return read(file);
    }

    @Override
    protected void saveItem(File file, Resume resume) {
        try {
            file.createNewFile();
            write(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void deleteItem(File file) {
        file.delete();
    }

    @Override
    protected void updateItem(File file, Resume resume) {
        try {
            write(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getFiles()) {
            resumes.add(read(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        for (File file : getFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return getFiles().size();
    }

    protected List<File> getFiles() {
        List<File> getFiles = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile()) {
                getFiles.add(file);
            }
        }
        return getFiles;
    }

    @Override
    protected boolean isItemExist(File file) {
        return file.isFile();
    }

    protected abstract Resume read(File file);

    protected abstract void write(File file, Resume resume) throws IOException;
}
