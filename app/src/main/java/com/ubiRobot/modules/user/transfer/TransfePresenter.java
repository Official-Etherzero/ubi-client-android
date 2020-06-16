package com.ubiRobot.modules.user.transfer;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.AuthenticationBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.modules.main.property.PropertyView;
import com.ubiRobot.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class TransfePresenter extends BasePresent<TransfeView> {


    public void transfe(String Access_Token, String Passwd, String TargetPhone, String Amount) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        hashMap.put("TargetPhone", TargetPhone);
        hashMap.put("Amount", Amount);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().transfe(object.toString(), new DataSource.DataCallback<String>() {
            @Override
            public void onDataLoaded(String obj) {
                view.transfeSuccesss(obj);
            }
            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });

    }


}
