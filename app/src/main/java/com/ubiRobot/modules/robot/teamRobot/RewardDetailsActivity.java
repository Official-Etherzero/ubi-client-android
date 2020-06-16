package com.ubiRobot.modules.robot.teamRobot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ubiRobot.R;
import com.ubiRobot.adapter.RevenueRecordAdapter;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.NodeRevenueBean;
import com.ubiRobot.bean.TeamNodeDataBean;
import com.ubiRobot.modules.user.Login.LoginActivity;
import com.ubiRobot.utils.SharedPrefsUitls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardDetailsActivity extends BaseActivity<TeamRobotView, TeamRobotPresenter> implements TeamRobotView {


    @BindView(R.id.reward_details_list)
    RecyclerView rewardDetailsList;

    private RevenueRecordAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reward_details;
    }

    @Override
    public TeamRobotPresenter initPresenter() {
        return new TeamRobotPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.transfer_record));
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rewardDetailsList.setLayoutManager(linearLayoutManager);
        adapter = new RevenueRecordAdapter(R.layout.daily_earnings_item, new ArrayList<>(), 1);
        rewardDetailsList.setAdapter(adapter);
        String token = SharedPrefsUitls.getInstance().getUserToken();
        presenter.getTeamRewardList("100", "1", token,  "0");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void requestFail(int code, String msg) {
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void setTeamNodeData(TeamNodeDataBean data) {

    }

    @Override
    public void setTeamRewardList(List<NodeRevenueBean> list) {
        adapter.setTokens(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
