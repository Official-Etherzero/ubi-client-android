package com.ubiRobot.modules.user.rigest;


import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.UserBean;

public interface RegisterView extends BaseView {

    void requestFail(String msg);

    void codeSuccess(int code);

    void RegisterSuccess(UserBean user);


}
