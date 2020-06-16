package com.ubiRobot.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.bean.DailyEarningsBean;
import com.ubiRobot.bean.RobotBean;

import java.util.List;

public class RobotEarningsAdapter extends BaseQuickAdapter<DailyEarningsBean, MyBaseViewHolder> {


    public RobotEarningsAdapter(int layoutResId, @Nullable List<DailyEarningsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, DailyEarningsBean item) {
        helper.setText(R.id.earvings_date, item.getRetDate());
        helper.setText(R.id.earvings_ubi, String.format(mContext.getResources().getString(R.string.UBI), item.getRet()));

    }

    public void setTokens(List<DailyEarningsBean> tokens) {
        setNewData(tokens);
    }

}
