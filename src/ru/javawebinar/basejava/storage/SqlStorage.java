package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
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

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM contact WHERE resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }
            insertIntoTable(resume.getUuid(), "contact", getContacts(resume), conn);

            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM text WHERE resume_uuid=?")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }
            insertSections(resume, conn);

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

            insertIntoTable(resume.getUuid(), "contact", getContacts(resume), conn);

            insertSections(resume, conn);

            return null;
        });
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeTransaction(conn -> {
            Resume resume;
//            try (PreparedStatement ps = conn.prepareStatement(
//                    "SELECT * FROM resume r\n" +
//                            " LEFT JOIN contact c\n" +
//                            " ON r.uuid=c.resume_uuid\n" +
//                            " WHERE r.uuid=?")) {
//                ps.setString(1, uuid);
//                ResultSet rs = ps.executeQuery();
//                if (!rs.next()) {
//                    throw new NotExistStorageException(uuid);
//                }
//                resume = new Resume(uuid, rs.getString("full_name"));
//                if (rs.getString("resume_uuid") != null) {
//                    do {
//                        ContactType type = ContactType.valueOf(rs.getString("type"));
//                        String value = rs.getString("value");
//                        resume.addContact(type, value);
//                    } while (rs.next());
//                }
//            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume r\n" +
                            " WHERE r.uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM contact c\n" +
                            " WHERE c.resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
//                if (!rs.next()) {
//                    throw new NotExistStorageException(uuid);
//                }
//                resume = new Resume(uuid, rs.getString("full_name"));
                if ((rs.next()) & (rs.getString("resume_uuid") != null)) {
                    do {
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        String value = rs.getString("value");
                        resume.addContact(type, value);
                    } while (rs.next());
                }
            }


            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume r\n" +
                            " LEFT JOIN text t\n" +
                            " ON r.uuid=t.resume_uuid\n" +
                            " WHERE r.uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if ((rs.next()) & (rs.getString("resume_uuid") != null)) {
                    do {
                        SectionType type = SectionType.valueOf(rs.getString("type"));
                        String value = rs.getString("value");
                        resume.addSection(type, new TextSection(value));
                    } while (rs.next());
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume r\n" +
                            " LEFT JOIN list l\n" +
                            " ON r.uuid=l.resume_uuid\n" +
                            " WHERE r.uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if ((rs.next()) & (rs.getString("resume_uuid") != null)) {
                    do {
                        SectionType type = SectionType.valueOf(rs.getString("type"));
                        List<String> value = Arrays.asList(rs.getString("value").split("\n"));
                        resume.addSection(type, new ListSection(value));
                    } while (rs.next());
                }

                return resume;
            }
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
        return sqlHelper.executeTransaction(conn -> {
            Map<String, Resume> resumes = new HashMap<>();
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

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM text")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    SectionType type = SectionType.valueOf(rs.getString("type"));
                    String value = rs.getString("value");
                    resumes.get(uuid).addSection(type, new TextSection(value));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM list")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    SectionType type = SectionType.valueOf(rs.getString("type"));
                    List<String> value = Arrays.asList(rs.getString("value").split("\n"));
                    resumes.get(uuid).addSection(type, new ListSection(value));
                }
            }

            List<Resume> list = new ArrayList<>(resumes.values());
            Collections.sort(list);
            return list;
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeStatement("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        String uuid = resume.getUuid();
        Map<String, String> textSection = new HashMap<>();
        Map<String, String> listSection = new HashMap<>();
        String listSectionText;
        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            SectionType sectionType = entry.getKey();
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    textSection.put(sectionType.name(), ((TextSection) entry.getValue()).getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    listSectionText = String.join("\n", ((ListSection) entry.getValue()).getItems());
                    listSection.put(sectionType.name(), listSectionText);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    break;
                default:
                    break;
            }
        }

        insertIntoTable(uuid, "text", textSection, conn);
        insertIntoTable(uuid, "list", listSection, conn);
    }

    private void insertIntoTable(String uuid, String table, Map<String, String> section, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                new StringBuilder().append("INSERT INTO ").append(table).append(" (resume_uuid, type, value) VALUES (?,?,?)").toString())) {
            for (Map.Entry<String, String> entry : section.entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, entry.getKey());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private Map<String, String> getContacts(Resume resume) {
        Map<String, String> contacts = new HashMap<>();
        for (Map.Entry<ContactType, String> contact : resume.getContact().entrySet()) {
            contacts.put(contact.getKey().name(), contact.getValue());
        }
        return contacts;
    }
}
