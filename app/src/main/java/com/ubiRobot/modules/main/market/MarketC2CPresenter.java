package com.ubiRobot.modules.main.market;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.C2COrderBean;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.LatestInterestBean;
import com.ubiRobot.bean.OtcInfoBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.utils.Md5Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarketC2CPresenter extends BasePresent<MarkerC2CView> {

    public void getOrders(String Access_Token, String page, String pagesize) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("page", page);
        hashMap.put("pagesize", pagesize);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getListOrders(object.toString(), new DataSource.DataCallback<List<C2COrderBean>>() {
            @Override
            public void onDataLoaded(List<C2COrderBean> list) {
                view.listOrders(list);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code,toastMessage);
            }
        });
    }

    public void lockOrder(String Access_Token, String OrderID) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("OrderID", OrderID);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().orderLock(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer code) {
                view.orderLockSuccess(code);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });
    }

    public void getC2CIfo(String Access_Token) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getC2CInfo(object.toString(), new DataSource.DataCallback<LatestInterestBean>() {
            @Override
            public void onDataLoaded(LatestInterestBean obj) {
                view.setC2CInfo(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });
    }

    public void confirmSale(String Access_Token, String OrderID,String Passwd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("OrderID", OrderID);
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().confirmSale(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer code) {
                view.confirmSaleSuccess(code);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });
    }

    public void unConfirmSale(String Access_Token, String OrderID) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("OrderID", OrderID);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().unConfirmSale(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer code) {
                view.unConfirmSaleSuccess(code);
            }
            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code,toastMessage);
            }
        });
    }

    public void createBuy(String Access_Token, String Count,String Price,String Passwd) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("Count", Count);
        hashMap.put("Price", Price);
        hashMap.put("Passwd", Md5Utils.md5(Passwd));
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().createBuy(object.toString(), new DataSource.DataCallback<Integer>() {
            @Override
            public void onDataLoaded(Integer code) {
                view.createBuySuccess(code);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.createBuyFail(code,toastMessage);
            }
        });
    }
}
