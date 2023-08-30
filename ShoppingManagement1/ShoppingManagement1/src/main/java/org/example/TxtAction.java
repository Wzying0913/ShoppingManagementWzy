package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TxtAction {
    public static void writeUserToFile(ArrayList<User> userList, String filePath) {// 将User对象写入文本文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false))) {//将先前文本置空之后写入新的内容，防止重复
            writer.write("----------------用户系统信息------------------\n");
            writer.write("用户名,"+"性别,"+"身份,"+"密码\n");
            for(User user : userList) {
                writer.write(user.getUsername() + "," + user.getSex() + "," + user.getIdentity() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> readUserFromFile(String filePath) {// 从文本文件中读取User对象
        UserManagement userManagement=new UserManagement();
        userManagement.userList=new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                if (fields.length == 4) {
                    String username = fields[0];
                    String sex = fields[1];
                    String identity = fields[2];
                    String password = fields[3];
                    User user = new User(username, sex, identity, password);
                    userManagement.addUser(user);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userManagement.userList;
    }
    public static void writeGoodsToFile(ArrayList<Goods> List, String filePath ,boolean isContinue) {// 写入文本文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,isContinue))) {
            writer.write("\n----------------商品信息------------------\n");
            for(Goods goods: List) {
                writer.write(goods.getNum() + "," + goods.getGoodsName()+ "," + goods.getSalesVolume()+ "," + goods.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Goods> readGoodsFromFile(String filePath) {// 从文本文件中读取
        GoodsManagement goodsManagement=new GoodsManagement();
        goodsManagement.goodsList=new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                if (fields.length == 4) {
                    int num = Integer.parseInt(fields[0]);
                    String goodsName = fields[1];
                    int salesVolume = Integer.parseInt(fields[2]);
                    double price = Double.parseDouble(fields[3]);
                    Goods goods = new Goods(num, goodsName, salesVolume, price);
                    goodsManagement.goodsList.add(goods);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsManagement.goodsList;
    }
}
