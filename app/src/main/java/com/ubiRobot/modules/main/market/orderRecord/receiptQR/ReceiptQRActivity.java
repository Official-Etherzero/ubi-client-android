package com.ubiRobot.modules.main.market.orderRecord.receiptQR;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ubiRobot.R;
import com.ubiRobot.app.UrlFactory;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.ReceiptQRBean;
import com.ubiRobot.modules.main.market.orderRecord.OrderRecordPresenter;
import com.ubiRobot.modules.main.market.orderRecord.OrderRecordView;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiptQRActivity extends BaseActivity<OrderRecordView, OrderRecordPresenter> implements OrderRecordView {


    @BindView(R.id.receipt_wechat)
    TextView receiptWechat;
    @BindView(R.id.receipt_alipay)
    TextView receiptAlipay;
    @BindView(R.id.receipt_qr)
    ImageView receiptQr;
    ReceiptQRBean qr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receipt_qr;
    }

    @Override
    public OrderRecordPresenter initPresenter() {
        return new OrderRecordPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.my_skm));
        String orderId = getIntent().getStringExtra("OrderId");
        MyLog.i("OrderId=" + orderId);
        presenter.getOrderQR(SharedPrefsUitls.getInstance().getUserToken(), orderId);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void requestFail(String msg) {

    }

    @Override
    public void getMyOrders(List<HangOrderBean> beans) {

    }

    @Override
    public void setCollectionCode(ReceiptQRBean qrBean) {

        MyLog.i("ReceiptQRBean="+qrBean.toString());
        if (qrBean != null && qrBean.getCount() != 0) {
            qr = qrBean;
            if (qrBean.getCount() == 1) {
                if (!Util.isNullOrEmpty(qrBean.getPic1())) {
                    receiptWechat.setTextColor(getResources().getColor(R.color.zt_wechat));
                    receiptAlipay.setVisibility(View.GONE);

                } else {
                    receiptAlipay.setTextColor(getResources().getColor(R.color.zt_alipay));
                    receiptWechat.setVisibility(View.GONE);
                }
            } else {
                receiptWechat.setTextColor(getResources().getColor(R.color.zt_wechat));
                receiptAlipay.setTextColor(getResources().getColor(R.color.zt_hui50));

            }
            Glide.with(activity)
                    .load(Util.isNullOrEmpty(qrBean.getPic1()) ? qrBean.getPic2() : qrBean.getPic1())
                    .into(receiptQr);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.receipt_wechat, R.id.receipt_alipay})
    public void onViewClicked(View view) {
        if (qr == null) return;
        switch (view.getId()) {
            case R.id.receipt_wechat:
                receiptWechat.setTextColor(getResources().getColor(R.color.zt_wechat));
                receiptAlipay.setTextColor(getResources().getColor(R.color.zt_hui50));
                Glide.with(activity)
                        .load(qr.getPic1())
                        .into(receiptQr);
                break;
            case R.id.receipt_alipay:
                receiptWechat.setTextColor(getResources().getColor(R.color.zt_hui50));
                receiptAlipay.setTextColor(getResources().getColor(R.color.zt_alipay));
                Glide.with(activity)
                        .load(qr.getPic2())
                        .into(receiptQr);
                break;
        }
    }
}
