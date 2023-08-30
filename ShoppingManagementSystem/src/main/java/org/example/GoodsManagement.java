package org.example;
import java.sql.*;
import java.util.Scanner;

public class GoodsManagement {
    private static final String CLASS_NAME = "org.sqlite.JDBC";
    private static final String DB_URL1 = "jdbc:sqlite:goods.db";

    public static void initializeGoods() {
        try (Connection connection = DriverManager.getConnection(DB_URL1);
             Statement statement = connection.createStatement()) {
            String emptyTable="DROP TABLE GOODS";
            statement.executeUpdate(emptyTable);
            String createTableQuery = "CREATE TABLE IF NOT EXISTS GOODS(num int not null,"+
                    "goodsName varchar(20) not null,salesVolume int not null,price double not null)";
            statement.executeUpdate(createTableQuery);
            System.out.println("数据库GOODS初始化成功!");
        } catch (SQLException e) {
            System.out.println("数据库GOODS初始化失败: " + e.getMessage());
        }
    }
    private static Connection conn;
    public static void insert(int num,String goodsName,int salesVolume,double price) throws SQLException {
        if (conn != null) {
            String sql = "INSERT INTO GOODS VALUES(?,?,?,?)";
            PreparedStatement newGoods = conn.prepareStatement(sql);
            newGoods.setInt(1,num);
            newGoods.setString(2,goodsName);
            newGoods.setInt(3,salesVolume);
            newGoods.setDouble(4,price);
            newGoods.executeUpdate();
        }
    }

    public static void delete(String condition) throws SQLException {
        if (conn != null) {
            String sql = "DELETE FROM GOODS WHERE " + condition;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
    }

    public static void update(String condition,String value) throws SQLException {
        if (conn != null) {
            String sql = "UPDATE GOODS SET " + value + " WHERE " + condition;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
    }

    public static void query(String condition) throws SQLException {
        if (conn != null) {
            String sql = "SELECT * FROM GOODS WHERE " + condition;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.print("序号：" + rs.getInt(1) + " ");
                System.out.print("商品名称：" + rs.getString(2) + " ");
                System.out.print("销量：" + rs.getInt(3) + " ");
                System.out.print("单价：" + rs.getDouble(4) + "\n");
            }
        }
    }


    public static void goodsAction() {
        try {
            Class.forName(CLASS_NAME);
            initializeGoods();
            conn = DriverManager.getConnection(DB_URL1);
            System.out.println(conn);
            Statement stmt = conn.createStatement();
            insert(1,"手机壳",121,20);
            insert(2,"长袜",80,10);
            insert(3,"电动车",1,3200);
            insert(4,"水杯",2,60);
            insert(5,"书包",22,89);
            insert(6,"玲娜贝儿",33,269);
            insert(7,"卡套",16,5.9);
            insert(8,"防晒衣",71,159);
            insert(9,"窗帘",12,250);
            insert(10,"口罩",21,55);
            insert(11,"面包",82,65);
            int count=12;
            boolean isContiue=true;
            while(isContiue){
                System.out.println("选择需要对商品信息进行的操作：\n列出所有（1），增加（2），删除（3），修改（4），查询（5），返回主菜单（6）");
                Scanner reader=new Scanner(System.in);
                int chance= reader.nextInt();//选择
                switch(chance){
                    case 1:
                        query("1");// 查询所有插入商品信息
                        break;
                    case 2:
                        System.out.printf("依次输入需要增加的商品名称、商品销量、商品单价：");
                        reader=new Scanner(System.in);
                        String name= reader.next();
                        int salesVolume=reader.nextInt();
                        double price= reader.nextDouble();
                        insert((count++),name,salesVolume,price);
                        System.out.println("添加后的商品系统：");
                        query("1");
                        break;
                    case 3:
                        System.out.printf("输入删除信息（例如：price>200）:");
                        reader=new Scanner(System.in);
                        String str= reader.next();
                        delete(str);
                        System.out.println("删除后的商品系统：");
                        query("1");
                        break;
                    case 4:
                        System.out.printf("输入修改信息（例如：goodsName='卡套' price='9.9'）:");
                        reader=new Scanner(System.in);
                        str= reader.next();
                        String str2= reader.next();
                        update(str,str2);
                        System.out.println("修改后的商品系统：");
                        query("1");
                        break;
                    case 5:
                        System.out.printf("输入查询信息（例如：goodsName='卡套'或num=3）:");
                        reader=new Scanner(System.in);
                        str= reader.next();
                        query(str);
                        break;
                    case 6:
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
