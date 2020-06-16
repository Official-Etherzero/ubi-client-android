package com.ubiRobot.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.bean.Token;
import com.ubiRobot.modules.walletmanage.WalletsMaster;
import com.ubiRobot.utils.CurrencyUtils;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class PropertyAdapter extends BaseQuickAdapter<Token, MyBaseViewHolder> {

    public PropertyAdapter(int layoutResId, @Nullable List<Token> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyBaseViewHolder helper, Token item) {


        String iso = item.tokenInfo.symbol;
        if (Util.isNullOrEmpty(iso)) return;
        MyLog.i("PropertyAdapter==" + SharedPrefsUitls.getInstance().getPreferredFiatIso());
        MyLog.i("getPreferredFiatIso==" + Locale.getDefault().getLanguage());
        String fiatBalance = CurrencyUtils.getFormattedAmount(mContext, SharedPrefsUitls.getInstance().getPreferredFiatIso(),
                WalletsMaster.getInstance().getWalletByIso(MyApp.getBreadContext(), iso).getFiatBalance(iso, item.balance));
        String cryptoBalance = CurrencyUtils.getFormattedAmount(mContext, iso, new BigDecimal(Util.isNullOrEmpty(item.balance) ? "0" : item.balance));
        helper.setText(R.id.wallet_trade_price, item.tokenInfo.name);
        helper.setText(R.id.wallet_name, iso);
        helper.setText(R.id.wallet_balance_fiat, fiatBalance);
        helper.setText(R.id.wallet_balance_currency, cryptoBalance);
        ImageView im = helper.getView(R.id.wallet_icon);
        im.setBackgroundResource(R.mipmap.ubi);
    }

    public void setTokens(List<Token> tokens) {
        setNewData(tokens);
    }
}
