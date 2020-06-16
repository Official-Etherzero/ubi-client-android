package com.ubiRobot.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.utils.Util;

import java.math.BigDecimal;
import java.util.List;

public class MyOrderRecordAdapter extends BaseQuickAdapter<HangOrderBean, MyBaseViewHolder> {

    private int stype;

    public MyOrderRecordAdapter(int layoutResId, @Nullable List<HangOrderBean> data, int stype) {
        super(layoutResId, data);
        this.stype = stype;
    }

    @Override
    protected void convert(MyBaseViewHolder helper, HangOrderBean item) {
        TextView name = helper.getView(R.id.order_iso);
        if (item.getDirection().equals("1")) {
            name.setText(mContext.getResources().getString(R.string.c2c_buy) + item.getOrderID());
            name.setTextColor(mContext.getResources().getColor(R.color.zt_buy));
        } else {
            name.setText(mContext.getResources().getString(R.string.c2c_sell) + item.getOrderID());
            name.setTextColor(mContext.getResources().getColor(R.color.zt_red));
        }
        helper.setText(R.id.order_date, item.getWriteTime());
        helper.setText(R.id.order_item_price, item.getPrice());
        helper.setText(R.id.order_item_count, item.getCount());
        float total = Float.valueOf(Util.isNullOrEmpty(item.getPrice()) ? "0" : item.getPrice())
                * Float.valueOf(Util.isNullOrEmpty(item.getCount()) ? "0" : item.getCount());
        helper.setText(R.id.order_item_total, total + "");


        TextView status = helper.getView(R.id.order_status);
        if (stype == 1) {
            int sta = Integer.valueOf(item.getStatus());
            String str;
            switch (sta) {
                case 0:
                    str = mContext.getResources().getString(R.string.under_way);
                    status.setTextColor(mContext.getResources().getColor(R.color.zt_fff));
                    break;
                case 1:
                    str = mContext.getResources().getString(R.string.Unconfirmed);
                    status.setTextColor(mContext.getResources().getColor(R.color.zt_lab));
                    break;
                case 2:
                    str = mContext.getResources().getString(R.string.obligation);
                    status.setTextColor(mContext.getResources().getColor(R.color.zt_buy));
                    break;
                case 3:
                    str = mContext.getResources().getString(R.string.payment_has_been);
                    status.setTextColor(mContext.getResources().getColor(R.color.zt_lu));
                    break;
                case 5:
                    str = mContext.getResources().getString(R.string.abnormal_payment);
                    status.setTextColor(mContext.getResources().getColor(R.color.zt_red));
                    break;
                default:
                    str = mContext.getResources().getString(R.string.abnormal_payment);
                    status.setTextColor(mContext.getResources().getColor(R.color.zt_red));
                    break;
            }
            status.setText(str);
        } else if (stype == 2) {
            status.setText(R.string.finish);
        } else {
            status.setText(R.string.canceled);
        }
    }

    public void setTokens(List<HangOrderBean> tokens) {
        setNewData(tokens);
    }
}
