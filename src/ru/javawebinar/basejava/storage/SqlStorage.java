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
            String uuid = resume.getUuid();
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }

            deleteFromTable(uuid, "contact", conn);
            insertIntoTable(uuid, "contact", getContacts(resume), conn);

            deleteFromTable(uuid, "text", conn);
            deleteFromTable(uuid, "list", conn);
            insertSections(resume, conn);

            return null;
        });
    }


    @Override
    public void save(Resume resume) {
        sqlHelper.executeTransaction(conn -> {
            String uuid = resume.getUuid();
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name)\n" +
                            " VALUES (?,?)\n" +
                            " ON CONFLICT DO NOTHING")) {
                ps.setString(1, uuid);
                ps.setString(2, resume.getFullName());
                if (ps.executeUpdate() == 0) {
                    throw new ExistStorageException(uuid);
                }
            }

            insertIntoTable(uuid, "contact", getContacts(resume), conn);

            insertSections(resume, conn);

            return null;
        });
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeTransaction(conn -> {
            Resume resume;

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM resume r\n" +
                            " LEFT JOIN contact c\n" +
                            " ON r.uuid=c.resume_uuid\n" +
                            " WHERE r.uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                if (rs.getString("resume_uuid") != null) {
                    do {
                        ContactType contactType = ContactType.valueOf(rs.getString("type"));
                        String value = rs.getString("value");
                        resume.addContact(contactType, value);
                    } while (rs.next());
                }
            }

            selectSection(uuid, resume, "text", conn);
            selectSection(uuid, resume, "list", conn);

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
        return sqlHelper.executeTransaction(conn -> {
            Map<String, Resume> resumes = new HashMap<>();

            selectResumesValues(resumes, "resume", conn, this::readUuids);
            selectResumesValues(resumes, "contact", conn, this::readContacts);
            selectResumesValues(resumes, "text", conn, this::readSections);
            selectResumesValues(resumes, "list", conn, this::readSections);

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

    private void deleteFromTable(String uuid, String table, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                new StringBuilder().append("DELETE FROM ").append(table).append(" WHERE resume_uuid=?").toString())) {
            ps.setString(1, uuid);
            ps.executeUpdate();
        }
    }

    private void insertIntoTable(String uuid, String table, Map<String, String> section, Connection conn)
            throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                new StringBuilder().append("INSERT INTO ").append(table).append(" (resume_uuid, type, value)" +
                        " VALUES (?,?,?)").toString())) {
            for (Map.Entry<String, String> entry : section.entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, entry.getKey());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        String uuid = resume.getUuid();
        Map<String, String> textSection = new HashMap<>();
        Map<String, String> listSection = new HashMap<>();

        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            SectionType sectionType = entry.getKey();
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    textSection.put(sectionType.name(), ((TextSection) entry.getValue()).getContent());
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    listSection.put(sectionType.name(),
                            String.join("\n", ((ListSection) entry.getValue()).getItems()));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    break;
            }
        }

        insertIntoTable(uuid, "text", textSection, conn);
        insertIntoTable(uuid, "list", listSection, conn);
    }

    private void selectSection(String uuid, Resume resume, String table, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                new StringBuilder().append("SELECT * FROM ").append(table).append(" t WHERE t.resume_uuid=?").toString())) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                readSections(resume, rs);
            }
        }
    }

    @FunctionalInterface
    protected interface readValues {
        void read(Map<String, Resume> resumes, ResultSet rs) throws SQLException;
    }

    private void selectResumesValues(Map<String, Resume> resumes, String table, Connection conn, readValues reader) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                new StringBuilder().append("SELECT * FROM ").append(table).toString())) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reader.read(resumes, rs);
            }
        }
    }

    private void readUuids(Map<String, Resume> resumes, ResultSet rs) throws SQLException {
        String uuid = rs.getString("uuid");
        Resume resume = new Resume(uuid, rs.getString("full_name"));
        resumes.put(uuid, resume);
    }

    private void readContacts(Map<String, Resume> resumes, ResultSet rs) throws SQLException {
        String uuid = rs.getString("resume_uuid");
        ContactType contactType = ContactType.valueOf(rs.getString("type"));
        resumes.get(uuid).addContact(contactType, rs.getString("value"));
    }

    @SuppressWarnings("unchecked")
    private <T> void readSections(T t, ResultSet rs) throws SQLException {
        String uuid = rs.getString("resume_uuid");
        SectionType sectionType = SectionType.valueOf(rs.getString("type"));
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                if (t instanceof Resume) {
                    ((Resume) t).addSection(sectionType, new TextSection(rs.getString("value")));
                } else {
                    ((Resume) ((Map<String, Object>) t).get(uuid)).addSection(sectionType, new TextSection(rs.getString("value")));
                }
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                if (t instanceof Resume) {
                    ((Resume) t).addSection(sectionType,
                            new ListSection(Arrays.asList(rs.getString("value").split("\n"))));
                } else {
                    ((Resume) ((Map<String, Object>) t).get(uuid)).addSection(sectionType,
                            new ListSection(Arrays.asList(rs.getString("value").split("\n"))));
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                break;
        }
    }

    private Map<String, String> getContacts(Resume resume) {
        Map<String, String> contacts = new HashMap<>();
        for (Map.Entry<ContactType, String> contactType : resume.getContact().entrySet()) {
            contacts.put(contactType.getKey().name(), contactType.getValue());
        }
        return contacts;
    }
}
