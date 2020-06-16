package com.ubiRobot.base;

import com.ubiRobot.bean.WalletBean;
import com.ubiRobot.sqlite.WalletDataStore;
import com.ubiRobot.utils.SharedPrefsUitls;

public abstract class BasePresent<T>{
    public T view;

    public void attach(T view){
        this.view = view;
    }

    public void detach(){
        this.view = null;
    }

    public WalletBean getCurrentWallet() {
        String wid = SharedPrefsUitls.getInstance().getCurrentWallet();
        return WalletDataStore.getInstance().queryWallet(wid);
    }
}