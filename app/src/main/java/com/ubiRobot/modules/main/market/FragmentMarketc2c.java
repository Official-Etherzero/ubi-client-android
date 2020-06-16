package com.ubiRobot.modules.main.market;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.C2CListAdapter;
import com.ubiRobot.base.BaseFragment;
import com.ubiRobot.bean.C2COrderBean;
import com.ubiRobot.bean.LatestInterestBean;
import com.ubiRobot.modules.main.market.orderRecord.OrderRecordActivity;
import com.ubiRobot.modules.user.Login.LoginActivity;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;
import com.ubiRobot.view.InputPwdDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentMarketc2c extends BaseFragment<MarkerC2CView, MarketC2CPresenter> implements MarkerC2CView,
        SwipeRefreshLayout.OnRefreshListener {


    boolean isShow = false;
    @BindView(R.id.c2c_zjcj)
    TextView c2cZjcj;
    @BindView(R.id.c2c_zdj)
    TextView c2cZdj;
    @BindView(R.id.c2c_qgd)
    TextView c2cQgd;
    @BindView(R.id.c2c_qgzl)
    TextView c2cQgzl;
    @BindView(R.id.c2c_buy_count)
    EditText c2cBuyCount;
    @BindView(R.id.c2c_buy_price)
    EditText c2cBuyPrice;
    @BindView(R.id.c2c_order_list)
    RecyclerView c2cOrderList;
    @BindView(R.id.c2c_refreshlayout)
    SwipeRefreshLayout c2cRefreshlayout;
    Unbinder unbinder;

    private C2CListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private InputPwdDialog buyPwdDialog;
    private InputPwdDialog sellPwdDialog;
    private float buyZdj;
    private String orderID;//锁定出售ID
    private int page = 1;
    boolean isRefresh = false;
    boolean isFirst = true;

    @Override
    public MarketC2CPresenter initPresenter() {
        return new MarketC2CPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        c2cOrderList.setLayoutManager(linearLayoutManager);
        adapter = new C2CListAdapter(R.layout.c2c_list_item, new ArrayList<>());
        c2cOrderList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                C2COrderBean bean = (C2COrderBean) adapter.getItem(position);
                assert bean != null;
                orderID = bean.getOrderID();
                presenter.lockOrder(SharedPrefsUitls.getInstance().getUserToken(), orderID);
            }
        });
        MyLog.i("token=" + SharedPrefsUitls.getInstance().getUserToken());
        c2cRefreshlayout.setColorSchemeResources(
                R.color.zt_lu,
                R.color.zt_501a,
                R.color.zt_red
        );
    }

    @Override
    protected void initData() {
    }

    @Override
    public void initEvent() {
        c2cRefreshlayout.setOnRefreshListener(this);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                ++page;
                presenter.getOrders(SharedPrefsUitls.getInstance().getUserToken(), page + "", "50");
            }
        }, c2cOrderList);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.market_c2c_layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        String token = SharedPrefsUitls.getInstance().getUserToken();
        updataListView(token);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        isFirst = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser && isShow) {
            String token = SharedPrefsUitls.getInstance().getUserToken();
            updataListView(token);
        } else {
            MyLog.i("getOtcInfo==" + 2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.c2c_order_record, R.id.c2c_buy_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.c2c_order_record:
                startActivity(new Intent(getActivity(), OrderRecordActivity.class));
                break;
            case R.id.c2c_buy_btn:
                String count = c2cBuyCount.getText().toString().trim();
                String price = c2cBuyPrice.getText().toString().trim();
                if (Util.isNullOrEmpty(count)) {
                    ToastUtils.showLongToast(getActivity(), R.string.c2c_buycount_hint);
                    return;
                }
                if (Util.isNullOrEmpty(price)) {
                    ToastUtils.showLongToast(getActivity(), R.string.c2c_buyprice_hint);
                    return;

                }
                float buyp = Float.valueOf(price);
                //判断求购价是否在指导价正负10%之间
                if (buyp > (buyZdj * 1.1) || buyp < buyZdj * 0.9) {
                    ToastUtils.showLongToast(getActivity(), R.string.c2c_buyprice_hint1);
                    return;
                }

                buyPwdDialog = new InputPwdDialog(getActivity());
                buyPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        buyPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        presenter.createBuy(SharedPrefsUitls.getInstance().getUserToken(), count, price, pwd);

                    }
                });
                buyPwdDialog.show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        updataListView(SharedPrefsUitls.getInstance().getUserToken());
        isRefresh = true;
    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(getActivity(), msg);
        if (code == 401) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void createBuyFail(int code, String msg) {
        buyPwdDialog.dismiss();
        ToastUtils.showLongToast(getActivity(), msg);
        if (code == 401) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void setC2CInfo(LatestInterestBean c2CInfo) {
        if (c2CInfo != null) {
            buyZdj = Float.valueOf(Util.isNullOrEmpty(c2CInfo.getPrice()) ? "0" : c2CInfo.getPrice());
            c2cZjcj.setText(String.format(getString(R.string.c2c_zjcj), c2CInfo.getLastPrice()));
            c2cZdj.setText(String.format(getString(R.string.c2c_zdj), c2CInfo.getPrice()));
            c2cQgd.setText(String.format(getString(R.string.c2c_qgd), c2CInfo.getCount()));
            c2cQgzl.setText(String.format(getString(R.string.c2c_qgzl), c2CInfo.getSum()));
            c2cBuyPrice.setText(c2CInfo.getPrice());
        }

    }

    @Override
    public void orderLockSuccess(int code) {

        sellPwdDialog = new InputPwdDialog(getActivity());
        sellPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
            @Override
            public void onCancel() {
                presenter.unConfirmSale(SharedPrefsUitls.getInstance().getUserToken(), orderID);
                sellPwdDialog.dismiss();
            }

            @Override
            public void onConfirm(String pwd) {
                presenter.confirmSale(SharedPrefsUitls.getInstance().getUserToken(), orderID, pwd);
            }
        });
        sellPwdDialog.show();
    }

    @Override
    public void confirmSaleSuccess(int code) {
        sellPwdDialog.dismiss();
        ToastUtils.showLongToast(getActivity(), R.string.c2c_confirmSaleSuccess);
        updataListView(SharedPrefsUitls.getInstance().getUserToken());

    }

    @Override
    public void unConfirmSaleSuccess(int code) {

    }

    @Override
    public void createBuySuccess(int code) {
        buyPwdDialog.dismiss();
        c2cBuyCount.setText("");
        ToastUtils.showLongToast(getActivity(), R.string.c2c_createBuySuccess);
        updataListView(SharedPrefsUitls.getInstance().getUserToken());
    }

    @Override
    public void listOrders(List<C2COrderBean> list) {


        if (page == 1) {
            adapter.setTokens(list);
        } else {
            adapter.addData(list);
        }

        if (!isFirst && isRefresh) {
            ToastUtils.showLongToast(getActivity(), R.string.refresh_the_success);
        }
        if (list.size() < 50) adapter.loadMoreEnd();
        if (list.size() == 50) adapter.loadMoreComplete();
        isFirst = false;
        c2cRefreshlayout.setRefreshing(false);
    }

    private void updataListView(String token) {
        page = 1;
        presenter.getOrders(token, page + "", "50");
        presenter.getC2CIfo(token);
    }
}
