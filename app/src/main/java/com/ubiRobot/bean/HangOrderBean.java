package com.ubiRobot.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HangOrderBean implements Parcelable {

    private String OrderID;
    private String Count;
    private String Price;
    private String WriteTime;
    private String LockTime;
    private String Status; //integer,0新建的订单，1表示锁定（等待买家确认），2表示锁定（买家确认，等待付款），3表示买家已经转钱等待卖家确认，4表示交易完成，5表示交易不成功没收到钱，6表示已取消'"
    private String Direction;//"string,1是卖单，2是买单"
    private String PayType;//"string,1是卖单，2是买单"
    private String Phone;//卖家电话
    private String Countdown;//开始锁定时间戳


    protected HangOrderBean(Parcel in) {
        OrderID = in.readString();
        Count = in.readString();
        Price = in.readString();
        WriteTime = in.readString();
        LockTime = in.readString();
        Status = in.readString();
        Direction = in.readString();
        PayType = in.readString();
        Phone = in.readString();
        Countdown = in.readString();
    }

    public static final Creator<HangOrderBean> CREATOR = new Creator<HangOrderBean>() {
        @Override
        public HangOrderBean createFromParcel(Parcel in) {
            return new HangOrderBean(in);
        }

        @Override
        public HangOrderBean[] newArray(int size) {
            return new HangOrderBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(OrderID);
        dest.writeString(Count);
        dest.writeString(Price);
        dest.writeString(WriteTime);
        dest.writeString(LockTime);
        dest.writeString(Status);
        dest.writeString(Direction);
        dest.writeString(PayType);
        dest.writeString(Phone);
        dest.writeString(Countdown);
    }

    @Override
    public String toString() {
        return "HangOrderBean{" +
                "OrderID='" + OrderID + '\'' +
                ", Count='" + Count + '\'' +
                ", Price='" + Price + '\'' +
                ", WriteTime='" + WriteTime + '\'' +
                ", LockTime='" + LockTime + '\'' +
                ", Status='" + Status + '\'' +
                ", Direction='" + Direction + '\'' +
                ", PayType='" + PayType + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Countdown='" + Countdown + '\'' +
                '}';
    }

    public String getOrderID() {
        return OrderID;
    }

    public String getCount() {
        return Count;
    }

    public String getPrice() {
        return Price;
    }

    public String getWriteTime() {
        return WriteTime;
    }

    public String getLockTime() {
        return LockTime;
    }

    public String getStatus() {
        return Status;
    }

    public String getDirection() {
        return Direction;
    }

    public String getPayType() {
        return PayType;
    }

    public String getPhone() {
        return Phone;
    }

    public String getCountdown() {
        return Countdown;
    }
}
