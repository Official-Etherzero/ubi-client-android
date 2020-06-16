package com.ubiRobot.data;


import com.lzy.okgo.model.Response;
import com.ubiRobot.app.UrlFactory;
import com.ubiRobot.bean.AuthenticationBean;
import com.ubiRobot.bean.BaseRobotBean;
import com.ubiRobot.bean.C2COrderBean;
import com.ubiRobot.bean.DailyEarningsListBean;
import com.ubiRobot.bean.GoogleSecretBean;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.LatestInterestBean;
import com.ubiRobot.bean.MiningHashrateBean;
import com.ubiRobot.bean.MiningIncomeBean;
import com.ubiRobot.bean.MyRobotBean;
import com.ubiRobot.bean.MyRobotDataBean;
import com.ubiRobot.bean.NodeRevenueBean;
import com.ubiRobot.bean.NodeRevenueDataBean;
import com.ubiRobot.bean.OtcInfoBean;
import com.ubiRobot.bean.ReceiptQRBean;
import com.ubiRobot.bean.RobotBean;
import com.ubiRobot.bean.TeamNodeDataBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.bean.UserGoogleBean;
import com.ubiRobot.http.HttpRequets;
import com.ubiRobot.http.callback.JsonCallback;
import com.ubiRobot.utils.MyLog;

import java.util.List;

/**
 * Created by Administrator on
 */
public class RemoteDataSource implements DataSource {
    private static RemoteDataSource INSTANCE;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    private RemoteDataSource() {

    }

    public void loadDayK(int count, DataCallback loadDataCallback) {

    }


    @Override
    public void getPhoneCode(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getPhoneCodeURL(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                MyLog.d("code=" + response.body().code + "msg=" + response.body().msg);
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void phoneRegister(String json, DataCallback<UserBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getPhoneRegisterURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<UserBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<UserBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void phoneLogin(String json, DataCallback<UserBean> dataCallback) {
        HttpRequets.getRequest(UrlFactory.getLoginPhoneURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<UserBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<UserBean>> response) {
                MyLog.i("-------------" + response.body().code);
                MyLog.i("-------------" + response.body().msg);
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void retsetPWDbyPhone(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getRetsetPWDbyPhoneURL(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getUserInfo(String json, DataCallback<UserBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getUserInfoURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<UserBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<UserBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getAliDescribeVerifyToken(String json, DataCallback<AuthenticationBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getAliDescribeVerifyTokenURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<AuthenticationBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<AuthenticationBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });

    }

    @Override
    public void getAliDescribeVerifyResult(String json, DataCallback<AuthenticationBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getAliDescribeVerifyResultURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<AuthenticationBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<AuthenticationBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getMiningList(String json, DataCallback<List<RobotBean>> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getNodeListURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<List<RobotBean>>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<List<RobotBean>>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                }
            }
        });
    }


    @Override
    public void getUserMiningList(String json, DataCallback<List<MyRobotBean>> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getUserMiningList(), INSTANCE, json, new JsonCallback<BaseRobotBean<MyRobotDataBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<MyRobotDataBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data.getUserNodeList());
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }

            }
        });

    }


    @Override
    public void miningBuy(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.miningBuy(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(0);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void buyFreeNode(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getBuyFreeNodeURL(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(0);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getMiningEarningList(String json, DataCallback<DailyEarningsListBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getMiningEarningList(), INSTANCE, json, new JsonCallback<BaseRobotBean<DailyEarningsListBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<DailyEarningsListBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void transfe(String json, DataCallback<String> dataCallback) {
        HttpRequets.getRequest(UrlFactory.getTransferUrl(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().msg);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getTeamNodeList(String json, DataCallback<TeamNodeDataBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getTeamNodeListURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<TeamNodeDataBean>>() {

            @Override
            public void onSuccess(Response<BaseRobotBean<TeamNodeDataBean>> response) {
                MyLog.i("==================" + response.body().data.toString());
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }

        });
    }

    @Override
    public void getDetailedList(String json, DataCallback<List<NodeRevenueBean>> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getDetailedListURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<NodeRevenueDataBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<NodeRevenueDataBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data.getUserNodeList());
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });

    }

    @Override
    public void getC2CInfo(String json, DataCallback<LatestInterestBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getC2CInfoURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<LatestInterestBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<LatestInterestBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void orderLock(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getOrderLockURL(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(0);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void confirmSale(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getConfirmSaleURL(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(0);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void unConfirmSale(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getUnConfirmSaleURL(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(0);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void createBuy(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getCreateBuyURL(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(0);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getListOrders(String json, DataCallback<List<C2COrderBean>> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getListOrdersURL(), INSTANCE, json, new JsonCallback<BaseRobotBean<List<C2COrderBean>>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<List<C2COrderBean>>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void myOrders(String json, DataCallback<List<HangOrderBean>> dataCallback) {
        HttpRequets.postRequest(UrlFactory.myOrders(), INSTANCE, json, new JsonCallback<BaseRobotBean<List<HangOrderBean>>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<List<HangOrderBean>>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void cancelBuy(String json, DataCallback<String> dataCallback) {
        HttpRequets.postRequest(UrlFactory.cancelBuyUrl(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().msg);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void confirmSuccess(String json, DataCallback<String> dataCallback) {
        HttpRequets.postRequest(UrlFactory.confirmSuccessUrl(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().msg);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void confirmPaid(String json, DataCallback<String> dataCallback) {
        HttpRequets.postRequest(UrlFactory.confirmPaidUrl(), INSTANCE, json, new JsonCallback<BaseRobotBean>() {
            @Override
            public void onSuccess(Response<BaseRobotBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().msg);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getOrderQRCode(String json, DataCallback<ReceiptQRBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getOrderQRCode(), INSTANCE, json, new JsonCallback<BaseRobotBean<ReceiptQRBean>>() {
            @Override
            public void onSuccess(Response<BaseRobotBean<ReceiptQRBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }


    @Override
    public void getDepositList(String token, DataCallback dataCallback) {

    }

    @Override
    public void commitSellerApply(String token, String coinId, String json, DataCallback dataCallback) {

    }


}
