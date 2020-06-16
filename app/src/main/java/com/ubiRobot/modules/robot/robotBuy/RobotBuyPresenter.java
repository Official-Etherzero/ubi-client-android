package com.ubiRobot.modules.robot.robotBuy;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.bean.OtcInfoBean;
import com.ubiRobot.bean.RobotBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.modules.robot.robotHome.RobotHomeView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class RobotBuyPresenter extends BasePresent<RobotBuyView> {


    public void getMiningList() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getMiningList(object.toString(), new DataSource.DataCallback<List<RobotBean>>() {
            @Override
            public void onDataLoaded(List<RobotBean> obj) {
                view.setMiningList(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
            }
        });

    }

    public void miningBuy(String Access_Token, String NodeID, String Passwd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("NodeID", NodeID);
        hashMap.put("Passwd", Passwd);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().miningBuy(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer obj) {
                view.miningBuySuccess(obj);
            }
            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage,code);
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
                    view.requestFail(toastMessage,code);
            }
        });
    }

}
