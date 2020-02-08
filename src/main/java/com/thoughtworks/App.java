package com.thoughtworks;

import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    String[] allItemNames = getItemNames();
    String[] allItemIds = getItemIds();
    double[] allItemPrices = getItemPrices();
    String[] halfPriceIds = getHalfPriceIds();
    String[] selected = selectedItems.split(",");
    int[] selectedNumbers = new int[selected.length];
    String[] selectedIds = new String[selected.length];
    String orderDetails = "============= 订餐明细 =============\n";
    for(int i=0;i<selected.length;i++){
      selectedNumbers[i] = Integer.parseInt(selected[i].split(" x ")[1]);
      selectedIds[i] = selected[i].split(" x ")[0];
      orderDetails += getSingleName(selectedIds[i]) + " x " + selectedNumbers[i] + " = "
                      + getSinglePrice(selectedIds[i], selectedNumbers[i]) + "元\n";
    }
    orderDetails += "-----------------------------------\n";
    int totalPrice = 0;
    int withHalfPrice = 0;
    int withThirty = 0;
    String selectedHalf = "";
    int savedMoney = 0;
    for(int i=0;i<selected.length;i++){
      int rawPrice = getSinglePrice(selectedIds[i], selectedNumbers[i]);
      totalPrice += rawPrice;
      if(isHalfPrice(selectedIds[i])){
        withHalfPrice += 0.5*rawPrice;
        selectedHalf += getSingleName(selectedIds[i])+"，";
        savedMoney += 0.5*rawPrice;
      }else {
        withHalfPrice += rawPrice;
      }
    }
    if(totalPrice>30){
      withThirty = totalPrice - 6;
    }else{
      withThirty = totalPrice;
    }
    if(totalPrice==withThirty&&totalPrice==withHalfPrice){
      orderDetails += "总计：" + totalPrice  + "元\n"
                    + "===================================";
      return orderDetails;
    }
    if(withHalfPrice<withThirty){
      orderDetails += "使用优惠:\n"
                     + "指定菜品半价("+selectedHalf.substring(0,selectedHalf.length()-1)
                     + ")，省" + savedMoney + "元\n"
                     + "-----------------------------------\n"
                     + "总计：" + withHalfPrice +"元\n"
                     + "===================================";
      return orderDetails;
    }
    if(withHalfPrice>withThirty&&withThirty!=totalPrice){
      orderDetails += "使用优惠:\n"
                    + "满30减6元，省6元\n"
                    + "-----------------------------------\n"
                    + "总计：" + withThirty +"元\n"
                    + "===================================";
      return orderDetails;
    }

    return "order is wrong!";
  }

  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }

  public static String getSingleName(String id) {
    switch (id) {
      case "ITEM0001":
        return "黄焖鸡";
      case "ITEM0013":
        return "肉夹馍";
      case "ITEM0022":
        return "凉皮";
      case "ITEM0030":
        return "冰粉";
      default:
        return "not found";
    }
  }

  public static int getSinglePrice(String id, int number) {
    switch (id) {
      case "ITEM0001":
        return 18*number;
      case "ITEM0013":
        return 6*number;
      case "ITEM0022":
        return 8*number;
      case "ITEM0030":
        return 2*number;
      default:
        return 0;
    }
  }

  public static boolean isHalfPrice(String id){
    if(id.equals("ITEM0001")||id.equals("ITEM0022")) {
      return true;
    }else{
      return false;
    }
  }

}
