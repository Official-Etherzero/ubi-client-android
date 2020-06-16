package com.ubiRobot.modules.user.ForgetPassword;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.modules.user.rigest.RegisterActivity;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity<ForgetPasswordView, ForgetPasswordPresenter> implements ForgetPasswordView {


    @BindView(R.id.reset_phone)
    EditText resetPhone;
    @BindView(R.id.reset_security_code)
    EditText resetSecurityCode;
    @BindView(R.id.reset_get_code)
    TextView resetGetCode;
    @BindView(R.id.reset_pwd)
    EditText resetPwd;
    @BindView(R.id.reset_confirm_pwd)
    EditText resetConfirmPwd;
    @BindView(R.id.btn_reset)
    TextView btnReset;
    public TimeCount time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public ForgetPasswordPresenter initPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.reset_password));

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        resetPhone.addTextChangedListener(watcher);
        resetPwd.addTextChangedListener(watcher);
        resetConfirmPwd.addTextChangedListener(watcher);
        resetSecurityCode.addTextChangedListener(watcher);
    }

    @Override
    public void requestFail(String msg) {
        ToastUtils.showLongToast(activity, msg);
    }

    @Override
    public void codeSuccess(int code) {
        if (code == 0) {
            ToastUtils.showLongToast(activity, R.string.phone_sms_send);
            time = new TimeCount(60000, 1000);
            time.start();// 开始计时
        }
    }


    @Override
    public void resetSuccess(int code) {
        if (code == 0) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.reset_get_code, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reset_get_code:
                String ph = resetPhone.getText().toString().trim();
                if (Util.isNullOrEmpty(ph)) {
                    ToastUtils.showLongToast(activity, R.string.login_hint_phone);
                } else {
                    presenter.getPhoneCode("86",ph);
                }
                break;
            case R.id.btn_reset:
                String phone = resetPhone.getText().toString().trim();
                String pwd = resetPwd.getText().toString().trim();
                String pwd1 = resetConfirmPwd.getText().toString().trim();
                String sCode = resetSecurityCode.getText().toString().trim();
                if (phone.length() < 6) {
                    ToastUtils.showLongToast(activity, R.string.login_hint_phone);
                } else if (!pwd.equals(pwd1)) {
                    ToastUtils.showLongToast(activity, R.string.pwds_alien);
                } else {
                    presenter.resetPhonePWD("86",phone,pwd,sCode);
                }
                break;
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            resetGetCode.setText("获取验证码");
            resetGetCode.setClickable(true);

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            resetGetCode.setClickable(false);
            resetGetCode.setText(millisUntilFinished / 1000 + " s");
        }
    }

    @Override
    protected void onStop() {
        if (time != null)
            time.cancel();
        super.onStop();

    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String phone = resetPhone.getText().toString().trim();
            String pwd = resetPwd.getText().toString().trim();
            String pwd1 = resetConfirmPwd.getText().toString().trim();
            String sCode = resetSecurityCode.getText().toString().trim();
            if (!Util.isNullOrEmpty(phone) && !Util.isNullOrEmpty(pwd) && !Util.isNullOrEmpty(pwd1)
                    && !Util.isNullOrEmpty(sCode) && !Util.isNullOrEmpty(pwd)) {
                btnReset.setEnabled(true);
            } else {
                btnReset.setEnabled(false);
            }
        }
    };
}
