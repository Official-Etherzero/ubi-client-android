package com.ubiRobot.modules.dapp;

import com.ubiRobot.base.BaseView;

public interface DappTransactionView extends BaseView{

    void onGasEstimate(String gas);
    void onGasPrice(String price);
    void onError(String err);
}
