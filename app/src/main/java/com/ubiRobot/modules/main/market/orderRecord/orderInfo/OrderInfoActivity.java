package com.ubiRobot.modules.main.market.orderRecord.orderInfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.modules.main.market.orderRecord.receiptQR.ReceiptQRActivity;
import com.ubiRobot.modules.user.Login.LoginActivity;
import com.ubiRobot.modules.user.rigest.RegisterActivity;
import com.ubiRobot.utils.Md5Utils;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;
import com.ubiRobot.view.InputPwdDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 处理创建的订单及完成取消订单显示
 */

public class OrderInfoActivity extends BaseActivity<OrderInfoView, OrderInfoPresenter> implements OrderInfoView {


    @BindView(R.id.oder_status)
    TextView oderStatus;
    @BindView(R.id.trans_amount)
    TextView transAmount;
    @BindView(R.id.tran_price)
    TextView tranPrice;
    @BindView(R.id.tran_count)
    TextView tranCount;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.cancel_order)
    TextView cancelOrder;
    @BindView(R.id.have_paid)
    TextView havePaid;
    @BindView(R.id.confirm_success)
    TextView confirm_success;
    @BindView(R.id.order_payType)
    TextView order_payType;
    @BindView(R.id.order_skm)
    TextView order_skm;
    @BindView(R.id.rg_paid)
    RadioButton rgPaid;
    @BindView(R.id.rg_unpaid)
    RadioButton rgUnpaid;
    @BindView(R.id.pay_rdaiogroup)
    RadioGroup payRdaiogroup;
    @BindView(R.id.pay_ll)
    LinearLayout payLl;
    @BindView(R.id.rg_alipay)
    RadioButton rg_alipay;
    @BindView(R.id.rg_wechat)
    RadioButton rg_wechat;
    @BindView(R.id.payment_method_rg)
    RadioGroup paymentMethodRg;
    @BindView(R.id.payment_method_ll)
    LinearLayout paymentMethodLl;
    @BindView(R.id.order_djs)
    TextView orderDjs;
    @BindView(R.id.seller_phone)
    TextView sellerPhone;
    @BindView(R.id.seller_phone_ll)
    LinearLayout sellerPhoneLl;


    private HangOrderBean bean;
    private InputPwdDialog inputPwdDialog;
    private int paid = -1;//1支付成功，2支付失败
    private int paidType = -1;//2支付宝，1微信
    public TimeCount time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy;
    }

    @Override
    public OrderInfoPresenter initPresenter() {
        return new OrderInfoPresenter();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        bean = intent.getParcelableExtra("HangOrderBean");
        setCenterTitle(getString(R.string.robot_order_details));

        MyLog.i("bean=" + bean.toString());
        int direction = Integer.valueOf(bean.getDirection());

        int sta = Integer.valueOf(bean.getStatus());
        String str;
        switch (sta) {
            case 0:
                str = getResources().getString(R.string.under_way);
                break;
            case 1:
                str = getResources().getString(R.string.Unconfirmed);
                break;
            case 2:
                str = getResources().getString(R.string.obligation);
                break;
            case 3:
                str = getResources().getString(R.string.payment_has_been);
                break;
            case 4:
                str = getResources().getString(R.string.finish);
                break;
            case 5:
                str = getResources().getString(R.string.abnormal_payment);
                break;
            default:
                str = getResources().getString(R.string.abnormal_payment);
                break;
        }
        oderStatus.setText(str);
        if (sta == 4) {
            cancelOrder.setVisibility(View.GONE);
            havePaid.setVisibility(View.GONE);
            confirm_success.setVisibility(View.GONE);
            payLl.setVisibility(View.GONE);
            paymentMethodLl.setVisibility(View.GONE);
            order_skm.setVisibility(View.GONE);
        } else if (direction == 2) {
            cancelOrder.setVisibility(View.GONE);
            havePaid.setVisibility(View.GONE);
            confirm_success.setVisibility(View.VISIBLE);
            payLl.setVisibility(View.VISIBLE);
            paymentMethodLl.setVisibility(View.GONE);
            order_skm.setVisibility(View.GONE);
        } else {
            if (sta == 0) {
                cancelOrder.setVisibility(View.VISIBLE);
                havePaid.setVisibility(View.GONE);
                paymentMethodLl.setVisibility(View.GONE);
                order_skm.setVisibility(View.GONE);
            } else {
                if (sta == 3) {
                    sellerPhoneLl.setVisibility(View.VISIBLE);
                    havePaid.setVisibility(View.GONE);
                    paymentMethodLl.setVisibility(View.GONE);
                } else {
                    havePaid.setVisibility(View.VISIBLE);
                    paymentMethodLl.setVisibility(View.VISIBLE);
                    sellerPhoneLl.setVisibility(View.GONE);
                }
                cancelOrder.setVisibility(View.GONE);
                order_skm.setVisibility(View.VISIBLE);

            }
            confirm_success.setVisibility(View.GONE);
            payLl.setVisibility(View.GONE);


        }
        //锁定时间
        long lTime = Long.valueOf(Util.isNullOrEmpty(bean.getCountdown()) ? "0" : bean.getCountdown());
        //当前时间
        long cTime = System.currentTimeMillis();
        MyLog.i("cTime="+cTime);
        if (lTime != 0 && sta == 2 && (cTime - lTime) < 60 * 60 * 1000 * 2) {//倒计时=2小时-（当前时间-锁定时间）
            MyLog.i("cTime=="+(60 * 60 * 1000 * 2 - (cTime - lTime)));
            time = new TimeCount(60 * 60 * 1000 * 2 - (cTime - lTime), 1000);
            time.start();// 开始计时
        }

        float total = Float.valueOf(Util.isNullOrEmpty(bean.getPrice()) ? "0" : bean.getPrice())
                * Float.valueOf(Util.isNullOrEmpty(bean.getCount()) ? "0" : bean.getCount());
        transAmount.setText("￥" + total);
        tranPrice.setText("￥" + bean.getPrice());
        tranCount.setText(String.format(getString(R.string.UBI), bean.getCount()));
        orderNumber.setText(bean.getOrderID());
        int ptype = Integer.valueOf(Util.isNullOrEmpty(bean.getPayType()) ? "0" : bean.getPayType());
        if (ptype == 0) {
            order_payType.setVisibility(View.GONE);
        } else {
            order_payType.setVisibility(View.VISIBLE);
            if (ptype == 1) {
                order_payType.setText(getString(R.string.payment_method) + getString(R.string.payment_method_wechat));
                order_payType.setTextColor(getResources().getColor(R.color.zt_wechat));
            } else {
                order_payType.setText(getString(R.string.payment_method) + getString(R.string.payment_method_alipay));
                order_payType.setTextColor(getResources().getColor(R.color.zt_alipay));
            }
        }
        sellerPhone.setText(bean.getPhone());

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (time != null)
            time.cancel();
    }

    @Override
    public void initEvent() {
        payRdaiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rg_paid:
                        paid = 1;
                        break;
                    case R.id.rg_unpaid:
                        paid = 2;
                        break;
                }
            }
        });
        paymentMethodRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rg_alipay:
                        paidType = 2;
                        break;
                    case R.id.rg_wechat:
                        paidType = 1;
                        break;
                }
            }
        });

    }

    @Override
    public void requestFail(int code, String msg) {

        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void cancelBuyOrderBack(String msg) {
        finish();
    }

    @Override
    public void paidSuccess(String msg) {
        ToastUtils.showLongToast(activity, R.string.paid_toast);
        sellerPhoneLl.setVisibility(View.VISIBLE);
        havePaid.setVisibility(View.GONE);
        paymentMethodLl.setVisibility(View.GONE);
        oderStatus.setText(R.string.payment_has_been);
        if (time != null)
            time.cancel();
    }


    @Override
    public void sellConfirmSuccess(String msg) {
        inputPwdDialog.dismiss();
        oderStatus.setText(R.string.finish);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick()
    public void onViewClicked() {

    }

    @OnClick({R.id.cancel_order, R.id.confirm_success, R.id.order_skm, R.id.have_paid})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.cancel_order:
                presenter.cancelBuyOrder(SharedPrefsUitls.getInstance().getUserToken(), bean.getOrderID());
                break;
            case R.id.confirm_success:
                //卖UBI转认收到钱
                if (paid == -1) {
                    ToastUtils.showLongToast(activity, R.string.confirm_payment_toast);
                    return;
                }
                inputPwdDialog = new InputPwdDialog(activity);
                inputPwdDialog.show();
                inputPwdDialog.setDeleteAlertVisibility(false);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        presenter.sellConfirm(Md5Utils.md5(pwd), SharedPrefsUitls.getInstance().getUserToken(), bean.getOrderID(), paid + "");
                    }
                });

                break;
            case R.id.order_skm:
                startActivity(new Intent(activity, ReceiptQRActivity.class).putExtra("OrderId", bean.getOrderID()));
                break;
            case R.id.have_paid:
                if (paidType == -1) {
                    ToastUtils.showLongToast(activity, R.string.payment_method_toast);
                    return;
                }
                presenter.confirmPaid(SharedPrefsUitls.getInstance().getUserToken(), bean.getOrderID(), paidType + "");

                break;
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            orderDjs.setText("");
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            orderDjs.setText("剩余时间：" + getTimeLeft(millisUntilFinished));
        }
    }

    private String getTimeLeft(long time) {

        long h= time/(60*60*1000);
        long m= time%(60*60*1000)/(60*1000);
        long s= time%(60*1000)/1000;

        return (h==0?"00":h)+":"+(m==0?"00":m)+":"+s;

//        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(time);
//        return formatter.format(calendar.getTime());
    }
}
