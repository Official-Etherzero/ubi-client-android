package com.ubiRobot.modules.main.market.orderRecord;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.ReceiptQRBean;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderRecordPresenter extends BasePresent<OrderRecordView> {

    public void myrders(String Access_Token, String type, String page, String pagesize) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("Type", type);
        hashMap.put("page", page);
        hashMap.put("pagesize", pagesize);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().myOrders(object.toString(), new DataSource.DataCallback<List<HangOrderBean>>() {
            @Override
            public void onDataLoaded(List<HangOrderBean> obj) {
                view.getMyOrders(obj == null ? new ArrayList<HangOrderBean>() : obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }

    public void getOrderQR(String Access_Token, String OrderID) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("OrderID", OrderID);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().getOrderQRCode(object.toString(), new DataSource.DataCallback<ReceiptQRBean>() {
            @Override
            public void onDataLoaded(ReceiptQRBean obj) {
                view.setCollectionCode(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(toastMessage);
            }
        });
    }


}
