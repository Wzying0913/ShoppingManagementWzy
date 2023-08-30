package org.example;
import java.sql.*;
import java.util.Scanner;

public class UserManagement {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private static final String DB_URL1 = "jdbc:sqlite:goods.db";
    public static void userAction(){//用户操作
        System.out.println("亲爱的用户，请选择登录（1），或注册（2）");
        Scanner reader=new Scanner(System.in);
        int chance= reader.nextInt();//选择
        switch (chance) {
            case 1: //登录
                MyLogin userLogin = new MyLogin();
                reader = new Scanner(System.in);
                String name;
                String password;
                boolean isContinue=true;
                while(isContinue){
                    System.out.print("请输入用户名：");
                    name = reader.next();
                    System.out.print("请输入密码：");
                    password = reader.next();
                    if(userLogin.login(name, password))
                        isContinue=false;
                }
                break;
            case 2: //注册
                MyRegister userRegister = new MyRegister();
                reader = new Scanner(System.in);
                String nameRegister = null, sex = null, passwordRegister = null;
                int identity = 2;
                boolean isContinue1 = true, isContinue2 = true, isContinue3 = true;
                while (isContinue1) {
                    System.out.println("请输入姓名！");
                    nameRegister = reader.next();
                    try {
                        userRegister.SetUserName(nameRegister);
                        isContinue1 = false;
                    } catch (NameException ex) {
                        System.out.println("出现异常！");
                    }
                }
                while (isContinue2) {
                    System.out.println("请输入性别！");
                    sex = reader.next();
                    try {
                        userRegister.setSex(sex);
                        isContinue2 = false;
                    } catch (SexException ex) {
                        System.out.printf("出现异常！");
                    }
                }
                userRegister.setIdentity(identity);
                while (isContinue3) {
                    System.out.printf("设置密码(密码10到15位，需要有数字、字母和特殊符号)：");
                    passwordRegister = reader.next();
                    try {
                        userRegister.setPassword(passwordRegister);
                        isContinue3 = false;
                    } catch (PasswordException ex) {
                        System.out.printf("出现异常！");
                    }
                }
                userRegister.registerUser(nameRegister, sex, identity, passwordRegister);
                System.out.println("用户注册成功！\n您的用户名为：" + nameRegister + "\n用户密码为：" + passwordRegister);
                System.out.println("请重新登录！");
                userLogin = new MyLogin();
                reader = new Scanner(System.in);
                isContinue=true;
                while(isContinue){
                    System.out.print("请输入用户名：");
                    name = reader.next();
                    System.out.print("请输入密码：");
                    password = reader.next();
                    if(userLogin.login(name, password))
                        isContinue=false;
                }
        }
        ShoppingCart.shoppingCart();
    }
    public static void passwordManageAction(String name){//密码管理
        try {
            Class.forName(CLASS_NAME);
            conn = DriverManager.getConnection(DB_URL);
            System.out.println(conn);
            Statement stmt = conn.createStatement();
            boolean isContiue=true;
            while(isContiue){
                System.out.println("选择需要对客户信息进行的操作：\n列出所有（1），修改自己密码（2），重置用户密码（3），返回主菜单（4）");
                Scanner reader=new Scanner(System.in);
                int chance= reader.nextInt();//选择
                switch(chance){
                    case 1:
                        query("1");// 查询所有插入商品信息
                        break;
                    case 2:
                        query("name='"+name+"'");
                        System.out.printf("输入旧密码:");
                        reader=new Scanner(System.in);
                        String str= "password='"+reader.next()+"'";
                        System.out.printf("输入新密码:");
                        String str2="password='"+reader.next()+"'";
                        update(str,str2);
                        System.out.printf("修改成功：");
                        query("name='"+name+"'");
                        break;
                    case 3:
                        System.out.println("以下是所有用户的信息（输入需要重置密码的用户名）：");
                        query("identity=2");
                        reader=new Scanner(System.in);
                        str= reader.next();
                        System.out.println("重置用户"+str+"密码：");
                        update("name='"+str+"'","password='**********'");
                        query("name='"+str+"'");
                        break;
                    case 4:
                        isContiue=false;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Connection conn;
    private static final String CLASS_NAME = "org.sqlite.JDBC";
    public static void delete(String condition) throws SQLException {
        if (conn != null) {
            String sql = "DELETE FROM USERS WHERE " + condition;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
    }
    public static void update(String condition,String value) throws SQLException {
        if (conn != null) {
            String sql = "UPDATE USERS SET " + value + " WHERE " + condition;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
    }
    public static void query(String condition) throws SQLException {
        if (conn != null) {
            String sql = "SELECT * FROM USERS WHERE " + condition;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.print("name：" + rs.getString(1) + " ");
                System.out.print("sex：" + rs.getString(2) + " ");
                System.out.print("identity：" + rs.getInt(3) + " ");
                System.out.print("password：" + rs.getString(4) + "\n");
            }
        }
    }
    public static void customManageAction(){//客户管理操作
        try {
            Class.forName(CLASS_NAME);
            conn = DriverManager.getConnection(DB_URL);
            System.out.println(conn);
            Statement stmt = conn.createStatement();
            boolean isContiue=true;
            while(isContiue){
                System.out.println("选择需要对客户信息进行的操作：\n列出所有（1），删除（2），查询（3），返回主菜单（4）");
                Scanner reader=new Scanner(System.in);
                int chance= reader.nextInt();//选择
                switch(chance){
                    case 1:
                        query("1");
                        break;
                    case 2:
                        System.out.printf("输入删除信息（例如：name='xxx'）:");
                        reader=new Scanner(System.in);
                        String str= reader.next();
                        delete(str);
                        System.out.println("删除后的用户信息：");
                        query("1");
                        break;
                    case 3:
                        System.out.printf("输入查询信息（例如：sex='女'）:");
                        reader=new Scanner(System.in);
                        str= reader.next();
                        query(str);
                        break;
                    case 4:
                        isContiue=false;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}