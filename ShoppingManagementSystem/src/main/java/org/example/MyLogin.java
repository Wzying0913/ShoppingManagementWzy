package org.example;
import java.sql.*;
public class MyLogin {//登录
    private static final String DB_URL = "jdbc:sqlite:users.db";
    public boolean login(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE name = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if ((MD5encipher.getMD5Str(password)).equals(storedPassword)) {
                    System.out.println("登陆成功!");
                    return true;
                }
                else
                    System.out.println("密码错误");
            }
            else
                System.out.println("用户名不存在");
        } catch (SQLException e) {
            System.out.println("登陆失败: " + e.getMessage());
        }
        return false;
    }
}
