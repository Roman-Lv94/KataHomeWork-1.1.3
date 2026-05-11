package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "4651";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Соединение с БД успешно.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Ошибка: не удалось установить соединение с БД", e);
        }
        return connection;
    }
}