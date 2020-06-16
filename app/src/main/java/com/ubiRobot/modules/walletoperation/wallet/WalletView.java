package com.ubiRobot.modules.walletoperation.wallet;

import com.ubiRobot.base.BaseView;
import com.ubiRobot.bean.TransactionRecords;

import java.util.List;

public interface WalletView extends BaseView {

    void walletTransactions(List<TransactionRecords> list);
    void showError(String err);

}
