package com.ubiRobot.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.bean.RobotBean;

import java.util.List;

public class RobotListAdapter extends BaseQuickAdapter<RobotBean, MyBaseViewHolder> {

    private ItemButtonClickListener listener;

    public RobotListAdapter(int layoutResId, @Nullable List<RobotBean> data) {
        super(layoutResId, data);
        this.listener = listener;
    }

    @Override
    protected void convert(MyBaseViewHolder helper, RobotBean item) {
        ConstraintLayout itemBg = helper.getView(R.id.robot_bg);
        switch (item.getMType()) {
            case 1:
                itemBg.setBackgroundResource(R.mipmap.primary);
                break;
            case 2:
                itemBg.setBackgroundResource(R.mipmap.intermediate);
                break;
            case 3:
                itemBg.setBackgroundResource(R.mipmap.multifunctional);
                break;
            case 4:
                itemBg.setBackgroundResource(R.mipmap.top);
                break;
            case 5:
                itemBg.setBackgroundResource(R.mipmap.super_bg);
                break;
            case 6:
                itemBg.setBackgroundResource(R.mipmap.intelligent);
                break;
        }
        helper.setText(R.id.robot_rclass, item.getName());
        helper.setText(R.id.robot_price, String.format(mContext.getResources().getString(R.string.UBI), item.getInput()));
        helper.setText(R.id.robot_mrcc, String.format(mContext.getResources().getString(R.string.UBI), item.getRet()));
        helper.setText(R.id.robot_period, String.format(mContext.getResources().getString(R.string.day), item.getPeriod()));
//        helper.setText(R.id.robot_num, String.format(mContext.getResources().getString(R.string.Pieces), item.getLimit()));
    }

    public void setTokens(List<RobotBean> tokens) {
        setNewData(tokens);
    }

    public interface ItemButtonClickListener {
        void setItem(RobotBean item);
    }
}
