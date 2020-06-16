package com.ubiRobot.modules.user.Login;


import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginPresenter extends BasePresent<LoginView> {

    public void loginPhone(String AreaCode, String MobilePhone, String Passwd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("AreaCode", AreaCode);
        hashMap.put("MobilePhone", MobilePhone);
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().phoneLogin(object.toString(), new DataSource.DataCallback<UserBean>() {
            @Override
            public void onDataLoaded(UserBean obj) {
                view.login(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {

                view.requestFail(toastMessage);
            }
        });
    }

}
