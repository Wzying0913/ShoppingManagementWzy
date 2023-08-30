package org.example;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

public class MyRegister {//用户注册
    private static final String DB_URL = "jdbc:sqlite:users.db";
    public boolean registerUser(String name, String sex,int identity_,String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS (name,sex,identity,password) VALUES (?,?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, sex);
            statement.setInt(3, identity_);
            statement.setString(4, MD5encipher.getMD5Str(password));
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("用户注册失败: " + e.getMessage());
        }
        return false;
    }
    public void insertInformation(){//插入信息
        registerUser("Anna","女",1,"anna123456*");
        registerUser("Jim","男",1,"Jim123456*@");
        registerUser("李四","男",1,"LiSi123456??");
        registerUser("张三","男",2,"zhang123456??");
        registerUser("王五","女",2,"wang123456??");
    }
    private String sex;//性别
    private String identity_;//身份
    public void setSex(String sex)throws SexException{
        if("男".equals(sex)||"女".equals(sex))
            this.sex=sex;
        else
            throw new SexException();
    }
    public void setIdentity(int identity){//身份：1，管理员；2，用户
        if("1".equals(identity))
            this.identity_="管理员";
        else if ("2".equals(identity))
            this.identity_="用户";
    }
    public void SetUserName(String userName) throws NameException{//写入用户名，用户名必须为字符
        for(int i=0;i<userName.length();i++){
            char current=userName.charAt(i);
            if((current>=65&&current<=90)||(current>=97&&current<=122)){//姓名应该为字符
            }
            else{
                throw new NameException();
            }
        }
    }
    public boolean setPassword(String str) throws PasswordException{//用户密码校验
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        boolean isSpecial = false;//定义一个boolean值，用来表示是否包含特殊字符
        for (int i = 0; i < str.length(); i++) {
            Pattern p = Pattern.compile("[~`#$%^&*!@.,()\\\\{}|:;?<>]");//判断是否含有特殊字符
            Matcher m = p.matcher(str);
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }else if (m.find()) {//判断是否包含特殊字符
                isSpecial = true;
            }
        }
        String regex = "^([a-zA-Z0-9~`#$%^&*!@.,()\\\\{}|:;?<>]){10,15}$";//密码的正则规则 10到15位  包含数字 字母 特殊字符!@.,()
        boolean isRight = isDigit && isLetter && isSpecial && str.matches(regex);
        if(isRight)
            return isRight;//密码校验成功
        else
            throw new PasswordException();
    }
}
class NameException extends Exception{
    NameException(){
        System.out.println("姓名必须是字符！");
    }
}
class SexException extends Exception{
    SexException(){
        System.out.println("性别必须是\"男\"或\"女\"!");
    }
}
class PasswordException extends Exception{
    PasswordException(){
        System.out.println("密码码10到15位，需要有数字、字母和特殊符号!");
    }
}