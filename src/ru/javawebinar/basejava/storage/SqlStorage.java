package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.requstStatement(
                "INSERT INTO resume (uuid, full_name)\n" +
                        " VALUES (?,?)\n" +
                        " ON CONFLICT DO NOTHING", ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    if (ps.executeUpdate() == 0) {
                        throw new ExistStorageException(resume.getUuid());
                    }
                    return null;
                });
        for (Map.Entry<ContactType, String> contact : resume.getContact().entrySet()) {
                sqlHelper.requstStatement(
                        "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", ps -> {
                            ps.setString(1, resume.getUuid());
                            ps.setString(2, contact.getKey().name());
//                            if (!contact.getValue().equals("")) {
                                ps.setString(3, contact.getValue());
//                            } else {}
//                            System.out.println(resume.getUuid() + " " + contact.getKey().name() + " " + contact.getValue());
                            ps.executeUpdate();
                            return null;
                        });

        }
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.requstStatement(
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
                    do {
                        String value = rs.getString("value");
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        resume.addContact(type, value);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.requstStatement("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.requstStatement(
//                "SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
                "SELECT * FROM resume r\n" +
                        " LEFT JOIN contact c\n" +
                        " ON r.uuid=c.resume_uuid\n" +
                        "  ORDER BY full_name, uuid", ps -> {
                    List<Resume> resumes = new ArrayList<>();
//                    Map<String, Resume> resumes = new LinkedHashMap<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
//                        if (!resumes.containsKey(uuid)) {
                        Resume resume = new Resume(uuid, rs.getString("full_name"));
                        do {
                            String value = rs.getString("value");
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            System.out.println(value + " " + type.name());
                            resume.addContact(type, value);
                        } while (rs.next());
                        resumes.add(resume);
//                        }
                    }
                    return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.requstStatement("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
