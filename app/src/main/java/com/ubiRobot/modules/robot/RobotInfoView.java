package com.ubiRobot.modules.robot;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.DailyEarningsListBean;

public interface RobotInfoView extends BaseView {

    void requestFail(int code,String msg);

    void setMiningEarningList(DailyEarningsListBean beans);
}
