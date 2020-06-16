package com.ubiRobot.modules.main.market.orderRecord.orderInfo;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.OtcInfoBean;

import java.util.List;

public interface OrderInfoView extends BaseView {

    void requestFail(int code, String msg);

    void cancelBuyOrderBack(String msg);

    void paidSuccess(String msg);

    void sellConfirmSuccess(String msg);
}
