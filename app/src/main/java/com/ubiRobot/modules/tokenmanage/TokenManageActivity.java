package com.ubiRobot.modules.tokenmanage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ubiRobot.R;
import com.ubiRobot.adapter.TokenManageAdapter;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.TokenInfo;
import com.ubiRobot.modules.normalvp.NormalPresenter;
import com.ubiRobot.modules.normalvp.NormalView;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.TokenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TokenManageActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.rv_token_list)
    RecyclerView rvTokenList;

    private LinearLayoutManager linearLayoutManager;
    TokenManageAdapter tokenManageAdapter;
    String wid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_token_manage;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle("资产管理");
        wid = getIntent().getStringExtra("wid");
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTokenList.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void initData() {
        List<String> tokenList = SharedPrefsUitls.getInstance().getWalletTokenList(wid);
        tokenManageAdapter = new TokenManageAdapter(TokenManageActivity.this, R.layout.manage_token_list_item, new ArrayList<TokenInfo>(), tokenList);
        rvTokenList.setAdapter(tokenManageAdapter);
//        if (wid.startsWith("ETH")) {
//            tokenManageAdapter.setTokens(TokenUtil.getEthTokens(this));
//        } else {
//            tokenManageAdapter.setTokens(TokenUtil.getTokenItems(this));
//        }

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

    @Override
    protected void onPause() {
        super.onPause();
        tokenManageAdapter.saveTokenChange(wid);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
