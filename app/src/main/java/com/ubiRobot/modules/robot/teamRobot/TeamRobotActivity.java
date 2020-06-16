package com.ubiRobot.modules.robot.teamRobot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.adapter.TeamNodeAdapter;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.NodeRevenueBean;
import com.ubiRobot.bean.TeamNodeDataBean;
import com.ubiRobot.modules.user.Login.LoginActivity;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;
import com.ubiRobot.view.MText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamRobotActivity extends BaseActivity<TeamRobotView, TeamRobotPresenter> implements TeamRobotView {


    @BindView(R.id.team_total)
    MText teamTotal;
    @BindView(R.id.team_personal_num)
    TextView teamPersonalNum;
    @BindView(R.id.team_robot_num)
    TextView teamNodeNum;
    @BindView(R.id.team_zrjl)
    TextView teamZrjl;
    @BindView(R.id.team_leve)
    ImageView teamLeve;
    @BindView(R.id.team_node_list)
    RecyclerView teamNodeList;

    private TeamNodeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_robot;
    }

    @Override
    public TeamRobotPresenter initPresenter() {
        return new TeamRobotPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.team));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        teamNodeList.setLayoutManager(linearLayoutManager);
        adapter = new TeamNodeAdapter(R.layout.team_node_item, new ArrayList<>());
        teamNodeList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String token = SharedPrefsUitls.getInstance().getUserToken();
        presenter.getTeamNodeList("100", "1", token);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void setTeamNodeData(TeamNodeDataBean data) {
        MyLog.i("TeamNodeDataBean=" + data.toString());
        if (data != null) {
            teamTotal.setText(data.getTeamReward());
            teamPersonalNum.setText(data.getPeopleCount() + "/" + data.getPeopleCount2());
            teamNodeNum.setText(data.getTeamNodeCount());
            teamZrjl.setText(data.getRewardYesterday());
            int leve = Integer.valueOf(data.getTeamLevel());
            if (leve == 1) {
                teamLeve.setImageResource(R.mipmap.grade_1);
            } else if (leve == 2) {
                teamLeve.setImageResource(R.mipmap.grade_2);
            } else if (leve == 3) {
                teamLeve.setImageResource(R.mipmap.grade_3);
            } else if (leve == 4) {
                teamLeve.setImageResource(R.mipmap.grade_4);
            }
            adapter.setTokens(data.getUserNodeList());
        }
    }

    @Override
    public void setTeamRewardList(List<NodeRevenueBean> list) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.team_jlmx)
    public void onViewClicked() {
        startActivity(new Intent(TeamRobotActivity.this, TeamRewardDetailsActivity.class));
    }
}
