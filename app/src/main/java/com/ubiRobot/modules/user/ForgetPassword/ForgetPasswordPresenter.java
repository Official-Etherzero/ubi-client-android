package com.ubiRobot.modules.user.ForgetPassword;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class ForgetPasswordPresenter extends BasePresent<ForgetPasswordView> {
    public void getPhoneCode(String AreaCode, String MobilePhone) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("AreaCode", AreaCode);
        hashMap.put("MobilePhone", MobilePhone);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getPhoneCode(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.codeSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });

    }



    public void resetPhonePWD(String AreaCode, String MobilePhone, String NewPasswd,String VerifyCode) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("AreaCode", AreaCode);
        hashMap.put("MobilePhone", MobilePhone);
        hashMap.put("NewPasswd", Md5Utils.md5(NewPasswd));
        hashMap.put("VerifyCode", VerifyCode);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().retsetPWDbyPhone(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.resetSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }


}
