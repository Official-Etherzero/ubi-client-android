package com.ubiRobot.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.baseAdapter.base.MyBaseViewHolder;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.base.Constants;
import com.ubiRobot.bean.Token;
import com.ubiRobot.bean.TokenInfo;
import com.ubiRobot.bean.TransactionRecords;
import com.ubiRobot.blockchain.BaseWalletManager;
import com.ubiRobot.modules.walletmanage.WalletsMaster;
import com.ubiRobot.utils.CurrencyUtils;
import com.ubiRobot.utils.DateUtil;
import com.ubiRobot.utils.ExchangeRateUtil;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.Util;

import java.math.BigDecimal;
import java.util.List;

public class TransactionsAdapter extends BaseQuickAdapter<TransactionRecords, MyBaseViewHolder> {

    private TokenInfo tokenInfo;

    public TransactionsAdapter(int layoutResId, @Nullable List<TransactionRecords> data, TokenInfo tokenInfo) {
        super(layoutResId, data);
        this.tokenInfo = tokenInfo;
    }

    @Override
    protected void convert(MyBaseViewHolder helper, TransactionRecords item) {
        String commentString = "";
        boolean received = item.isReceived;
        TextView status = helper.getView(R.id.transaction_status);
        if (Util.isNullOrEmpty(item.blockNumber)) {
            helper.setBackgroundRes(R.id.tx_status_icon, R.mipmap.asset_log_verify);
            status.setText(mContext.getString(R.string.TransactionDetails_confirming));
            status.setTextColor(mContext.getResources().getColor(R.color.zt_lab));
        } else if (item.status.equals("success")) {

            if (received) {
                helper.setBackgroundRes(R.id.tx_status_icon, R.mipmap.asset_log_zhuanru);
            } else {
                helper.setBackgroundRes(R.id.tx_status_icon, R.mipmap.asset_log_zhuanchu);
            }
            status.setText(mContext.getString(R.string.transfer_ok));
            status.setTextColor(mContext.getResources().getColor(R.color.zt_lab));

        } else {
            helper.setBackgroundRes(R.id.tx_status_icon, R.mipmap.asset_log_wrong);
            status.setText(mContext.getString(R.string.transfer_fail));
            status.setTextColor(mContext.getResources().getColor(R.color.zt_red));
        }

        BigDecimal cryptoAmount = new BigDecimal(item.value).abs();
        String formattedAmount = CurrencyUtils.getFormattedAmount(mContext, tokenInfo.symbol, cryptoAmount, Constants.MAX_DECIMAL_PLACES_FOR_UI);
        helper.setText(R.id.tx_amount, formattedAmount);
        helper.setText(R.id.tx_description, !commentString.isEmpty() ? commentString : (!received ? item.to : item.from));
        //if it's 0 we use the current time.
        long timeStamp = Long.valueOf(item.date) == 0 ? System.currentTimeMillis() : Long.valueOf(item.date) * DateUtils.SECOND_IN_MILLIS;

        String shortDate = DateUtil.getShortDate(timeStamp);

        helper.setText(R.id.tx_date, shortDate);


    }

    public void setTokens(List<TransactionRecords> tokens) {
        setNewData(tokens);
    }
}
