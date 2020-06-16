package com.ubiRobot.modules.robot.robotHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.MyRobotListAdapter;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.modules.robot.RobotInfoActivity;
import com.ubiRobot.modules.robot.RobotMorePopupWindow;
import com.ubiRobot.modules.robot.robotBuy.RobotBuyActivity;
import com.ubiRobot.modules.robot.teamRobot.TeamRobotActivity;
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

public class RobotHomeActivity extends BaseActivity<RobotHomeView, RobotHomePresenter> implements RobotHomeView {


    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.robot_qwsl)
    TextView robotQwsl;
    @BindView(R.id.robot_qwjd)
    TextView robotQwjd;
    @BindView(R.id.robot_wdsl)
    TextView robotWdsl;
    @BindView(R.id.robot_tdsl)
    TextView robotTdsl;
    @BindView(R.id.robot_inaction_list)
    RecyclerView robotInactionList;
    @BindView(R.id.robot_ll_not)
    LinearLayout robotLlNot;
    @BindView(R.id.robot_home_ll)
    RelativeLayout robot_home_ll;
    //    @BindView(R.id.robot_add_btn)
//    LinearLayout robotAddBtn;
    @BindView(R.id.robot_home_balance)
    MText robotHomeBalance;

    private MyRobotListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RobotMorePopupWindow popupWindow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_robot_home;
    }

    @Override
    public RobotHomePresenter initPresenter() {
        return new RobotHomePresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.robot));
//        imgRight.setImageResource(R.mipmap.robot_more);
//        imgRight.setVisibility(View.VISIBLE);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        robotInactionList.setLayoutManager(linearLayoutManager);
        adapter = new MyRobotListAdapter(R.layout.myrobot_item_layout, new ArrayList<>());
        robotInactionList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyRobotBean bean = (MyRobotBean) adapter.getItem(position);
                startActivity(new Intent(activity, RobotInfoActivity.class).putExtra("robot", bean));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
    }

    @Override
    protected void onResume() {
        super.onResume();

        String token = SharedPrefsUitls.getInstance().getUserToken();

        if (!Util.isNullOrEmpty(token)) {
            //加载数据 89720429
            MyLog.i("-----------" + token);
            presenter.getUserInfo(token);
            presenter.getUserMiningList("30", "1", token);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_right, R.id.robot_add_btn,R.id.robot_tdsl_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_right:
                popupWindow = new RobotMorePopupWindow(activity);
                popupWindow.showAtLocation(robot_home_ll, Gravity.TOP, 0, 0);
                break;
            case R.id.robot_add_btn:
                startActivity(new Intent(activity, RobotBuyActivity.class));
                break;
            case R.id.robot_tdsl_ll:
                startActivity(new Intent(activity, TeamRobotActivity.class));
                break;
        }
    }


    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void setUserMiningList(List<MyRobotBean> beans) {
        if (beans != null && beans.size() > 0) {
            adapter.setTokens(beans);
            robotLlNot.setVisibility(View.GONE);
        } else {
            robotLlNot.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void userInfo(UserBean user) {
        if (user != null) {
            MyApp.getmInstance().setUser(user);
            robotQwsl.setText(user.getAllSuanLi());
            robotQwjd.setText(user.getAllNodeCount());
            robotWdsl.setText(user.getMySuanLi());
            robotTdsl.setText(user.getTeamSuanLi());
            robotHomeBalance.setText(user.getETZ());
        }
    }

}
