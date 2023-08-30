package org.example;
public class MyLogin {//登录
    public boolean login(String username, String password) {
        User user=UserManagement.queryUser(username);
        if (user!=null) {
            String storedPassword = user.getPassword();
            if ((MD5encipher.getMD5Str(password)).equals(storedPassword)) {
                System.out.println("登陆成功!");
                return true;
            }
            else
                System.out.println("密码错误");
        }
        else
            System.out.println("用户名不存在");
        return false;
    }
}