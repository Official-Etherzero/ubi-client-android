package com.ubiRobot.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.bean.TeamNodeBean;
import com.ubiRobot.utils.Util;

import java.util.List;

public class TeamNodeAdapter extends BaseQuickAdapter<TeamNodeBean, MyBaseViewHolder> {


    public TeamNodeAdapter(int layoutResId, @Nullable List<TeamNodeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, TeamNodeBean item) {
        ImageView team_calss = helper.getView(R.id.team_calss);
        int level = Integer.valueOf(item.getTeamLevel());
        if (level > 0) {
            team_calss.setVisibility(View.VISIBLE);
        } else {
            team_calss.setVisibility(View.GONE);
        }

        switch (level) {
            case 0:
                break;
            case 1:
                team_calss.setImageResource(R.mipmap.grade_1);
                break;
            case 2:
                team_calss.setImageResource(R.mipmap.grade_1);
                break;
            case 3:
                team_calss.setImageResource(R.mipmap.grade_1);
                break;
            case 4:
                team_calss.setImageResource(R.mipmap.grade_1);
                break;
        }
        helper.setText(R.id.team_acc, Util.isNullOrEmpty(item.getPhone()) ? item.getEmail() : item.getPhone());
        helper.setText(R.id.team_node_total, String.format(MyApp.getmInstance().getString(R.string.team_node_item_num), item.getTeamNodeCount()));


    }

    public void setTokens(List<TeamNodeBean> tokens) {
        setNewData(tokens);
    }

}
