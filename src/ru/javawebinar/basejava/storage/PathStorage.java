package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.serialization.SerializationStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> implements Serializable {

    protected Path directory;
    protected SerializationStrategy strategy;

    protected PathStorage(Path directory, SerializationStrategy strategy) {
        Objects.requireNonNull(directory, " directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toString() + " is not directory");
        } else if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not readable|writebal");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    protected Path getCursor(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected Resume getItem(Path path) {
        try {
            return strategy.read(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error " + getFileName(path), e);
        }
    }

    @Override
    protected void saveItem(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("File save error " + getFileName(path), e);
        }
        updateItem(path, resume);
    }

    @Override
    protected void deleteItem(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error " + getFileName(path), e);
        }
    }

    @Override
    protected void updateItem(Path path, Resume resume) {
        try {
            strategy.write(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("File update error " + getFileName(path), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        return getPathsList().map(this::getItem).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getPathsList().forEach(this::deleteItem);
    }

    @Override
    public int size() {
        return (int) getPathsList().count();
    }

    @Override
    protected boolean isItemExist(Path path) {
        return Files.isRegularFile(path);
    }

    protected Stream<Path> getPathsList() {
        try {
            return Files.list(directory).filter(e -> e.toFile().isFile());
        } catch (IOException e) {
            throw new StorageException("Files get error " + directory.toAbsolutePath(), e);
        }
    }

    protected String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
