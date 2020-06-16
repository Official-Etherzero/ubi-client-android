package com.ubiRobot.modules.walletmanage.createwallet;

import com.ubiRobot.base.BaseView;

public interface CreateWalletView extends BaseView {


    void getWalletSuccess(String address);
    void getWalletFail(String msg);


}
