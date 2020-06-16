package com.ubiRobot.modules.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ubiRobot.R;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.modules.normalvp.NormalPresenter;
import com.ubiRobot.modules.normalvp.NormalView;
import com.ubiRobot.utils.ClipboardManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteFriendsActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.my_yqm)
    TextView myYqm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friends;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        UserBean userBean = MyApp.getmInstance().getUser();
        if (userBean != null) {
            myYqm.setText(userBean.getInviteCode());
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.wallet_back, R.id.copy_yqm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wallet_back:
                finish();
                break;
            case R.id.copy_yqm:
                String yqUrl = "https://ubiglobal.io/#/";
                ClipboardManager.putClipboard(InviteFriendsActivity.this, yqUrl);

                break;
        }
    }
}
