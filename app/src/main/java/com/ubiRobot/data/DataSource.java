package com.ubiRobot.data;


import com.ubiRobot.R;
import com.ubiRobot.bean.AuthenticationBean;
import com.ubiRobot.bean.C2COrderBean;
import com.ubiRobot.bean.DailyEarningsListBean;
import com.ubiRobot.bean.GoogleSecretBean;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.LatestInterestBean;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MiningIncomeBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.bean.NodeRevenueBean;
import com.ubiRobot.bean.OtcInfoBean;
import com.ubiRobot.bean.ReceiptQRBean;
import com.ubiRobot.bean.RobotBean;
import com.ubiRobot.bean.TeamNodeDataBean;
import com.ubiRobot.bean.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface DataSource {


    //新接口
    void getPhoneCode(String json, DataCallback<Integer> dataCallback);

    void phoneRegister(String json, DataCallback<UserBean> dataCallback);

    void phoneLogin(String json, DataCallback<UserBean> dataCallback);

    void retsetPWDbyPhone(String json, DataCallback<Integer> dataCallback);

    void getUserInfo(String json, DataCallback<UserBean> dataCallback);

    void getAliDescribeVerifyToken(String json, DataCallback<AuthenticationBean> dataCallback);

    void getAliDescribeVerifyResult(String json, DataCallback<AuthenticationBean> dataCallback);


    //机器人
    void getMiningList(String jon, DataCallback<List<RobotBean>> dataCallback);

    void miningBuy(String json, DataCallback<Integer> dataCallback);
    void buyFreeNode(String json, DataCallback<Integer> dataCallback);

    void getUserMiningList(String json, DataCallback<List<MyRobotBean>> dataCallback);

    void getMiningEarningList(String json, DataCallback<DailyEarningsListBean> dataCallback);

    void transfe(String json, DataCallback<String> dataCallback);

    void getTeamNodeList(String json, DataCallback<TeamNodeDataBean> dataCallback);
    void getDetailedList(String json, DataCallback<List<NodeRevenueBean>> dataCallback);
    //c2c
    void getC2CInfo(String json, DataCallback<LatestInterestBean> dataCallback);
    void orderLock(String json, DataCallback<Integer> dataCallback);
    void confirmSale(String json, DataCallback<Integer> dataCallback);
    void unConfirmSale(String json, DataCallback<Integer> dataCallback);
    void createBuy(String json, DataCallback<Integer> dataCallback);
    void getListOrders(String json, DataCallback<List<C2COrderBean>> dataCallback);
    void myOrders(String json, DataCallback<List<HangOrderBean>> dataCallback);
    void cancelBuy(String json, DataCallback<String> dataCallback);
    void confirmSuccess(String json, DataCallback<String> dataCallback);
    void confirmPaid(String json, DataCallback<String> dataCallback);
    void getOrderQRCode(String json, DataCallback<ReceiptQRBean> dataCallback);






    interface DataCallback<T> {

        void onDataLoaded(T obj);

        void onDataNotAvailable(Integer code, String toastMessage);
    }

    void getDepositList(String token, DataCallback dataCallback);

    void commitSellerApply(String token, String coinId, String json, DataCallback dataCallback);

}
