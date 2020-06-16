package com.ubiRobot.modules.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.ubiRobot.R;
import com.ubiRobot.adapter.HomePagerAdapter;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.base.BaseUrl;
import com.ubiRobot.base.Constants;
import com.ubiRobot.data.DataSource;
import com.ubiRobot.data.RemoteDataSource;
import com.ubiRobot.modules.main.discovery.FragmentDiscovery;
import com.ubiRobot.modules.main.market.FragmentMarketc2c;
import com.ubiRobot.modules.main.my.FragmentMy;
import com.ubiRobot.modules.main.property.PropertyFragment01;
import com.ubiRobot.modules.normalvp.NormalPresenter;
import com.ubiRobot.modules.normalvp.NormalView;
import com.ubiRobot.modules.walletoperation.send.SendActivity;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;
import com.ubiRobot.view.NoScrollViewPager;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.vp_home)
    NoScrollViewPager vpHome;
//    @BindView(R.id.main_daohanglan)
//    LinearLayout mainDaohanglan;
    private int index = 0;


    protected static MainActivity mActivity = null;
    private HomePagerAdapter homePagerAdapter;

    public static MainActivity getApp() {
        return mActivity;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mActivity = this;
        vpHome.setOffscreenPageLimit(2);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PropertyFragment01());
        fragmentList.add(new FragmentMarketc2c());
//        fragmentList.add(new FragmentDiscovery());
//        fragmentList.add(new FragmentMy());
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), fragmentList);
        vpHome.setAdapter(homePagerAdapter);
        MyLog.i("------------------------"+SharedPrefsUitls.getInstance().getCurrentWalletAddress());
        checkVersionUpdate();
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

    @OnClick({R.id.main_ll_wallet, R.id.main_ll_discivery, R.id.main_ll_my, R.id.main_ll_market})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_ll_wallet:
                index = 0;
                isClickLL(true, false, false, false);
                vpHome.setCurrentItem(0, false);
                break;
            case R.id.main_ll_market:
                isClickLL(false, true, false, false);
                index = 1;
                vpHome.setCurrentItem(1, false);
                break;
            case R.id.main_ll_discivery:
//                ToastUtils.showLongToast(activity, R.string.staytuned);
//                iisClickLL(false, false, true, false);
//                vpHome.setCurrentItem(2, false);
                break;
            case R.id.main_ll_my:
//                ToastUtils.showLongToast(activity, R.string.staytuned);
//                index = 3;
//                isClickLL(false, false, false, true);
//                vpHome.setCurrentItem(3, false);
                break;
        }
    }

    public void isClickLL(boolean tab01, boolean tab02, boolean tab03, boolean tab04) {
//        if (tab01) {
//            QMUIStatusBarHelper.setStatusBarDarkMode(this);
//        } else {
//            QMUIStatusBarHelper.setStatusBarLightMode(this);
//        }
        findViewById(R.id.main_ll_wallet).setSelected(tab01);
        findViewById(R.id.main_ll_market).setSelected(tab02);
//        findViewById(R.id.main_ll_discivery).setSelected(tab03);
//        findViewById(R.id.main_ll_my).setSelected(tab04);
    }

//    public void isShowNavigationBar(boolean isShow) {
//        if (isShow) {
//            mainDaohanglan.setVisibility(View.VISIBLE);
//        } else {
//            mainDaohanglan.setVisibility(View.GONE);
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentTab();

    }

    public void setCurrentTab() {
        vpHome.setCurrentItem(index, false);
        switch (index) {
            case 0:
                isClickLL(true, false, false, false);
                break;
            case 1:
                isClickLL(false, true, false, false);
                break;
            case 2:
                isClickLL(false, false, true, false);
                break;
            case 4:
                isClickLL(false, false, false, true);
                break;
        }
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        MyLog.i("**************" + requestCode);
        if (data == null) return;
        switch (requestCode) {
            case Constants.SCANNER_REQUEST:
                String result = data.getStringExtra("result");
                MyLog.i("**************" + result);

                if (!Util.isNullOrEmpty(result) && Util.isAddressValid(result)) {
                    Intent intent = new Intent(this, SendActivity.class);
                    intent.putExtra("toAddress", result);
                    intent.putExtra("iso", "UBI");
                    startActivity(intent);
                } else {
                    ToastUtils.showLongToast(activity, R.string.Send_invalidAddressTitle);
                }


                break;
        }
    }


    public String getVersionCode() {
        Context ctx = this.getApplicationContext();
        PackageManager packageManager = ctx.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 更新版本
     */
    public void checkVersionUpdate() {
        Context ctx = this.getApplicationContext();
        AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestMethod(HttpRequestMethod.GET)
                .setRequestUrl(BaseUrl.versionCheekUrl())
                .request(new RequestVersionListener() {
                    @android.support.annotation.Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {

                        try {
                            if (Util.isNullOrEmpty(result)) {
                                MyLog.i("onRequestVersionSuccess: 获取新版本失败1");
                            }
                            JSONObject json = new JSONObject(result);
                            MyLog.i("onRequestVersionSuccess: json==" + json);

                            String dlUrl = json.getString("AndroidURL");
                            String dlContent = json.getString("Describe");
                            String versionCode = json.getString("AndroidVersionCode");
                            String versionName = json.getString("AndroidVersionName");

                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(getString(R.string.current_version));
                            stringBuilder.append(versionName);
                            stringBuilder.append("\n");
                            stringBuilder.append(getString(R.string.update_content));
                            stringBuilder.append(dlContent);

                            String finalString = stringBuilder.toString();

                            if (Integer.parseInt(versionCode) > Integer.parseInt(getVersionCode())) {
                                UIData uiData = UIData
                                        .create()
                                        .setDownloadUrl(dlUrl)
                                        .setTitle(getString(R.string.download_latest_version))
                                        .setContent(finalString);
                                return uiData;
                            } else {
//                                ToastUtils.showLongToast(activity, "已是最新版本");
                                return null;
                            }

                        } catch (Exception e) {
                            MyLog.i("onRequestVersionSuccess: 获取新版本失败2");
                        }

                        return null;
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {

                    }
                })
                .excuteMission(ctx);

    }
}
