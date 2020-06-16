package com.ubiRobot.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.bean.AccountBean;
import com.ubiRobot.bean.WalletBean;
import com.ubiRobot.modules.walletsetting.WalletSetting;
import com.ubiRobot.utils.SharedPrefsUitls;

import java.util.List;

public class TransferTypeSelectAdapter extends BaseQuickAdapter<AccountBean, MyBaseViewHolder> {

    public TransferTypeSelectAdapter(int layoutResId, @Nullable List<AccountBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, AccountBean item) {
        helper.setText(R.id.string_name, item.getAccName());
    }

    public void setTokens(List<AccountBean> tokens) {
        setNewData(tokens);
    }
}
