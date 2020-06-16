package com.ubiRobot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ubiRobot.utils.Util;

public class MyRobotBean implements Parcelable {
    private String NodeID;//"integer,节点ID"
    private String OAmount;
    private String Amount;
    private String UserID;//"integer,用户ID",
    private String MiniID;//integer,节点等级ID",
    private String StartTime;
    private String ExpireTime;
    private String Name;
    private String RateOfProgress;
    private String RestOfDay;//"integer,剩余天数",
    private String Reward;//"integer,累计收益",
    private String RewardYesterday;// "integer,昨日收益"


    protected MyRobotBean(Parcel in) {
        NodeID = in.readString();
        OAmount = in.readString();
        Amount = in.readString();
        UserID = in.readString();
        MiniID = in.readString();
        StartTime = in.readString();
        ExpireTime = in.readString();
        Name = in.readString();
        RateOfProgress = in.readString();
    }

    public static final Creator<MyRobotBean> CREATOR = new Creator<MyRobotBean>() {
        @Override
        public MyRobotBean createFromParcel(Parcel in) {
            return new MyRobotBean(in);
        }

        @Override
        public MyRobotBean[] newArray(int size) {
            return new MyRobotBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NodeID);
        dest.writeString(OAmount);
        dest.writeString(Amount);
        dest.writeString(UserID);
        dest.writeString(MiniID);
        dest.writeString(StartTime);
        dest.writeString(ExpireTime);
        dest.writeString(Name);
        dest.writeString(RateOfProgress);
    }

    public String getOrderID() {
        return NodeID;
    }

    public String getOAmount() {
        return OAmount;
    }

    public String getAmount() {
        return Amount;
    }

    public String getUserID() {
        return UserID;
    }

    public int getMiniID() {
        return Integer.valueOf(Util.isNullOrEmpty(MiniID) ? "0" : MiniID);
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getExpireTime() {
        return ExpireTime;
    }

    public String getName() {
        return Name;
    }

    public String getRateOfProgress() {
        return RateOfProgress;
    }

    @Override
    public String toString() {
        return "MyRobotBean{" +
                "OrderID='" + NodeID + '\'' +
                ", OAmount='" + OAmount + '\'' +
                ", Amount='" + Amount + '\'' +
                ", UserID='" + UserID + '\'' +
                ", MiniID='" + MiniID + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", ExpireTime='" + ExpireTime + '\'' +
                ", Name='" + Name + '\'' +
                ", RateOfProgress='" + RateOfProgress + '\'' +
                '}';
    }
}
