package com.ubiRobot.modules.walletoperation;

import com.ubiRobot.bean.WalletBean;
import com.ubiRobot.blockchain.walletutils.WalletUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Tiny 熊 @ Upchain.pro
 * WeiXin: xlbxiong
 */

public class FetchWalletInteract {


    public FetchWalletInteract() {
    }

    public Single<List<WalletBean>> fetch() {


        return Single.fromCallable(() -> {
            return WalletUtils.loadAll();
        }).observeOn(AndroidSchedulers.mainThread());

    }

    public Single<WalletBean> findDefault() {

        return Single.fromCallable(() -> {
            return WalletUtils.getCurrent();
        });

    }
}
