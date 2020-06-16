package com.ubiRobot.bean;

import java.io.Serializable;

public class BaseRobotBean<T> implements Serializable {

    public int code;
    public String msg;
    public T data = null;

    @Override
    public String toString() {
        return "BaseRobotBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}