package com.ubiRobot.bean;

public class LatestInterestBean {
   private String LastPrice;//"float,最后成交价",
   private String Count;//"integer,总数",
   private String Sum;//"float,总额"
   private String Price;//"float,指导价",

    public String getLastPrice() {
        return LastPrice;
    }

    public String getCount() {
        return Count;
    }

    public String getSum() {
        return Sum;
    }

    public String getPrice() {
        return Price;
    }
}
