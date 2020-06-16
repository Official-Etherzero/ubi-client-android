package com.ubiRobot.modules.user.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.base.Constants;
import com.ubiRobot.modules.robot.teamRobot.RewardDetailsActivity;
import com.ubiRobot.modules.user.Login.LoginActivity;
import com.ubiRobot.utils.ETZAnimator;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransfeActivity extends BaseActivity<TransfeView, TransfePresenter> implements TransfeView {


    @BindView(R.id.transfe_assets)
    TextView transfeAssets;
    @BindView(R.id.transfe_count)
    EditText transfeCount;
    @BindView(R.id.transfe_acc)
    EditText transfeAcc;
    @BindView(R.id.transfe_pwd)
    EditText transfePwd;
    @BindView(R.id.transfe_btn)
    TextView transfeBtn;
    @BindView(R.id.img_right)
    ImageView imgRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfe;
    }

    @Override
    public TransfePresenter initPresenter() {
        return new TransfePresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.tansfe));
        imgRight.setImageResource(R.mipmap.robot_jilu);
        imgRight.setVisibility(View.VISIBLE);
        transfeAssets.setText(MyApp.getmInstance().getUser().getETZ());

    }

    @Override
    protected void initData() {
    }

    @Override
    public void initEvent() {
        transfeCount.addTextChangedListener(watcher);
        transfePwd.addTextChangedListener(watcher);
        transfeAcc.addTextChangedListener(watcher);
    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(activity, msg);
        if (code == 401) {
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void transfeSuccesss(String msg) {
        ToastUtils.showLongToast(activity, msg);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.transfe_all, R.id.transfe_scan, R.id.transfe_btn, R.id.img_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_right:
                startActivity(new Intent(activity, RewardDetailsActivity.class));
                break;
            case R.id.transfe_all:
                transfeCount.setText(MyApp.getmInstance().getUser().getETZ());
                break;
            case R.id.transfe_scan:
                ETZAnimator.openScanner(this, Constants.SCANNER_REQUEST);
                break;
            case R.id.transfe_btn:
                String count = transfeCount.getText().toString().trim();
                String pwd = transfePwd.getText().toString().trim();
                String acc = transfeAcc.getText().toString().trim();
                presenter.transfe(SharedPrefsUitls.getInstance().getUserToken(), pwd, acc, count);
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
            String count = transfeCount.getText().toString().trim();
            String pwd = transfePwd.getText().toString().trim();
            String acc = transfeAcc.getText().toString().trim();
            if (!Util.isNullOrEmpty(count) && !Util.isNullOrEmpty(pwd) && !Util.isNullOrEmpty(acc)) {
                transfeBtn.setEnabled(true);
            } else {
                transfeBtn.setEnabled(false);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        MyLog.i("**************" + requestCode);
        if (data == null) return;
        switch (requestCode) {
            case Constants.SCANNER_REQUEST:
                String result = data.getStringExtra("result");
                MyLog.i("**************" + result);
                if (!Util.isNullOrEmpty(result)) {
                    transfeAcc.setText(result);
                } else {
                    ToastUtils.showLongToast(activity, R.string.Send_invalidAddressTitle);
                }
                break;
        }
    }

}
