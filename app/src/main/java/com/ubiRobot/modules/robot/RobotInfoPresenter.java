package com.ubiRobot.modules.robot;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.DailyEarningsListBean;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.modules.robot.robotHome.RobotHomeView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class RobotInfoPresenter extends BasePresent<RobotInfoView> {

    public void getMiningEarningList(String NodeID, String PageSize, String CurrentPage, String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("NodeID", NodeID);
        hashMap.put("PageSize", PageSize);
        hashMap.put("CurrentPage", CurrentPage);
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getMiningEarningList(object.toString(), new DataSource.DataCallback<DailyEarningsListBean>() {
            @Override
            public void onDataLoaded(DailyEarningsListBean obj) {
                view.setMiningEarningList(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });
    }
}
