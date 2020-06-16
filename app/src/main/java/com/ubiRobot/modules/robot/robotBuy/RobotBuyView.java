package com.ubiRobot.modules.robot.robotBuy;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.bean.OtcInfoBean;
import com.ubiRobot.bean.RobotBean;
import com.ubiRobot.bean.UserBean;

import java.util.List;

public interface RobotBuyView extends BaseView {

    void requestFail(String msg, int code);

    void miningBuySuccess(int code);

    void setMiningList(List<RobotBean> beans);

    void userInfo(UserBean user);
}
