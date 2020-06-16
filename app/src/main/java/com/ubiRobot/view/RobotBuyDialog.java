package com.ubiRobot.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.bean.RobotBean;
import com.ubiRobot.utils.DateUtil;
import com.ubiRobot.utils.Md5Utils;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;


public class RobotBuyDialog extends Dialog implements View.OnClickListener {

    private TextView create_time;
    private TextView name;
    private TextView period;
    private TextView close_time;
    private TextView day_output;
    private EditText buy_pwd;
    protected OnConfirmClickListener mListener;
    private RobotBean bean;

    public RobotBuyDialog(@NonNull Context context, RobotBean bean) {
        super(context, R.style.MyDialog);
        this.bean = bean;
    }

    public RobotBuyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot_buy_layout);
        setCanceledOnTouchOutside(false);
        create_time = findViewById(R.id.robot_create_time);
        name = findViewById(R.id.robot_buy_class);
        period = findViewById(R.id.robot_period);
        close_time = findViewById(R.id.robot_close_time);
        day_output = findViewById(R.id.robot_day_output);
        buy_pwd = findViewById(R.id.robot_buy_pwd);
        Long current = System.currentTimeMillis();
        create_time.setText(DateUtil.getDateAll(current));
        if (bean == null) {
            close_time.setText(DateUtil.getDateDue(30));
            period.setText(String.format(getContext().getResources().getString(R.string.day), "30"));
            day_output.setText(String.format(getContext().getResources().getString(R.string.UBI), "0.37"));
            name.setText(getContext().getResources().getString(R.string.junior_robot));
        } else {
            close_time.setText(DateUtil.getDateDue(bean.getPeriod() == null ? 0 : Integer.valueOf(bean.getPeriod())));
            period.setText(String.format(getContext().getResources().getString(R.string.day), bean.getPeriod()));
            day_output.setText(String.format(getContext().getResources().getString(R.string.UBI), bean.getRet()));
            name.setText(bean.getName());
        }

        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.robot_buy_cancel).setOnClickListener(this);
        findViewById(R.id.robot_buy_confirm).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.robot_buy_confirm:// 确定
                String pwd = buy_pwd.getText().toString().trim();
                if (Util.isNullOrEmpty(pwd)) {
                    ToastUtils.showLongToast(getContext(), R.string.robot_buy_pwd);
                } else if (mListener != null) {
                    mListener.setConfirmClick(Md5Utils.md5(pwd), bean==null?"1":bean.getMiniID() + "");
                }
                break;
            case R.id.robot_buy_cancel:
                dismiss();
                break;
        }
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnConfirmClickListener {
        void setConfirmClick(String pwd, String miniId);
    }

    public void setOnConfirmClickListener(RobotBuyDialog.OnConfirmClickListener listener) {
        this.mListener = listener;
    }


}
