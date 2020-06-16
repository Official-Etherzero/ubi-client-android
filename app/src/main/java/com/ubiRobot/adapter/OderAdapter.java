package com.ubiRobot.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.utils.MyLog;

import java.math.BigDecimal;
import java.util.List;

public class OderAdapter extends BaseQuickAdapter<HangOrderBean, MyBaseViewHolder> {


    private boolean isBuy;

    public OderAdapter(int layoutResId, @Nullable List<HangOrderBean> data, boolean isBuy) {
        super(layoutResId, data);
        this.isBuy = isBuy;
    }

    @Override
    protected void convert(MyBaseViewHolder helper, HangOrderBean item) {

        helper.setText(R.id.deal_price, item.getPrice());

        BigDecimal price = new BigDecimal(item.getPrice());

        TextView tv = helper.getView(R.id.deal_total);
        TextView btn = helper.getView(R.id.market_buy_btn);
        if (!isBuy) {
            tv.setTextColor(mContext.getResources().getColor(R.color.zt_red));
            btn.setBackgroundResource(R.drawable.sell_btn_bg);
            btn.setText(mContext.getResources().getString(R.string.robot_buy1));
        } else {
            tv.setTextColor(mContext.getResources().getColor(R.color.zt_buy));
            btn.setBackgroundResource(R.drawable.buy_btn_bg);
            btn.setText(mContext.getResources().getString(R.string.market_sell));
        }

    }

    public void setTokens(List<HangOrderBean> tokens) {
        setNewData(tokens);
    }
}
