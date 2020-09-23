package ru.javawebinar.basejava.storage;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    protected interface FunctionWithSQLException<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    protected <T> T requstStatement(String request, FunctionWithSQLException<T> function) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(request)) {
            return function.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("PSQLException " + e.getSQLState(), null);
            }
            throw new StorageException("PSQLException", e);
        }
    }
}
