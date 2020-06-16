package com.ubiRobot.modules.user.transfer;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.AuthenticationBean;
import com.ubiRobot.bean.UserBean;

public interface TransfeView extends BaseView {

    void requestFail(int code, String msg);

    void transfeSuccesss(String msg);



}
