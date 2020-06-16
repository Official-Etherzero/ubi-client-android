package com.ubiRobot.modules.main.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ubiRobot.R;
import com.ubiRobot.base.BaseFragment;
import com.ubiRobot.base.BaseUrl;
import com.ubiRobot.bean.WalletBean;
import com.ubiRobot.modules.main.my.Language.LanguageSelectionActivity;
import com.ubiRobot.modules.main.my.about.AboutActivity;
import com.ubiRobot.modules.main.my.concacts.ContactsActivity;
import com.ubiRobot.modules.main.my.displayCurrency.DisplayCurrencyActivity;
import com.ubiRobot.modules.main.my.selectnode.ETZNodeSelectionActivity;
import com.ubiRobot.modules.main.my.wallets.WalletsManageActivity;
import com.ubiRobot.modules.normalvp.NormalPresenter;
import com.ubiRobot.modules.normalvp.NormalView;
import com.ubiRobot.modules.walletsetting.WalletSetting;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.view.MText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentMy extends BaseFragment<NormalView, NormalPresenter> implements NormalView {
//    @BindView(R.id.current_currency)
//    MText currentCurrency;
//    @BindView(R.id.current_language)
//    MText currentLanguage;
    Unbinder unbinder;

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
//        currentNode.setText(BaseUrl.getEthereumRpcUrl());
    }
}
