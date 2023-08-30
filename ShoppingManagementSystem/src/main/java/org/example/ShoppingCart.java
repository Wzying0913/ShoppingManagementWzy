package org.example;

import java.sql.*;
import java.util.Scanner;

public class ShoppingCart {
    private static final String CLASS_NAME = "org.sqlite.JDBC";
    private static final String DB_URL2 = "jdbc:sqlite:cart.db";
    private static Connection conn;
    public static void initializeShoppingCart() {
        try (Connection connection = DriverManager.getConnection(DB_URL2);
             Statement statement = connection.createStatement()) {
            String emptyTable="DROP TABLE CART";
            statement.executeUpdate(emptyTable);
            String createTableQuery = "CREATE TABLE IF NOT EXISTS CART(num int not null,"+
                    "goodsName varchar(20) not null,number int not null,price double not null)";
            statement.executeUpdate(createTableQuery);
            System.out.println("数据库CART初始化成功!");
        } catch (SQLException e) {
            System.out.println("数据库CART初始化失败: " + e.getMessage());
        }
    }
    public static void insert(int num,String goodsName,int number,double price) throws SQLException {
        if (conn != null) {
            String sql = "INSERT INTO CART VALUES(?,?,?,?)";
            PreparedStatement newGoods = conn.prepareStatement(sql);
            newGoods.setInt(1,num);
            newGoods.setString(2,goodsName);
            newGoods.setInt(3,number);
            newGoods.setDouble(4,price);
            newGoods.executeUpdate();
        }
    }

    public static void delete(String condition) throws SQLException {
        if (conn != null) {
            String sql = "DELETE FROM CART WHERE " + condition;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
    }

    public static void update(String condition,String value) throws SQLException {
        if (conn != null) {
            String sql = "UPDATE CART SET " + value + " WHERE " + condition;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
    }

    public static void query(String condition) throws SQLException {
        if (conn != null) {
            String sql = "SELECT * FROM CART WHERE " + condition;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.print("序号：" + rs.getInt(1) + " ");
                System.out.print("商品名称：" + rs.getString(2) + " ");
                System.out.print("数量：" + rs.getInt(3) + " ");
                System.out.print("单价：" + rs.getDouble(4) + "\n");
            }
        }
    }
    public static void shoppingCart(){//购物车
        try {
            Class.forName(CLASS_NAME);
            initializeShoppingCart();
            conn = DriverManager.getConnection(DB_URL2);
            System.out.println(conn);
            Statement stmt = conn.createStatement();
            insert(1,"手机壳",1,20);
            insert(2,"长袜",1,10);
            insert(3,"电动车",1,3200);
            insert(4,"水杯",1,60);
            insert(5,"书包",1,89);
            insert(6,"玲娜贝儿",1,269);
            insert(7,"卡套",1,5.9);
            insert(8,"防晒衣",1,159);
            insert(9,"窗帘",1,250);
            insert(10,"口罩",1,55);
            insert(11,"面包",1,65);

            boolean isContiue=true;
            while(isContiue){
                System.out.println("亲爱的用户，请选择需要对购物车进行的操作：");
                System.out.println("列出所有（1），增加（2），减少（3），删除商品（4），计算总价（5），清空购物车（6），返回主菜单（7）");
                Scanner reader=new Scanner(System.in);
                int chance= reader.nextInt();//选择
                switch(chance){
                    case 1:
                        query("1");// 查询所有插入商品信息
                        break;
                    case 2:
                        System.out.printf("请输入需要增加的商品序号(每次添加一件):");
                        reader=new Scanner(System.in);
                        int num= reader.nextInt();
                        update("num="+num,"number=number+1");
                        System.out.println("添加后该商品的信息：");
                        query("num="+num);
                        break;
                    case 3:
                        System.out.printf("请输入需要减少的商品序号(每次减少一件):");
                        reader=new Scanner(System.in);
                        num= reader.nextInt();
                        update("num="+num,"number=number-1");
                        System.out.println("减少后该商品的信息：");
                        query("num="+num);
                        break;
                    case 4:
                        System.out.printf("请输入需要删除的商品序号(直接删除这一商品):");
                        reader=new Scanner(System.in);
                        num= reader.nextInt();
                        delete("num="+num);
                        System.out.println("删除后的购物车：");
                        query("1");
                        break;
                    case 5:
                        String prices="SELECT * FROM CART WHERE "+"1";;
                        stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(prices);
                        double sum=0.0;
                        while (rs.next())
                            sum+=rs.getInt(3)* rs.getDouble(4);
                        System.out.printf("购物车总价格为:"+sum+"元\n");
                        break;
                    case 6:
                        System.out.printf("购物车已清空");
                        delete("1");
                        break;
                    case 7:
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
