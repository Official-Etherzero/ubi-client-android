package com.ubiRobot.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.bean.C2COrderBean;
import com.ubiRobot.bean.HangOrderBean;

import java.math.BigDecimal;
import java.util.List;

public class C2CListAdapter extends BaseQuickAdapter<C2COrderBean, MyBaseViewHolder> {



    public C2CListAdapter(int layoutResId, @Nullable List<C2COrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, C2COrderBean item) {
        helper.setText(R.id.item_order_id,item.getOrderID());
        helper.setText(R.id.item_order_count,item.getCount());
        helper.setText(R.id.item_order_price,item.getPrice());
    }

    public void setTokens(List<C2COrderBean> tokens) {
        setNewData(tokens);
    }
}
