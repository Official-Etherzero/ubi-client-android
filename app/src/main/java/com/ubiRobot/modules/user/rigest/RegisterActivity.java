package com.ubiRobot.modules.user.rigest;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.modules.main.MainActivity;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterView, RegisterPresenter> implements RegisterView {


    @BindView(R.id.register_phone)
    EditText registerPhone;
    @BindView(R.id.register_pwd)
    EditText registerPwd;
    @BindView(R.id.register_confirm_pwd)
    EditText registerConfirmPwd;
    @BindView(R.id.register_security_code)
    EditText registerSecurityCode;
    @BindView(R.id.get_security_code)
    TextView getSecurityCode;
    @BindView(R.id.register_invitation_code)
    EditText registerInvitationCode;
    @BindView(R.id.btn_rigest)
    TextView btnRigest;

    public TimeCount time;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.rigest));

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        registerPhone.addTextChangedListener(watcher);
        registerPwd.addTextChangedListener(watcher);
        registerConfirmPwd.addTextChangedListener(watcher);
        registerSecurityCode.addTextChangedListener(watcher);
        registerInvitationCode.addTextChangedListener(watcher);
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
    public void RegisterSuccess(UserBean user) {
        if (user != null) {
            SharedPrefsUitls.getInstance().putCurrentAccount(user.getUserID());
//            SharedPrefsUitls.getInstance().putCurrentWalletAddress("");
            SharedPrefsUitls.getInstance().putUserToken(user.getAccess_Token());
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.get_security_code, R.id.btn_rigest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_security_code:
                String ph = registerPhone.getText().toString().trim();
                if (Util.isNullOrEmpty(ph)) {
                    ToastUtils.showLongToast(activity, R.string.login_hint_phone);
                } else {
                    presenter.getPhoneCode("86",ph);
                }
                break;
            case R.id.btn_rigest:
                String phone = registerPhone.getText().toString().trim();
                String pwd = registerPwd.getText().toString().trim();
                String pwd1 = registerConfirmPwd.getText().toString().trim();
                String sCode = registerSecurityCode.getText().toString().trim();
                String iCode = registerInvitationCode.getText().toString().trim();
                if (phone.length() < 6) {
                    ToastUtils.showLongToast(activity, R.string.login_hint_phone);
                } else if (!pwd.equals(pwd1)) {
                    ToastUtils.showLongToast(activity, R.string.pwds_alien);
                } else {
                    presenter.phoneRegister(pwd, iCode, phone, sCode);
                }
                break;
        }
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
            String phone = registerPhone.getText().toString().trim();
            String pwd = registerPwd.getText().toString().trim();
            String pwd1 = registerConfirmPwd.getText().toString().trim();
            String sCode = registerSecurityCode.getText().toString().trim();
            String iCode = registerInvitationCode.getText().toString().trim();
            if (!Util.isNullOrEmpty(phone) && !Util.isNullOrEmpty(pwd) && !Util.isNullOrEmpty(pwd1)
                    && !Util.isNullOrEmpty(sCode) && !Util.isNullOrEmpty(pwd) && !Util.isNullOrEmpty(iCode)) {
                btnRigest.setEnabled(true);
            } else {
                btnRigest.setEnabled(false);
            }
        }
    };

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            getSecurityCode.setText("获取验证码");
            getSecurityCode.setClickable(true);

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getSecurityCode.setClickable(false);
            getSecurityCode.setText(millisUntilFinished / 1000 + " s");
        }
    }

    @Override
    protected void onStop() {
        if (time != null)
            time.cancel();
        super.onStop();

    }
}
