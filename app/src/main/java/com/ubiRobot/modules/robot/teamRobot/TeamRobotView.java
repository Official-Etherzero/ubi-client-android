package com.ubiRobot.modules.robot.teamRobot;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.NodeRevenueBean;
import com.ubiRobot.bean.TeamNodeDataBean;

import java.util.List;

public interface TeamRobotView extends BaseView {

    void requestFail(int code, String msg);

    void setTeamNodeData(TeamNodeDataBean data);

    void setTeamRewardList(List<NodeRevenueBean> list);


}
