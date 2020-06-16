package com.ubiRobot.modules.main.property;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.AuthenticationBean;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MiningIncomeBean;
import com.ubiRobot.bean.UserBean;

public interface PropertyView extends BaseView {

    void requestFail(int code, String msg);

    void userInfo(UserBean user);

    void setVerifyToken(AuthenticationBean user);

    void setVerifyResult(AuthenticationBean user);

    void buyFreeNodeSuccess(int code);
}
