package com.ubiRobot.adapter;

import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.view.RingProgressView;

import java.util.List;

public class MyRobotListAdapter extends BaseQuickAdapter<MyRobotBean, MyBaseViewHolder> {


    public MyRobotListAdapter(int layoutResId, @Nullable List<MyRobotBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, MyRobotBean item) {
        RelativeLayout itemBg= helper.getView(R.id.robot_item_bg);
        switch (item.getMiniID()){
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


        helper.setText(R.id.robot_item_class, item.getName());
        helper.setText(R.id.robot_item_dateopen, item.getStartTime());
        helper.setText(R.id.robot_item_dateclose, item.getExpireTime());
        RingProgressView wpv = helper.getView(R.id.robot_item_wpv);
        String progerss = item.getRateOfProgress();
        progerss = progerss.substring(0, progerss.indexOf("%"));
        wpv.setCurrentProgress(Integer.valueOf(progerss));
    }
    public void setTokens(List<MyRobotBean> tokens) {
        setNewData(tokens);
    }
}
