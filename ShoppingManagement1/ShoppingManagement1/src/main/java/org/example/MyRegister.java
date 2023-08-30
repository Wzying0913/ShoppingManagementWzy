package org.example;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegister {//用户注册
    public ArrayList<User> insertInformation(){//插入信息
        User user1 = new User("Anna","女", "administer", MD5encipher.getMD5Str("anna123456*"));UserManagement.addUser(user1);
        User user2 = new User("Jim", "男", "administer", MD5encipher.getMD5Str("Jim123456*@"));UserManagement.addUser(user2);
        User user3 = new User("李四","男","administer",MD5encipher.getMD5Str("LiSi123456??"));UserManagement.addUser(user3);
        User user4 = new User("张三","男","user",MD5encipher.getMD5Str("zhang123456??"));UserManagement.addUser(user4);
        User user5 = new User("王五","女","user",MD5encipher.getMD5Str("wang123456??"));UserManagement.addUser(user5);
        return UserManagement.userList;
    }
    private String sex;//性别
    public void setSex(String sex)throws SexException{
        if("男".equals(sex)||"女".equals(sex))
            this.sex=sex;
        else
            throw new SexException();
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