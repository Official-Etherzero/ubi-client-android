package com.ubiRobot.bean;

import com.ubiRobot.utils.Util;

public class MiningIncomeBean {
    private String Last;
    private String Cumulative;
    private String ExchangeRate;

    public String getLast() {
        return Last;
    }

    public String getCumulative() {
        return Cumulative;
    }

    public String getExchangeRate() {
        return Util.isNullOrEmpty(ExchangeRate) ? "0" : ExchangeRate;
    }
}
