package com.ubiRobot.modules.main.property;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.AuthenticationBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;

import org.json.JSONObject;

import java.util.HashMap;

public class PropertyPresenter extends BasePresent<PropertyView> {


    public void getUserInfo(String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getUserInfo(object.toString(), new DataSource.DataCallback<UserBean>() {
            @Override
            public void onDataLoaded(UserBean obj) {

                view.userInfo(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                if (code == 401)
                    view.requestFail(code, toastMessage);
            }
        });
    }

    public void getAliDescribeVerifyToken(String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getAliDescribeVerifyToken(object.toString(), new DataSource.DataCallback<AuthenticationBean>() {
            @Override
            public void onDataLoaded(AuthenticationBean obj) {
                view.setVerifyToken(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });
    }

    public void getAliDescribeVerifyResult(String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getAliDescribeVerifyResult(object.toString(), new DataSource.DataCallback<AuthenticationBean>() {
            @Override
            public void onDataLoaded(AuthenticationBean obj) {
                view.setVerifyResult(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });
    }

    public void buyFreeNode(String Access_Token, String Passwd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("Passwd", Passwd);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().buyFreeNode(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.buyFreeNodeSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });
    }


}
