package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class GoodsManagement {
    public static ArrayList<Goods> goodsList;
    public static void initializeGoods() {
        goodsList=new ArrayList<>();
        goodsList=TxtAction.readGoodsFromFile("goods.txt");//用户文件不空则直接读出
        if(goodsList==null || goodsList.isEmpty()){//如果该文件读出为空，将预设的信息导入goodsList和文件
            Goods goods1=new Goods(1,"手机壳",121,20);addGoods(goods1);
            Goods goods2=new Goods(2,"长 袜",80,10);addGoods(goods2);
            Goods goods3=new Goods(3,"电动车",1,3200);addGoods(goods3);
            Goods goods4=new Goods(4,"水 杯",2,60);addGoods(goods4);
            Goods goods5=new Goods(5,"书 包",22,89);addGoods(goods5);
            Goods goods6=new Goods(6,"玲娜贝儿",33,269);addGoods(goods6);
            Goods goods7=new Goods(7,"卡 套",16,5.9);addGoods(goods7);
            Goods goods8=new Goods(8,"防晒衣",71,159);addGoods(goods8);
            Goods goods9=new Goods(9,"窗 帘",12,250);addGoods(goods9);
            Goods goods10=new Goods(10,"口 罩",21,55);addGoods(goods10);
            Goods goods11=new Goods(11,"面 包",82,65);addGoods(goods11);
            TxtAction.writeGoodsToFile(goodsList, "goods.txt",false);
        }
    }
    public static void addGoods(Goods goods) {
        goodsList.add(goods);
    }
    public static void deleteGoods(Goods goods) {
        goodsList.remove(goods);
    }

    public static void updateGoods(Goods goods) {
        int index = findGoodsIndex(goods.getNum());
        if (index != -1)
            goodsList.set(index, goods);
        else
            System.out.println("商品不存在");
    }
    public static Goods queryGoods(int num) {
        for (Goods goods : goodsList) {
            if (goods.getNum()==num) {
                return goods;
            }
        }
        return null; // 用户不存在
    }
    public static void printGoods(){
        System.out.println("----------------商品系统信息（管理员可见）------------------");
        System.out.println("序号\t\t"+"商品名称\t\t"+"销量\t\t"+"单价");
        for(Goods goods : goodsList){
            System.out.println(goods.getNum()+"\t\t"+goods.getGoodsName()+"\t\t"+goods.getSalesVolume()+"\t\t"+goods.getPrice());
        }
    }
    private static int findGoodsIndex(int num) {
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).getNum()==num) {
                return i;
            }
        }
        return -1; // 用户不存在
    }

    public static void goodsAction() {
        initializeGoods();
        int count=12;
        boolean isContiue=true;
        while(isContiue){
            System.out.println("选择需要对商品信息进行的操作：\n列出所有（1），增加（2），删除（3），修改（4），查询（5），返回主菜单（6）");
            Scanner reader=new Scanner(System.in);
            int chance= reader.nextInt();//选择
            switch(chance){
                case 1:
                    printGoods();
                    break;
                case 2:
                    System.out.printf("依次输入需要增加的商品名称、商品销量、商品单价：");
                    reader=new Scanner(System.in);
                    String name= reader.next();
                    int salesVolume=reader.nextInt();
                    double price= reader.nextDouble();
                    Goods goodsNew=new Goods((count++),name,salesVolume,price);addGoods(goodsNew);
                    System.out.println("添加后的商品系统：");
                    printGoods();
                    break;
                case 3:
                    System.out.printf("输入删除的商品序号:");
                    reader=new Scanner(System.in);
                    int num= reader.nextInt();
                    deleteGoods(queryGoods(num));
                    System.out.println("删除后的商品系统：");
                    printGoods();
                    break;
                case 4:
                    System.out.printf("输入修改的商品序号:");
                    reader=new Scanner(System.in);
                    num= reader.nextInt();
                    System.out.printf("请输入修改后的商品内容（商品名称、销量、单价）：");
                    reader=new Scanner(System.in);
                    name= reader.next();
                    int sales=reader.nextInt();
                    double priceNew=reader.nextDouble();
                    updateGoods(new Goods(num,name,sales,priceNew));
                    System.out.println("修改后的该商品：");
                    Goods foundGoods = queryGoods(num);
                    if (foundGoods != null) {
                        System.out.println(foundGoods);
                    } else {
                        System.out.println("商品不存在！");
                    }
                    break;
                case 5:
                    System.out.printf("输入查询的商品序号:");
                    reader=new Scanner(System.in);
                    num= reader.nextInt();
                    foundGoods = queryGoods(num);
                    if (foundGoods != null) {
                        System.out.println(foundGoods);
                    } else {
                        System.out.println("商品不存在！");
                    }
                    break;
                case 6:
                    TxtAction.writeGoodsToFile(goodsList,"goods.txt",false);
                    isContiue=false;
                    break;
                default:
                    System.out.println("输入选项为1-6，请重新输入！");
            }
        }
    }
}
class Goods{
    private int num;
    private String goodsName;
    private int salesVolume;
    private double price;
    public Goods(int num, String goodsName, int salesVolume, double price) {
        this.num = num;
        this.goodsName = goodsName;
        this.salesVolume = salesVolume;
        this.price = price;
    }
    public int getNum() {
        return num;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public int getSalesVolume() {
        return salesVolume;
    }
    public void SetSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }
    public double getPrice() {
        return price;
    }
    @Override    public String toString() {
        return "Goods{" +
                "序号='" + num + '\'' +
                ", 商品名称='" + goodsName + '\'' +
                ", 销量='" + salesVolume + '\'' +
                ", 单价='" + price + '\'' +
                '}';
    }
}
