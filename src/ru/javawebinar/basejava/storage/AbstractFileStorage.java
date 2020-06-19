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
        Objects.requireNonNull(directory, " directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        } else if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable|writebal");
        }
        this.directory = directory;
    }

    @Override
    protected File getCursor(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getItem(File file) {
        try {
            return read(file);
        } catch (IOException e) {
            throw new StorageException("Can't read " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void saveItem(File file, Resume resume) {
        try {
            file.createNewFile();
            write(file, resume);
        } catch (IOException e) {
            throw new StorageException("Can't create " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void deleteItem(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error ", file.getName());
        } else {
            file.delete();
        }
    }

    @Override
    protected void updateItem(File file, Resume resume) {
        try {
            write(file, resume);
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
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    protected List<File> getFiles() {
        if (directory.listFiles() != null) {
            List<File> getFiles = new ArrayList<>();
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isFile()) {
                    getFiles.add(file);
                }
            }
            return getFiles;
        } else {
            throw new StorageException("Directory read error ", null);
        }
    }

    @Override
    protected boolean isItemExist(File file) {
        return file.exists();
    }

    protected abstract Resume read(File file) throws IOException;

    protected abstract void write(File file, Resume resume) throws IOException;
}
