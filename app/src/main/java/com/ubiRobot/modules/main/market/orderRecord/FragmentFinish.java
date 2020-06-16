package com.ubiRobot.modules.main.market.orderRecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.MyOrderRecordAdapter;
import com.ubiRobot.app.MyApp;
import com.ubiRobot.base.BaseFragment;
import com.ubiRobot.bean.HangOrderBean;
import com.ubiRobot.bean.ReceiptQRBean;
import com.ubiRobot.modules.main.market.orderRecord.orderInfo.OrderInfoActivity;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentFinish extends BaseFragment<OrderRecordView, OrderRecordPresenter> implements OrderRecordView {


    @BindView(R.id.rc_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.list_nologs)
    TextView listNologs;

    private MyOrderRecordAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private HangOrderBean bean;

    @Override
    public OrderRecordPresenter initPresenter() {
        return new OrderRecordPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyOrderRecordAdapter(R.layout.order_record_item_layout, new ArrayList<>(), 2);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bean = (HangOrderBean) adapter.getItem(position);
                Intent intent = new Intent();
                intent.setClass(Objects.requireNonNull(getActivity()), OrderInfoActivity.class);
                intent.putExtra("HangOrderBean", bean);
                intent.putExtra("status", 2);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.listview_layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.myrders(SharedPrefsUitls.getInstance().getUserToken(), "2", "0", "150");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void requestFail(String msg) {
        ToastUtils.showLongToast(getActivity(), msg);
    }

    @Override
    public void getMyOrders(List<HangOrderBean> beans) {
        if (beans != null && beans.size() > 0) {
            listNologs.setVisibility(View.GONE);
            adapter.setTokens(beans);
        } else {
            listNologs.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setCollectionCode(ReceiptQRBean qrBean) {

    }
}
