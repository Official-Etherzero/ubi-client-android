package com.ubiRobot.app;



/**
 * Created by Administrator on 2018/1/29.
 */

public class UrlFactory {

    public static final String host1 = "https://ubi.wanlege.com";



    //手机册验证码
    public static String getPhoneCodeURL() { return host1 + "/ubi/account/getSMSCode"; }
    //手机注册
    public static String getPhoneRegisterURL() {
        return host1 + "/ubi/account/registerByPhone";
    }
    //手机登录
    public static String getLoginPhoneURL() { return host1 + "/ubi/account/loginByPhone"; }
    //手机账户重置密码
    public static String getRetsetPWDbyPhoneURL() { return host1 + "/ubi/account/retsetPWDbyPhone"; }
    //用户信息
    public static String getUserInfoURL() { return host1 + "/ubi/account/getUserInfo"; }

    //实人认正
    public static String getAliDescribeVerifyTokenURL() { return host1 + "/ubi/account/AliDescribeVerifyToken"; }
    public static String getAliDescribeVerifyResultURL() { return host1 + "/ubi/account/AliDescribeVerifyResult"; }
    //上传收款二维吗
    public static String SetPaymentProfileUrl() { return host1 + "/ubi/account/SetPaymentProfile"; }

    //节点
    //节点列表
    public static String getNodeListURL() { return host1 + "/ubi/mining/getNodeList"; }
    //购买节点
    public static String miningBuy() { return host1 + "/ubi/mining/buyNode"; }
    //我的节点列表
    public static String getUserMiningList() { return host1 + "/ubi/mining/getMyNodeList"; }
    //我的节点收益列表
    public static String getMiningEarningList() { return host1 + "/ubi/mining/getNodeRewardList"; }
    //内部转账
    public static String getTransferUrl() { return host1 + "/ubi/account/Transfer"; }
    //团队节点列表
    public static String getTeamNodeListURL() { return host1 + "/ubi/mining/getTeamNodeList"; }
    public static String getDetailedListURL() { return host1 + "/ubi/mining/getDetailedList"; }
    public static String getBuyFreeNodeURL() { return host1 + "/ubi/mining/buyFreeNode"; }

    //c2c
    //统计信息
    public static String getC2CInfoURL() { return host1 + "/ubi/otc2/getInfo"; }
    //订单锁定
    public static String getOrderLockURL() { return host1 + "/ubi/otc2/lock"; }
    //确认出售
    public static String getConfirmSaleURL() { return host1 + "/ubi/otc2/confirmSale"; }
    //取消出售
    public static String getUnConfirmSaleURL() { return host1 + "/ubi/otc2/unConfirmSale"; }
    //求购
    public static String getCreateBuyURL() { return host1 + "/ubi/otc2/createBuy"; }
    //求购列表
    public static String getListOrdersURL() { return host1 + "/ubi/otc2/listOrders"; }
    //订单记录
    public  static String myOrders() { return host1 + "/ubi/otc2/listMyOrders";}
    //出售者确认是否收到钱
    public  static String confirmSuccessUrl() { return host1 + "/ubi/otc2/confirmSuccess";}
    //求购者确认支付
    public  static String confirmPaidUrl() { return host1 + "/ubi/otc2/notice";}
    //求购者取消求购单
    public  static String cancelBuyUrl() { return host1 + "/ubi/otc2/cancelBuy";}
    //获取订单收款吗码
    public  static String getOrderQRCode() { return host1 + "/ubi/otc2/detailOfQR";}


}
