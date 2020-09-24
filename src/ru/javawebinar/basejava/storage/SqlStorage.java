package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.requstStatement("DELETE FROM resume", ps -> {
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.requstStatement("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.requstStatement("INSERT INTO resume (uuid, full_name) " +
                "VALUES (?,?)" +
                "ON CONFLICT DO NOTHING", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new ExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.requstStatement("SELECT * FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.requstStatement("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.requstStatement("SELECT * FROM resume ORDER BY uuid", ps -> {
            List<Resume> storage = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                storage.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return storage;
        });
    }

    @Override
    public int size() {
        return sqlHelper.requstStatement("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}
