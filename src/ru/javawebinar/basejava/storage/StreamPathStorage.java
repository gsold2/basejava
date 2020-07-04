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

public class StreamPathStorage extends AbstractStorage<Path> implements Serializable {

    protected Path directory;
    protected SerializationStrategy strategy;

    protected StreamPathStorage(Path directory, SerializationStrategy strategy) {
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
    protected Resume getItem(Path file) {
        try {
            return read(Files.newInputStream(file));
        } catch (IOException e) {
            throw new StorageException("Can't read " + file.toAbsolutePath(), file.toString(), e);
        }
    }

    @Override
    protected void saveItem(Path file, Resume resume) {
        updateItem(file, resume);
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
            write(Files.newOutputStream(file), resume);
        } catch (IOException e) {
            throw new StorageException("Can't update " + file.toAbsolutePath(), file.toString(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        return getPaths().stream().map(this::getItem).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getPaths().forEach(this::deleteItem);
    }

    @Override
    public int size() {
        return getPaths().size();
    }

    @Override
    protected boolean isItemExist(Path file) {
        return Files.exists(file);
    }

    protected List<Path> getPaths() {
        try {
            return Files.list(directory).filter(e -> e.toFile().isFile()).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Can't get files ", directory.toAbsolutePath().toString(), e);
        }
    }

    protected Resume read(InputStream is) throws IOException {
        return strategy.read(is);
    }

    protected void write(OutputStream os, Resume resume) throws IOException {
        strategy.write(os, resume);
    }
}
