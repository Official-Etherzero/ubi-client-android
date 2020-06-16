package com.ubiRobot.modules.robot;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.adapter.RobotEarningsAdapter;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.DailyEarningsListBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.modules.user.Login.LoginActivity;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.view.RingProgressView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RobotInfoActivity extends BaseActivity<RobotInfoView, RobotInfoPresenter> implements RobotInfoView {


    @BindView(R.id.robot_date_open)
    TextView robotDateOpen;
    @BindView(R.id.robot_date_close)
    TextView robotDateClose;
    @BindView(R.id.robot_ljsy)
    TextView robotLjsy;
    @BindView(R.id.robot_progress)
    RingProgressView robot_progress;
    @BindView(R.id.robot_info_list)
    RecyclerView robotInfoList;
    @BindView(R.id.robot_not_log)
    LinearLayout robotNotLog;
    @BindView(R.id.robot_info_title_bg)
    ConstraintLayout robotInfoTitleBg;

    private RobotEarningsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private MyRobotBean bean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_robot_info;
    }

    @Override
    public RobotInfoPresenter initPresenter() {
        return new RobotInfoPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.robot));
        bean = getIntent().getParcelableExtra("robot");
        if (bean != null) {
            robotDateOpen.setText(bean.getStartTime());
            robotDateClose.setText(bean.getExpireTime());
            String progerss = bean.getRateOfProgress();
            progerss = progerss.substring(0, progerss.indexOf("%"));
            robot_progress.setCurrentProgress(Integer.valueOf(progerss));
            switch (bean.getMiniID()) {
                case 1:
                    robotInfoTitleBg.setBackgroundResource(R.mipmap.primary_big);
                    break;
                case 2:
                    robotInfoTitleBg.setBackgroundResource(R.mipmap.intermediate_big);
                    break;
                case 3:
                    robotInfoTitleBg.setBackgroundResource(R.mipmap.multifunctional_big);
                    break;
                case 4:
                    robotInfoTitleBg.setBackgroundResource(R.mipmap.top_big);
                    break;
                case 5:
                    robotInfoTitleBg.setBackgroundResource(R.mipmap.super_big);
                    break;
                case 6:
                    robotInfoTitleBg.setBackgroundResource(R.mipmap.intelligent_big);
                    break;
            }
        }
        presenter.getMiningEarningList(bean.getOrderID(),  "50", "1", SharedPrefsUitls.getInstance().getUserToken());
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        robotInfoList.setLayoutManager(linearLayoutManager);
        adapter = new RobotEarningsAdapter(R.layout.daily_earnings_item, new ArrayList<>());
        robotInfoList.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void requestFail(int code,String msg) {
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void setMiningEarningList(DailyEarningsListBean beans) {
        robotLjsy.setText(beans.getTotalEarning());
        if (beans.getMiningEarningList() != null && beans.getMiningEarningList().size() > 0) {
            robotNotLog.setVisibility(View.GONE);
        } else {
            robotNotLog.setVisibility(View.VISIBLE);
        }
        adapter.setTokens(beans.getMiningEarningList());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
