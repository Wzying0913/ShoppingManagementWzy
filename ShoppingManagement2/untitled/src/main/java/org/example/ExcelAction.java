package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelAction {
    public static ArrayList<User> readUsersFromExcel(String filePath) {
        ArrayList<User> userList = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String name = row.getCell(0).getStringCellValue();
                String sex = row.getCell(1).getStringCellValue();
                String identity = row.getCell(2).getStringCellValue();
                String password = row.getCell(3).getStringCellValue();
                User user = new User(name, sex, identity, password);
                userList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public static void writeUsersToExcel(ArrayList<User> userList, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");
            int rowNum = 0;
            for (User user : userList) {
                Row row = sheet.createRow(rowNum++);

                Cell name = row.createCell(0);
                name.setCellValue(user.getUsername());
                Cell sex = row.createCell(1);
                sex.setCellValue(user.getSex());
                Cell identity = row.createCell(2);
                identity.setCellValue(user.getIdentity());
                Cell password = row.createCell(3);
                password.setCellValue(user.getPassword());
            }
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Goods> readGoodsFromExcel(String filePath) {
        ArrayList<Goods> goodsList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                int num = (int) row.getCell(0).getNumericCellValue();
                String goodsName = row.getCell(1).getStringCellValue();
                int salesVolume = (int) row.getCell(2).getNumericCellValue();
                double price = row.getCell(3).getNumericCellValue();
                Goods goods = new Goods(num, goodsName, salesVolume, price);
                goodsList.add(goods);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    public static void writeGoodsToExcel(ArrayList<Goods> goodsList, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Goods");
            int rowNum = 0;
            for (Goods goods : goodsList) {
                Row row = sheet.createRow(rowNum++);

                Cell num = row.createCell(0);
                num.setCellValue(goods.getNum());
                Cell goodsName = row.createCell(1);
                goodsName.setCellValue(goods.getGoodsName());
                Cell salesVolume = row.createCell(2);
                salesVolume.setCellValue(goods.getSalesVolume());
                Cell price = row.createCell(3);
                price.setCellValue(goods.getPrice());
            }
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}