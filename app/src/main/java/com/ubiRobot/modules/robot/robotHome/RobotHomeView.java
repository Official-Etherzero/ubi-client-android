package com.ubiRobot.modules.robot.robotHome;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.bean.UserBean;

import java.util.List;

public interface RobotHomeView extends BaseView {

    void requestFail(int code,String msg);


    void setUserMiningList(List<MyRobotBean> beans);

    void userInfo(UserBean user);

}
