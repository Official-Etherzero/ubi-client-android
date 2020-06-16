package com.ubiRobot.modules.user.ForgetPassword;


import com.ubiRobot.base.BaseView;

public interface ForgetPasswordView extends BaseView {
    void requestFail(String msg);

    void codeSuccess(int code);

    void resetSuccess(int code);


}
