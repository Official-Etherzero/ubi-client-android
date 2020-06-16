package com.ubiRobot.modules.walletmanage.importwallet;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ubiRobot.R;
import com.ubiRobot.adapter.LoadWalletPageFragmentAdapter;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.base.BaseFragment;
import com.ubiRobot.base.Constants;
import com.ubiRobot.modules.walletmanage.createwallet.CreateWalletPresenter;
import com.ubiRobot.modules.walletmanage.createwallet.CreateWalletView;
import com.ubiRobot.modules.walletmanage.fragment.ImportKeystoreFragment;
import com.ubiRobot.modules.walletmanage.fragment.ImportMnemonicFragment;
import com.ubiRobot.modules.walletmanage.fragment.ImportPrivateKeyFragment;
import com.ubiRobot.utils.Util;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImportWallet extends BaseActivity<CreateWalletView, CreateWalletPresenter> implements CreateWalletView  {
//public class ImportWallet extends BaseActivity<CreateWalletView, CreateWalletPresenter> implements CreateWalletView  {

    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_load_wallet)
    ViewPager vpLoadWallet;

    String type;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private LoadWalletPageFragmentAdapter loadWalletPageFragmentAdapter;
    private IndicatorViewPager indicatorViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_import_wallet;
    }

    @Override
    public CreateWalletPresenter initPresenter() {
        return new CreateWalletPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        type = getIntent().getStringExtra(Constants.WALLET_TYPE);
        fragmentList.add(new ImportMnemonicFragment());
        fragmentList.add(new ImportPrivateKeyFragment());
        if (!type.equalsIgnoreCase("BTC")){
            fragmentList.add(new ImportKeystoreFragment());
        }

        setCenterTitle(getResources().getString(R.string.load_wallet_btn));

        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.zt_fff), getResources().getColor(R.color.zt_et_hint))
                .setSize(14, 14));
        indicatorView.setScrollBar(new TextWidthColorBar(this, indicatorView, getResources().getColor(R.color.zt_lu), Util.dip2px(3)));
        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpLoadWallet);
        loadWalletPageFragmentAdapter = new LoadWalletPageFragmentAdapter(this, getSupportFragmentManager(), fragmentList);
        indicatorViewPager.setAdapter(loadWalletPageFragmentAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpLoadWallet.setOffscreenPageLimit(3);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void getWalletSuccess(String address) {

    }

    @Override
    public void getWalletFail(String msg) {

    }

}
