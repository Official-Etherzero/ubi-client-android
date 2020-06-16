package com.ubiRobot.modules.user.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.modules.main.MainActivity;
import com.ubiRobot.modules.user.ForgetPassword.ResetPasswordActivity;
import com.ubiRobot.modules.user.rigest.RegisterActivity;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {


    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.btn_login)
    TextView btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.login));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        loginPhone.addTextChangedListener(watcher);
        loginPwd.addTextChangedListener(watcher);
    }

    @Override
    public void requestFail(String msg) {

        ToastUtils.showLongToast(activity, msg);
    }

    @Override
    public void login(UserBean user) {
        if (user != null) {
            SharedPrefsUitls.getInstance().putCurrentAccount(user.getUserID());
            if (MainActivity.getApp() != null) MainActivity.getApp().setIndex(0);
            SharedPrefsUitls.getInstance().putUserToken(user.getAccess_Token());
            MyApp.getmInstance().setUser(user);
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

    @OnClick({R.id.btn_login, R.id.login_forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phone = loginPhone.getText().toString().trim();
                String pwd = loginPwd.getText().toString().trim();
                if (verifyRegistration(phone, pwd)) {
                    presenter.loginPhone("86", phone, pwd);
                }
                break;
            case R.id.login_forget_pwd:
                startActivity(new Intent(activity, ResetPasswordActivity.class));
                break;
        }
    }

    private boolean verifyRegistration(String phone, String pwd) {
        boolean isOk = false;
        if (phone.length() < 6) {
            ToastUtils.showLongToast(activity, R.string.login_hint_phone);
        } else if (!Util.isPassword(pwd)) {
            ToastUtils.showLongToast(activity, R.string.login_hint_pwd);
        } else {
            isOk = true;
        }

        return isOk;
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
            String phone = loginPhone.getText().toString().trim();
            String pwd = loginPwd.getText().toString().trim();
            if (!Util.isNullOrEmpty(phone) && !Util.isNullOrEmpty(pwd)) {
                btnLogin.setEnabled(true);
            } else {
                btnLogin.setEnabled(false);
            }
        }
    };

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
