package com.ubiRobot.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OtcInfoBean implements Parcelable {
    private String Price;
    private String IsActive;
    private String USDTSysAddr;//"string,系统分配的USDT地址，用于激活",
    private String USDTReceAddr;//"string,用于接收别人转账的USDT地址"

    protected OtcInfoBean(Parcel in) {
        Price = in.readString();
        IsActive = in.readString();
        USDTSysAddr = in.readString();
        USDTReceAddr = in.readString();
    }

    public static final Creator<OtcInfoBean> CREATOR = new Creator<OtcInfoBean>() {
        @Override
        public OtcInfoBean createFromParcel(Parcel in) {
            return new OtcInfoBean(in);
        }

        @Override
        public OtcInfoBean[] newArray(int size) {
            return new OtcInfoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Price);
        dest.writeString(IsActive);
        dest.writeString(USDTSysAddr);
        dest.writeString(USDTReceAddr);
    }

    public String getPrice() {
        return Price;
    }

    public String getIsActive() {
        return IsActive;
    }

    public String getUSDTSysAddr() {
        return USDTSysAddr;
    }

    public String getUSDTReceAddr() {
        return USDTReceAddr;
    }
}
