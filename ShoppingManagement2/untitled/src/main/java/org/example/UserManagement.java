package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class UserManagement {
    public static ArrayList<User> userList;
    public static void userSelect(String name) {//用户选择：密码管理or购物
        boolean isContinue1=true;
        while(isContinue1){
            System.out.println("亲爱的用户，请选择密码管理（1），或购物（2），或退出登录（3）");
            Scanner reader=new Scanner(System.in);
            int chance= reader.nextInt();
            boolean isContinue2=true;
            while(isContinue2){
                switch (chance){
                    case 1:
                        System.out.println("亲爱的用户，请选择修改密码（1），或重置密码（2），或返回用户操作菜单（3）");
                        int chancePassword= reader.nextInt();
                        switch(chancePassword) {
                            case 1:
                                queryUser(name);
                                boolean isContinue3=true;
                                while (isContinue3) {
                                    System.out.printf("输入新密码(密码10到15位，需要有数字、字母和特殊符号:");
                                    String str= reader.next();
                                    try {
                                        if(MyRegister.setPassword(str)){
                                            User newUser = new User(name,queryUser(name).getSex(),queryUser(name).getIdentity(),MD5encipher.getMD5Str(str));
                                            updateUser(newUser);
                                            isContinue3 = false;
                                        }
                                    } catch (PasswordException ex) {
                                        System.out.printf("出现异常！");
                                    }
                                }
                                System.out.printf("修改成功：");
                                User foundUser= queryUser(name);
                                if (foundUser != null) {
                                    System.out.println(foundUser);
                                } else {
                                    System.out.println("用户不存在！");
                                }
                                break;
                            case 2:
                                User newUser = new User(name,queryUser(name).getSex(),queryUser(name).getIdentity(),MD5encipher.getMD5Str("**********"));
                                updateUser(newUser);
                                foundUser= queryUser(name);
                                if (foundUser != null) {
                                    System.out.println(foundUser);
                                } else {
                                    System.out.println("用户不存在！");
                                }
                                break;
                            case 3:
                                isContinue2=false;
                                break;
                            default:
                                System.out.println("输入选项为1、2、3，请重新输入！");
                        }
                        break;
                    case 2:
                        ShoppingCart.shoppingCart();
                        isContinue2=false;
                        break;
                    case 3:
                        isContinue2=false;
                        isContinue1=false;
                        break;
                    default:
                        System.out.println("输入选项为1、2、3，请重新输入！");
                }
            }
        }
    }
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
                    if(userLogin.login(name, password)){
                        isContinue=false;
                        userSelect(name);
                    }
                }
                break;
            case 2: //注册
                MyRegister userRegister = new MyRegister();
                reader = new Scanner(System.in);
                String nameRegister = null, sex = null, passwordRegister = null;
                String identity = "user";
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
                User user=new User(nameRegister, sex, identity, MD5encipher.getMD5Str(passwordRegister));
                UserManagement.addUser(user);
                System.out.println("用户注册成功！\n您的用户名为：" + nameRegister + "\n用户密码为：" + MD5encipher.getMD5Str(passwordRegister));
                System.out.println("请重新登录！");
                userLogin = new MyLogin();
                reader = new Scanner(System.in);
                isContinue=true;
                while(isContinue){
                    System.out.print("请输入用户名：");
                    name = reader.next();
                    System.out.print("请输入密码：");
                    password = reader.next();
                    if(userLogin.login(name, password)){
                        isContinue=false;
                        userSelect(nameRegister);
                    }
                }
                break;
            default:
                System.out.println("输入选项为1、2，请重新输入！");
        }
    }
    public static void passwordManageAction(String name){//密码管理
        boolean isContiue=true;
        while(isContiue){
            System.out.println("选择需要对客户信息进行的操作：\n列出所有（1），修改自己密码（2），重置用户密码（3），返回主菜单（4）");
            Scanner reader=new Scanner(System.in);
            int chance= reader.nextInt();//选择
            switch(chance){
                case 1:
                    printUser();
                    break;
                case 2:
                    queryUser(name);
                    boolean isContinue=true;
                    while (isContinue) {
                        System.out.printf("输入新密码(密码10到15位，需要有数字、字母和特殊符号:");
                        String str= reader.next();
                        try {
                            if(MyRegister.setPassword(str)){
                                User newUser = new User(name,queryUser(name).getSex(),queryUser(name).getIdentity(),MD5encipher.getMD5Str(str));
                                updateUser(newUser);
                                isContinue = false;
                            }
                        } catch (PasswordException ex) {
                            System.out.printf("出现异常！");
                        }
                    }
                    System.out.printf("修改成功：");
                    User foundUser= queryUser(name);
                    if (foundUser != null) {
                        System.out.println(foundUser);
                    } else {
                        System.out.println("用户不存在！");
                    }
                    break;
                case 3:
                    System.out.println("以下是所有用户的信息（输入需要重置密码的用户名）：");
                    printUser();
                    reader=new Scanner(System.in);
                    String str= reader.next();
                    System.out.println("重置用户"+str+"密码：");
                    User newUser = new User(str,queryUser(str).getSex(),queryUser(str).getIdentity(),MD5encipher.getMD5Str("**********"));
                    updateUser(newUser);
                    foundUser= queryUser(str);
                    if (foundUser != null) {
                        System.out.println(foundUser);
                    } else {
                        System.out.println("用户不存在！");
                    }
                    break;
                case 4:
                    isContiue=false;
                    break;
                default:
                    System.out.println("输入选项为1-4，请重新输入！");
            }
        }
    }
    public static void initializeUser() {
        userList = new ArrayList<>();
        userList=ExcelAction.readUsersFromExcel("user.xlsx");
        if(userList==null || userList.isEmpty()){//如果该文件读出为空，将预设的信息导入userList和文件
            MyRegister administratorInsert=new MyRegister();
            userList = administratorInsert.insertInformation(); // 获取导入的用户列表
            ExcelAction.writeUsersToExcel(userList, "user.xlsx");
        }
    }
    public static void addUser(User user) {
        userList.add(user);
    }
    public static void deleteUser(User user) {
        userList.remove(user);
    }
    public static void updateUser(User user) {
        int index = findUserIndex(user.getUsername());
        if (index != -1)
            userList.set(index, user);
        else
            System.out.println("用户不存在");
    }
    public static User queryUser(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // 用户不存在
    }
    public static void printUser(){
        System.out.println("----------------用户系统信息------------------");
        System.out.println("用户名\t\t"+"性别\t\t"+"身份\t\t"+"密码");
        for(User user : userList){
            System.out.println(user.getUsername()+"\t\t"+user.getSex()+"\t\t"+user.getIdentity()+"\t\t"+user.getPassword());
        }
    }
    private static int findUserIndex(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1; // 用户不存在
    }
    public void customManageAction(){//客户管理操作
        boolean isContiue=true;
        while(isContiue){
            System.out.println("选择需要对客户信息进行的操作：\n列出所有（1），删除（2），查询（3），返回主菜单（4）");
            Scanner reader=new Scanner(System.in);
            int chance= reader.nextInt();//选择
            switch(chance){
                case 1:
                    printUser();
                    break;
                case 2:
                    System.out.printf("输入需要删除的用户名:");
                    reader=new Scanner(System.in);
                    String str= reader.next();
                    User foundUser = queryUser(str);
                    deleteUser(foundUser);
                    System.out.println("删除后的用户信息：");
                    printUser();
                    break;
                case 3:
                    System.out.printf("输入查询的用户名:");
                    reader=new Scanner(System.in);
                    str= reader.next();
                    foundUser = queryUser(str);
                    if (foundUser != null) {
                        System.out.println("找到用户：" + foundUser);
                    } else {
                        System.out.println("用户不存在！");
                    }
                    break;
                case 4:
                    isContiue=false;
                    break;
                default:
                    System.out.println("输入选项为1-4，请重新输入！");
            }
        }
    }
}