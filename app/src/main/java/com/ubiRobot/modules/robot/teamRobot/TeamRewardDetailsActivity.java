package com.ubiRobot.modules.robot.teamRobot;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.ubiRobot.R;
import com.ubiRobot.adapter.OrderRecordAdapter;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.base.BaseFragment;
import com.ubiRobot.modules.normalvp.NormalPresenter;
import com.ubiRobot.modules.normalvp.NormalView;
import com.ubiRobot.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamRewardDetailsActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.order_record_tab)
    ScrollIndicatorView orderRecordTab;
    @BindView(R.id.order_record_vp)
    ViewPager orderRecordVp;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private OrderRecordAdapter adapter;
    private IndicatorViewPager indicatorViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team_reward_details;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        FragmentDetailsList team=new FragmentDetailsList();
        team.setType("4");
        FragmentDetailsList zt=new FragmentDetailsList();
        zt.setType("7");
        fragmentList.add(team);
        fragmentList.add(zt);

        setCenterTitle(getResources().getString(R.string.team_node_jlmx));

        orderRecordTab.setSplitAuto(true);
        orderRecordTab.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.zt_lu), getResources().getColor(R.color.zt_lab2))
                .setSize(14, 14));
        orderRecordTab.setScrollBar(new TextWidthColorBar(this, orderRecordTab, getResources().getColor(R.color.zt_lu), Util.dip2px(3)));
        orderRecordTab.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(orderRecordTab, orderRecordVp);
        adapter = new OrderRecordAdapter(this, getSupportFragmentManager(), fragmentList);
        indicatorViewPager.setAdapter(adapter);
        indicatorViewPager.setCurrentItem(0, false);
        orderRecordVp.setOffscreenPageLimit(2);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
