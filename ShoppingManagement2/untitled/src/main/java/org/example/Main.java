package org.example;

import java.util.Scanner;
import static java.lang.System.exit;
import static org.example.UserManagement.userList;

public class Main {

    public static void main(String[] args){
        System.out.println("-------------------购物管理系统-------------------");
        UserManagement.initializeUser();
        while(true){
            System.out.println("请输入您的身份：管理员（1），用户（2）,退出系统（3）");
            Scanner reader=new Scanner(System.in);
            int chance= reader.nextInt();//选择
            switch(chance){
                case 1:
                    System.out.println("尊敬的管理员，请登录！");
                    MyLogin administrator=new MyLogin();
                    reader = new Scanner(System.in);
                    String name=null;
                    String password=null;
                    boolean isContinue=true;
                    while(isContinue){
                        System.out.print("请输入用户名：");
                        name = reader.next();
                        System.out.print("请输入密码：");
                        password = reader.next();
                        if(administrator.login(name, password))
                            isContinue=false;
                    }
                    boolean isExit=true;
                    while(isExit){
                        System.out.println("客户管理（1），商品管理（2），密码管理（3）,退出登录（4）");
                        UserManagement userManagement=new UserManagement();
                        reader=new Scanner(System.in);
                        chance= reader.nextInt();//选择
                        switch (chance){
                            case 1:
                                userManagement.customManageAction();
                                break;
                            case 2:
                                GoodsManagement goodsManagement=new GoodsManagement();
                                goodsManagement.goodsAction();
                                break;
                            case 3:
                                userManagement.passwordManageAction(name);
                                break;
                            case 4:
                                System.out.println("退出登录！");
                                isExit=false;
                        }
                    }
                    break;
                case 2:
                    UserManagement userManagement=new UserManagement();
                    userManagement.userAction();
                    break;
                case 3:
                    ExcelAction.writeUsersToExcel(userList, "user.xlsx");
                    System.out.println("退出系统！");
                    exit(0);
                default:
                    System.out.println("输入选项为1、2、3，请重新输入！");
            }
        }
    }
}