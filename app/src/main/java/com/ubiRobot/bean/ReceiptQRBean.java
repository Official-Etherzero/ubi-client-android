package com.ubiRobot.bean;

import com.ubiRobot.app.UrlFactory;
import com.ubiRobot.utils.Util;

public class ReceiptQRBean {
    private String pic1;
    private String pic2;

    public String getPic1() {
        return Util.isNullOrEmpty(pic1) ? pic1 : UrlFactory.host1 + pic1;
    }

    public String getPic2() {
        return Util.isNullOrEmpty(pic2) ? pic2 : UrlFactory.host1 + pic2;
    }

    public int getCount() {
        int count = 0;
        if (!Util.isNullOrEmpty(pic1)) ++count;
        if (!Util.isNullOrEmpty(pic2)) ++count;
        return count;
    }

    @Override
    public String toString() {
        return "ReceiptQRBean{" +
                "pic1='" + pic1 + '\'' +
                ", pic2='" + pic2 + '\'' +
                '}';
    }
}
