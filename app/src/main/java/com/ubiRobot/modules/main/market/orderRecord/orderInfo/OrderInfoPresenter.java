package com.ubiRobot.modules.main.market.orderRecord.orderInfo;

import com.ubiRobot.base.BasePresent;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;

import org.json.JSONObject;

import java.util.HashMap;

public class OrderInfoPresenter extends BasePresent<OrderInfoView> {


    public void cancelBuyOrder(String Access_Token, String orderId) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("OrderID", orderId);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().cancelBuy(object.toString(), new DataSource.DataCallback<String>() {
            @Override
            public void onDataLoaded(String obj) {
                view.cancelBuyOrderBack(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code, toastMessage);
            }
        });
    }


    //出售者确认收到钱
    public void sellConfirm(String pwd, String Access_Token, String orderId, String status) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Passwd", pwd);
        hashMap.put("Access_Token", Access_Token);
        hashMap.put("OrderID", orderId);
        hashMap.put("Status", status);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().confirmSuccess(object.toString() ,new DataSource.DataCallback<String>() {
            @Override
            public void onDataLoaded(String obj) {
                view.sellConfirmSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code,toastMessage);
            }
        });
    }

    /**
     * 创建者处理买单
     *
     * @param token
     * @param orderId
     * @param payType
     */
    public void confirmPaid(String token, String orderId, String payType) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Access_Token", token);
        hashMap.put("OrderID", orderId);
        hashMap.put("PayType", payType);
        JSONObject object = new JSONObject(hashMap);
        RemoteDataSource.getInstance().confirmPaid(object.toString(), new DataSource.DataCallback<String>() {
            @Override
            public void onDataLoaded(String obj) {
                view.paidSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.requestFail(code,toastMessage);
            }
        });
    }

}
