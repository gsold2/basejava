package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {

    public SqlHelper() {
    }
//
//    protected interface ABlockOfCode<T> {
//        T execute();
//    }
//
//    public <T> void tryAndCatcher(ConnectionFactory connectionFactory, ABlockOfCode<T> ablockOfCode) {
//        try (Connection conn = connectionFactory.getConnection()) {
//            ablockOfCode.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
}
