package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public interface FunctionWithSQLException<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public <T> T requstStatement(String request, FunctionWithSQLException<T> function) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(request)) {
            return function.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
