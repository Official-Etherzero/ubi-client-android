package com.ubiRobot.modules.robot.robotHome;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class RobotHomePresenter extends BasePresent<RobotHomeView> {


    public void getUserMiningList(String PageSize, String CurrentPage, String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("PageSize", PageSize);
        hashMap.put("CurrentPage", CurrentPage);
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getUserMiningList(object.toString(), new DataSource.DataCallback<List<MyRobotBean>>() {
            @Override
            public void onDataLoaded(List<MyRobotBean> obj) {
                view.setUserMiningList(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });

    }

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

}
