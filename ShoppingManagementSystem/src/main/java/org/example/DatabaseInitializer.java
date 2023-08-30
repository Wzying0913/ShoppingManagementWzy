package org.example;
import java.sql.*;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            String emptyTable="DROP TABLE USERS";
            statement.executeUpdate(emptyTable);
            String createTableQuery = "CREATE TABLE IF NOT EXISTS USERS (name varchar(20) not null, sex varchar(2) not null, " +
                    "identity int not null, password varchar(20) not null)";
            statement.executeUpdate(createTableQuery);
            System.out.println("数据库user初始化成功!");
        } catch (SQLException e) {
            System.out.println("数据库user初始化失败: " + e.getMessage());
        }
    }
}