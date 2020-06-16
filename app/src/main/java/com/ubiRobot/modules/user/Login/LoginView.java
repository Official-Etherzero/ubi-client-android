package com.ubiRobot.modules.user.Login;


import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.UserBean;

public interface LoginView extends BaseView {

    void requestFail(String msg);

    void login(UserBean user);
}
