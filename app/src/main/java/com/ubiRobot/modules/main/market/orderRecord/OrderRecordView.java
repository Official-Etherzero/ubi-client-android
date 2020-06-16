package com.ubiRobot.modules.main.market.orderRecord;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.OtcInfoBean;
import com.ubiRobot.bean.ReceiptQRBean;

import java.util.List;

public interface OrderRecordView extends BaseView {

    void requestFail(String msg);

    void getMyOrders(List<HangOrderBean> beans);

    void setCollectionCode(ReceiptQRBean qrBean);

}
