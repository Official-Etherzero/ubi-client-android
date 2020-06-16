package com.ubiRobot.modules.robot.teamRobot;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.NodeRevenueBean;
import com.ubiRobot.bean.TeamNodeDataBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.modules.user.transfer.TransfeView;
import com.ubiRobot.utils.Md5Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class TeamRobotPresenter extends BasePresent<TeamRobotView> {


    public void getTeamNodeList(String PageSize, String CurrentPage, String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("PageSize", PageSize);
        hashMap.put("CurrentPage", CurrentPage);
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getTeamNodeList(object.toString(), new DataSource.DataCallback<TeamNodeDataBean>() {
            @Override
            public void onDataLoaded(TeamNodeDataBean data) {
                view.setTeamNodeData(data);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);

            }
        });
    }

    public void getTeamRewardList(String PageSize, String CurrentPage, String Access_Token,String Type) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("PageSize", PageSize);
        hashMap.put("CurrentPage", CurrentPage);
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("Type", Type);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getDetailedList(object.toString(), new DataSource.DataCallback<List<NodeRevenueBean>>() {
            @Override
            public void onDataLoaded(List<NodeRevenueBean> list) {
                view.setTeamRewardList(list);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);

            }
        });
    }


}
