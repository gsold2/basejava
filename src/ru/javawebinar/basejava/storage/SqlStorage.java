package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeStatement("DELETE FROM resume", ps -> {
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            String[] types = new String[resume.getContact().size()];
            int numberOfTypes = types.length;
            if (numberOfTypes > 0) {
                Arrays.fill(types, "?");
                String variableParameters = String.join(",", types);
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM contact WHERE resume_uuid=?\n" +
                                " AND type NOT IN(" + variableParameters + ")")) {
                    ps.setString(1, resume.getUuid());
                    ContactType[] contactType = resume.getContact().keySet().toArray(new ContactType[numberOfTypes]);
                    String[] entres = Arrays.stream(contactType).map(Object::toString).toArray(String[]::new);
                    for (int i = 2; i < numberOfTypes + 2; i++) {
                        ps.setString(i, entres[i - 2]);
                    }
                    ps.executeUpdate();
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE contact SET value=? WHERE resume_uuid=? AND type=?")) {
                for (Map.Entry<ContactType, String> contact : resume.getContact().entrySet()) {
                    ps.setString(1, contact.getValue());
                    ps.setString(2, resume.getUuid());
                    ps.setString(3, contact.getKey().name());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name)\n" +
                            " VALUES (?,?)\n" +
                            " ON CONFLICT DO NOTHING")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                if (ps.executeUpdate() == 0) {
                    throw new ExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> contact : resume.getContact().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, contact.getKey().name());
                    ps.setString(3, contact.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeStatement(
                "SELECT * FROM resume r\n" +
                        " LEFT JOIN contact c\n" +
                        " ON r.uuid=c.resume_uuid\n" +
                        " WHERE r.uuid=?", ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    if (rs.getString("resume_uuid") != null) {
                        do {
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            String value = rs.getString("value");
                            resume.addContact(type, value);
                        } while (rs.next());
                    }
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeStatement("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new HashMap<>();
        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume r")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    resumes.put(uuid, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    String value = rs.getString("value");
                    resumes.get(uuid).addContact(type, value);
                }
            }
            return null;
        });
        List<Resume> list = new ArrayList<>(resumes.values());
        Collections.sort(list);
        return list;
    }

    @Override
    public int size() {
        return sqlHelper.executeStatement("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
