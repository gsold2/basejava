package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    protected Path directory;

    protected AbstractPathStorage(Path directory) {
        Objects.requireNonNull(directory, " directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toString() + " is not directory");
        } else if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not readable|writebal");
        }
        this.directory = directory;
    }

    @Override
    protected Path getCursor(String uuid) {
        return Paths.get(String.valueOf(directory), uuid);
    }

    @Override
    protected Resume getItem(Path file) {
        try {
            return read(new BufferedInputStream(new FileInputStream(file.toFile())));
        } catch (IOException e) {
            throw new StorageException("Can't read " + file.toAbsolutePath(), file.toString(), e);
        }
    }

    @Override
    protected void saveItem(Path file, Resume resume) {
        try {
            Files.createFile(file);
            write(new BufferedOutputStream(new FileOutputStream(file.toFile())), resume);
        } catch (IOException e) {
            throw new StorageException("Can't create " + file.toAbsolutePath(), file.toString(), e);
        }
    }

    @Override
    protected void deleteItem(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("File delete error ", file.toString(), e);
        }
    }

    @Override
    protected void updateItem(Path file, Resume resume) {
        try {
            write(new BufferedOutputStream(new FileOutputStream(file.toFile())), resume);
        } catch (IOException e) {
            throw new StorageException("Can't update " + file.toAbsolutePath(), file.toString(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).filter(e -> e.toFile().isFile()).forEach(path -> resumes.add(getItem(path)));
        } catch (IOException e) {
            throw new StorageException("Files read error ", directory.toAbsolutePath().toString(), e);
        }
        return resumes;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteItem);
        } catch (IOException e) {
            throw new StorageException("Files delete error ", directory.toAbsolutePath().toString(), e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error ", directory.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected boolean isItemExist(Path file) {
        return Files.exists(file);
    }

    protected abstract Resume read(InputStream is) throws IOException;

    protected abstract void write(OutputStream os, Resume resume) throws IOException;
}
