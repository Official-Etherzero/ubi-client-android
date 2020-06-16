package com.ubiRobot.modules.user.collectionCode;


import com.ubiRobot.base.BasePresent;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class CollectionCodePresenter extends BasePresent<CollectionCodeView> {


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
                view.uplodeSuccess(obj);
            }
            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });

    }


}
