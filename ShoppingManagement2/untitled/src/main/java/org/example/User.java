package org.example;

public class User {
    private String username;
    private String sex;
    private String identity;
    private String password;

    public User(String username, String sex, String identity, String password) {
        this.username = username;
        this.sex = sex;
        this.identity=identity;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getSex() {
        return sex;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPassword() {
        return password;
    }

    @Override    public String toString() {
        return "User{" +
                "用户名='" + username + '\'' +
                ", 性别='" + sex + '\'' +
                ", 身份='" + identity + '\'' +
                ", 密码='" + password + '\'' +
                '}';
    }
}