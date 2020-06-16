package com.ubiRobot.modules.robot.robotBuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.RobotListAdapter;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.RobotBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.modules.user.Login.LoginActivity;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.view.MText;
import com.ubiRobot.view.RobotBuyDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RobotBuyActivity extends BaseActivity<RobotBuyView, RobotBuyPresenter> implements RobotBuyView,
        RobotBuyDialog.OnConfirmClickListener {

    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.robot_buy_inaction_list)
    RecyclerView robotBuyInactionList;
    @BindView(R.id.robot_buy_balance)
    MText robotBuyBalance;

    private RobotListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RobotBuyDialog buyDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_robot_buy;
    }

    @Override
    public RobotBuyPresenter initPresenter() {
        return new RobotBuyPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.robot));
//        presenter.getMiningHashrate(SharedPrefsUitls.getInstance().getUserId());
        presenter.getMiningList();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        robotBuyInactionList.setLayoutManager(linearLayoutManager);
        adapter = new RobotListAdapter(R.layout.robot_item_layout, new ArrayList<>());
        robotBuyInactionList.setAdapter(adapter);
        robotBuyBalance.setText(MyApp.getmInstance().getUser().getETZ());

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RobotBean item = (RobotBean) adapter.getItem(position);
                buyDialog = new RobotBuyDialog(activity, item);
                buyDialog.show();
                buyDialog.setOnConfirmClickListener(RobotBuyActivity.this);
            }
        });

    }

    @Override
    public void requestFail(String msg, int code) {
        MyLog.i("-----requestFail--" + msg
                + "----" + code
        );
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }

    }


    @Override
    public void miningBuySuccess(int code) {
        if (code == 0) {
            buyDialog.dismiss();
            presenter.getMiningList();
            presenter.getUserInfo(SharedPrefsUitls.getInstance().getUserToken());
        }
    }

    @Override
    public void setMiningList(List<RobotBean> beans) {
        adapter.setTokens(beans);
    }

    @Override
    public void userInfo(UserBean user) {
        if (user != null) {
            MyApp.getmInstance().setUser(user);
            robotBuyBalance.setText(user.getETZ());
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //提交购买
    @Override
    public void setConfirmClick(String pwd, String miniId) {
        presenter.miningBuy(SharedPrefsUitls.getInstance().getUserToken(), miniId, pwd);
    }
}
