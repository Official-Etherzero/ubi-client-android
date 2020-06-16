package com.ubiRobot.modules.main.property;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.security.rp.RPSDK;
import com.gyf.barlibrary.ImmersionBar;
import com.ubiRobot.R;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.base.BaseFragment;
import com.ubiRobot.base.Constants;
import com.ubiRobot.bean.AuthenticationBean;
import com.ubiRobot.bean.UserBean;
import com.ubiRobot.modules.main.my.Language.LanguageSelectionActivity;
import com.ubiRobot.modules.robot.robotBuy.RobotBuyActivity;
import com.ubiRobot.modules.robot.robotHome.RobotHomeActivity;
import com.ubiRobot.modules.robot.teamRobot.TeamRobotActivity;
import com.ubiRobot.modules.user.InviteFriendsActivity;
import com.ubiRobot.modules.user.Login.LoginActivity;
import com.ubiRobot.modules.user.collectionCode.CollectionCodeActivity;
import com.ubiRobot.modules.user.transfer.TransfeActivity;
import com.ubiRobot.modules.walletoperation.receive.ReceiveQrCodeActivity;
import com.ubiRobot.utils.ETZAnimator;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;
import com.ubiRobot.utils.sysinfo.QMUIStatusBarHelper;
import com.ubiRobot.view.LoadingDialog;
import com.ubiRobot.view.MText;
import com.ubiRobot.view.RobotBuyDialog;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PropertyFragment01 extends BaseFragment<PropertyView, PropertyPresenter> implements PropertyView,
        RobotBuyDialog.OnConfirmClickListener {
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    Unbinder unbinder;
    @BindView(R.id.card_title)
    TextView cardTitle;
    @BindView(R.id.property_banner_btn)
    TextView propertyBannerBtn;
    @BindView(R.id.property_my_power)
    TextView propertyMyPower;
    @BindView(R.id.property_team_power)
    TextView propertyTeamPower;
    @BindView(R.id.property_cumulative_revenue)
    TextView propertyCumulativeRevenue;
    @BindView(R.id.property_last_revenue)
    TextView propertyLastRevenue;
    @BindView(R.id.ubi_total_value)
    MText ubiTotalValue;
    @BindView(R.id.wallet_card_address)
    TextView walletCardAddress;
    @BindView(R.id.wallte_send)
    LinearLayout wallteSend;
    @BindView(R.id.wallet_receice)
    LinearLayout walletReceice;
    @BindView(R.id.seting_acc)
    MText setingAcc;
    @BindView(R.id.seting_uid)
    MText setingUid;
    @BindView(R.id.my_verified)
    TextView my_verified;


    private boolean isShow = false;
    protected static PropertyFragment01 fragment01 = null;
    UserBean userBean;
    LoadingDialog loadingDialog;
    private RobotBuyDialog buyDialog;

    public static PropertyFragment01 getInstance() {
        return fragment01;
    }


    @Override
    public PropertyPresenter initPresenter() {
        return new PropertyPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        fragment01 = this;

    }

    @Override
    protected void initData() {

    }

    public void setTitle(String title) {
        cardTitle.setText(title);
    }


    @Override
    public void initEvent() {
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_property;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    // 打开关闭DrawerLayout
    private void openOrCloseDrawerLayout() {
        boolean drawerOpen = drawer.isDrawerOpen(Gravity.START);
        if (drawerOpen) {
            drawer.closeDrawer(Gravity.START);

        } else {
            drawer.openDrawer(Gravity.START);

        }
    }


    @Override
    public void onDestroyView() {
        MyLog.i("init1---------------onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        MyLog.i("init1---------------onStop");
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        MyLog.i("init1---------------onPause");
        super.onPause();
        boolean drawerOpen = drawer.isDrawerOpen(Gravity.START);
        if (drawerOpen) {
            drawer.closeDrawer(Gravity.START);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser && isShow) {
            updataUi();
        } else {
            if (drawer != null) {
                boolean drawerOpen = drawer.isDrawerOpen(Gravity.START);
                if (drawerOpen) {
                    drawer.closeDrawer(Gravity.START);
                }
            }
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updataUi();
        isShow = true;
    }

    private void updataUi() {
        String token = SharedPrefsUitls.getInstance().getUserToken();
        if (!Util.isNullOrEmpty(token)) {
            presenter.getUserInfo(token);
        }
    }


    @OnClick({R.id.home_set, R.id.btn_scan, R.id.property_banner_btn, R.id.btn_miner, R.id.my_smrz,
            R.id.my_language, R.id.my_yqhy, R.id.my_help, R.id.exit, R.id.wallte_send, R.id.wallet_transfer, R.id.property_team,
            R.id.property_myrobot, R.id.my_skm})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_scan:
                ETZAnimator.openScanner(getActivity(), Constants.SCANNER_REQUEST);
                break;
            case R.id.home_set:
                openOrCloseDrawerLayout();
                break;
            case R.id.wallte_send:
                intent = new Intent(getActivity(), TransfeActivity.class);
                startActivity(intent);
                break;
            case R.id.property_team:
                intent = new Intent(getActivity(), TeamRobotActivity.class);
                startActivity(intent);
                break;
            case R.id.property_myrobot:
                intent = new Intent(getActivity(), RobotHomeActivity.class);
                intent.putExtra("status", 0);
                startActivity(intent);
                break;
            case R.id.wallet_transfer:
                intent = new Intent(getActivity(), ReceiveQrCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.property_banner_btn:
                if (userBean.getIsTrueName().equals("0")) {
                    ToastUtils.showLongToast(getActivity(), R.string.smrz_hint);
                } else if (userBean.getHavaFreeNode() == 0) {
                    buyDialog = new RobotBuyDialog(Objects.requireNonNull(getActivity()), null);
                    buyDialog.show();
                    buyDialog.setOnConfirmClickListener(this);
                } else {
                    intent = new Intent(getActivity(), RobotBuyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_miner:
                intent = new Intent(getActivity(), RobotHomeActivity.class);
                intent.putExtra("status", 0);
                startActivity(intent);
                break;
            case R.id.my_smrz:
                if (!userBean.getIsTrueName().equals("1")) {
                    loadingDialog = new LoadingDialog(getActivity());
                    loadingDialog.show();
                    presenter.getAliDescribeVerifyToken(SharedPrefsUitls.getInstance().getUserToken());
                }
                break;
            case R.id.my_language:
                intent = new Intent(getActivity(), LanguageSelectionActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
            case R.id.my_yqhy:
                intent = new Intent(getActivity(), InviteFriendsActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
            case R.id.my_help:
                break;
            case R.id.my_skm:
                intent = new Intent(getActivity(), CollectionCodeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
            case R.id.exit:
                SharedPrefsUitls.getInstance().putUserToken("");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(getActivity(), msg);
        if (code == 401) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void userInfo(UserBean user) {
        MyLog.i("-------------" + user.toString());
        userBean = user;
        MyApp.getmInstance().setUser(user);
        propertyMyPower.setText(user.getMySuanLi());
        propertyTeamPower.setText(user.getTeamSuanLi());
        propertyCumulativeRevenue.setText(user.getMyReward());
        propertyLastRevenue.setText(user.getMyRewardYes());
        ubiTotalValue.setText(user.getETZ());
        walletCardAddress.setText(user.getRechargeAddr());
        String phone = user.getPhone();
        setingAcc.setText(phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length()));
        setingUid.setText("UID:" + user.getUserID());
        if (!user.getIsTrueName().equals("1")) {
            my_verified.setVisibility(View.GONE);
        } else {
            my_verified.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void setVerifyToken(AuthenticationBean bean) {
        loadingDialog.dismiss();
//        CloudRealIdentityTrigger.startVerifyByNative(getActivity(), bean.getVerifyToken(), getALRealIdentityCallback());
//        RPSDK.start(getActivity(), bean.getVerifyToken(), getALRealIdentityCallback());
        RPSDK.start(bean.getVerifyToken(), getActivity(), new RPSDK.RPCompletedListener() {
            @Override
            public void onAuditResult(RPSDK.AUDIT audit, String code) {
                if (audit == RPSDK.AUDIT.AUDIT_PASS) {
                    // 认证通过。建议接入方调用实人认证服务端接口DescribeVerifyResult来获取最终的认证状态，并以此为准进行业务上的判断和处理
                    // do something
                    presenter.getAliDescribeVerifyResult(SharedPrefsUitls.getInstance().getUserToken());
                    ToastUtils.showLongToast(getActivity(), R.string.name_renzhen_success);
                } else if (audit == RPSDK.AUDIT.AUDIT_FAIL) {
                    // 认证不通过。建议接入方调用实人认证服务端接口DescribeVerifyResult来获取最终的认证状态，并以此为准进行业务上的判断和处理
                    // do something
                    ToastUtils.showLongToast(getActivity(), R.string.name_renzhen_fail);
                } else if (audit == RPSDK.AUDIT.AUDIT_NOT) {
                    // 未认证，具体原因可通过code来区分（code取值参见下方表格），通常是用户主动退出或者姓名身份证号实名校验不匹配等原因，导致未完成认证流程
                    // do something
                }
            }
        });
    }

    @Override
    public void setVerifyResult(AuthenticationBean bean) {
        ToastUtils.showLongToast(getActivity(), bean.getVerifyStatus());

    }

    @Override
    public void buyFreeNodeSuccess(int code) {
        if (code == 0) {
            buyDialog.dismiss();
        }
    }

    @Override
    public void setConfirmClick(String pwd, String miniId) {
        presenter.buyFreeNode(SharedPrefsUitls.getInstance().getUserToken(), pwd);
    }

//    /**
//     * 基础回调的方式 TODO
//     *
//     * @return
//     */
//    private ALRealIdentityCallback getALRealIdentityCallback() {
//        return new ALRealIdentityCallback() {
//            @Override
//            public void onAuditResult(ALRealIdentityResult alRealIdentityResult, String s) {
//                //DO your things
//                MyLog.i("RPSDKALRealIdentityResult:" + alRealIdentityResult.audit + "==s=" + s);
//                presenter.getAliDescribeVerifyResult(SharedPrefsUitls.getInstance().getUserToken());
//                if (alRealIdentityResult.audit == 1) {
//                    ToastUtils.showLongToast(getActivity(), R.string.name_renzhen_success);
//                }
//            }
//        };
//    }

}
