package com.ubiRobot.modules.main.market;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.C2COrderBean;
import com.ubiRobot.bean.DailyEarningsListBean;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.LatestInterestBean;
import com.ubiRobot.bean.OtcInfoBean;

import java.util.List;

public interface MarkerC2CView extends BaseView {

    void requestFail(int code, String msg);

    void createBuyFail(int code, String msg);

    void setC2CInfo(LatestInterestBean c2CInfo);

    void orderLockSuccess(int code);

    void confirmSaleSuccess(int code);

    void unConfirmSaleSuccess(int code);

    void createBuySuccess(int code);

    void listOrders(List<C2COrderBean> list);

}
