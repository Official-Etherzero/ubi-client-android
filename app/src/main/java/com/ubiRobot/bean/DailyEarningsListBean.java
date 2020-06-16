package com.ubiRobot.bean;

import com.ubiRobot.utils.Util;

import java.util.List;

public class DailyEarningsListBean {
    private List<DailyEarningsBean> UserNodeList;
    private String Total;
    private String TotalReward;


    public List<DailyEarningsBean> getMiningEarningList() {
        return UserNodeList;
    }

    public String getTotal() {
        return Total;
    }

    public String getTotalEarning() {
        return Util.isNullOrEmpty(TotalReward)?"0":TotalReward;
    }
}
