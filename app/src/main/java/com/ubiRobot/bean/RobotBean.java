package com.ubiRobot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ubiRobot.utils.Util;

public class RobotBean implements Parcelable{
    private String MiniID;//"integer,矿机ID",
    private String MType;//"integer,矿机类型 1-初级，2-中级，3-高级， 4-顶级，5-特级，6-智能",
    private String Name;//"string,矿机名称",
    private String Type;//"integer,目前固定为1，表示矿机，方便后面扩展",
    private String Input;//"integer,投入",
    private String Earnings;//"integer,收益",
    private String SuanLi;//"integer,算力",
    private String EveryDay;//"double,每日产出",
    private String Period;// "integer,周期",
    private String Remark;// "string,备注"
    private String Limit;//"integer,今日剩余",


    protected RobotBean(Parcel in) {
        MiniID = in.readString();
        MType = in.readString();
        Name = in.readString();
        Type = in.readString();
        Input = in.readString();
        Earnings = in.readString();
        SuanLi = in.readString();
        EveryDay = in.readString();
        Period = in.readString();
        Remark = in.readString();
        Limit = in.readString();
    }

    public static final Creator<RobotBean> CREATOR = new Creator<RobotBean>() {
        @Override
        public RobotBean createFromParcel(Parcel in) {
            return new RobotBean(in);
        }

        @Override
        public RobotBean[] newArray(int size) {
            return new RobotBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MiniID);
        dest.writeString(MType);
        dest.writeString(Name);
        dest.writeString(Type);
        dest.writeString(Input);
        dest.writeString(Earnings);
        dest.writeString(SuanLi);
        dest.writeString(EveryDay);
        dest.writeString(Period);
        dest.writeString(Remark);
        dest.writeString(Limit);
    }

    public String getMiniID() {
        return MiniID;
    }

    public int getMType() {
        return  Integer.valueOf(Util.isNullOrEmpty(MType)?"0":MType);
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getInput() {
        return Input;
    }

    public String getEarnings() {
        return Earnings;
    }

    public String getSuanLi() {
        return SuanLi;
    }

    public String getRet() {
        return EveryDay;
    }

    public String getPeriod() {
        return Period;
    }

    public String getRemark() {
        return Remark;
    }

    public String getLimit() {
        return Limit;
    }
}
