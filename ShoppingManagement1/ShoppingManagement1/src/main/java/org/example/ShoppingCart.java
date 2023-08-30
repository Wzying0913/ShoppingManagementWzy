package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ShoppingCart {
    public static ArrayList<Goods> cartList;
    public static ArrayList<Goods> shoppingList;//购物历史信息
    public static void initializeShoppingCart() {
        cartList=new ArrayList<>();
        shoppingList=new ArrayList<>();
        cartList=TxtAction.readGoodsFromFile("cart.txt");//用户文件不空则直接读出
        if(cartList==null || cartList.isEmpty()){//如果该文件读出为空，将预设的信息导入cartList和文件
            GoodsManagement.initializeGoods();
            cartList=new ArrayList<>(GoodsManagement.goodsList);
            for(Goods goods:cartList){//购物车与商品系统不同的是，每个用户看到的不是销售量而是自己的选择数量，选择数量初始化为1
                goods.SetSalesVolume(1);
            }
            TxtAction.writeGoodsToFile(cartList, "cart.txt",false);
        }
    }
    public static void delete(int num) {
        Iterator<Goods> iterator = cartList.iterator();
        while (iterator.hasNext()) {
            Goods goods = iterator.next();
            if (goods.getNum() == num) {
                iterator.remove();
            }
        }
    }

    public static void update(Goods goods) {
        int index = goods.getNum()-1;
        if (index != -1)
            cartList.set(index, goods);
        else
            System.out.println(goods.getGoodsName()+"商品不存在");
    }
    public static Goods query(int num) {
        for (Goods goods : cartList) {
            if (goods.getNum()==num) {
                return goods;
            }
        }
        return null; // 用户不存在
    }
    public static void printCart(ArrayList<Goods> listName){
        System.out.println("序号\t\t"+"商品名称\t\t"+"数量\t\t"+"单价");
        for(Goods goods : listName){
            System.out.println(goods.getNum()+"\t\t"+goods.getGoodsName()+"\t\t"+goods.getSalesVolume()+"\t\t"+goods.getPrice());
        }
    }
    public static void shoppingCart(){//购物车
        initializeShoppingCart();
        try{
            boolean isContiue=true;
            while(isContiue){
                System.out.println("亲爱的用户，请选择需要对购物车进行的操作：");
                System.out.println("列出所有（1），增加（2），减少（3），删除商品（4），计算总价（5），清空购物车（6），查看购物历史信息（7），返回主菜单（8）");
                Scanner reader=new Scanner(System.in);
                int chance= reader.nextInt();//选择
                switch(chance){
                    case 1:
                        System.out.println("----------------购物车信息（用户个人可见）------------------");
                        printCart(cartList);
                        break;
                    case 2:
                        System.out.printf("请输入需要增加的商品序号(每次添加一件):");
                        reader=new Scanner(System.in);
                        int num= reader.nextInt();
                        int number=query(num).getSalesVolume()+1;
                        update(new Goods(num,query(num).getGoodsName(),number,query(num).getPrice()));
                        System.out.println("添加后该商品的信息：");
                        Goods foundGoods = query(num);
                        if (foundGoods != null) {
                            System.out.println(foundGoods);
                        } else {
                            System.out.println("商品不存在！");
                        }
                        break;
                    case 3:
                        System.out.printf("请输入需要减少的商品序号(每次减少一件):");
                        reader=new Scanner(System.in);
                        num= reader.nextInt();
                        if(query(num).getSalesVolume()==0)
                            System.out.println("该商品为0件，不可再减!");
                        else{
                            number=query(num).getSalesVolume()-1;
                            update(new Goods(num,query(num).getGoodsName(),number,query(num).getPrice()));
                            System.out.println("减少后该商品的信息：");
                        }
                        foundGoods = query(num);
                        if (foundGoods != null) {
                            System.out.println(foundGoods);
                        } else {
                            System.out.println("商品不存在！");
                        }
                        break;
                    case 4:
                        System.out.printf("请输入需要删除的商品序号(直接删除这一商品):");
                        reader=new Scanner(System.in);
                        num= reader.nextInt();
                        delete(num);
                        System.out.println("删除后的购物车：");
                        printCart(cartList);
                        break;
                    case 5:
                        double sum=0.0;
                        for (Goods goods : cartList) {
                            sum+=goods.getSalesVolume()*goods.getPrice();
                        }
                        TxtAction.writeGoodsToFile(cartList, "cart.txt",false);
                        shoppingList=cartList;
                        System.out.printf("购物车总价格为:"+sum+"元,请支付！\n");
                        break;
                    case 6:
                        System.out.println("购物车已清空");
                        cartList=null;
                        break;
                    case 7:
                        System.out.println("----------------购物历史信息（用户个人可见）------------------");
                        TxtAction.writeGoodsToFile(shoppingList, "shoppingHistory.txt",true);
                        printCart(shoppingList);
                        break;
                    case 8:
                        isContiue=false;
                        break;
                    default:
                        System.out.println("输入选项为1-8，请重新输入！");
                }
            }
        }catch (NullPointerException e){
            System.out.println("购物车已清空，无法继续增加、减少、删除商品！");
        }
    }
}
