package com.ubiRobot.modules.main.my.Language;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.LanguageAdapter;
import com.ubiRobot.app.ActivityUtils;
import com.ubiRobot.app.AppManager;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.bean.languageEntity;
import com.ubiRobot.modules.main.MainActivity;
import com.ubiRobot.modules.main.my.displayCurrency.DisplayCurrencyActivity;
import com.ubiRobot.modules.normalvp.NormalPresenter;
import com.ubiRobot.modules.normalvp.NormalView;
import com.ubiRobot.utils.LocalManageUtil;
import com.ubiRobot.utils.SPLUtil;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.gyf.barlibrary.ImmersionBar;

public class LanguageSelectionActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {

    private RecyclerView etz_node_lv;
    LanguageAdapter adapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_etznode_selection;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getResources().getString(R.string.my_yysz));
        etz_node_lv = findViewById(R.id.etz_node_lv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        etz_node_lv.setLayoutManager(linearLayoutManager);
        adapter = new LanguageAdapter(R.layout.node_list_item, LocalManageUtil.getLanguageList(this));
        etz_node_lv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                languageEntity item = (languageEntity) adapter.getItem(position);
//                LocalManageUtil.saveSelectLanguage(activity, item.getLid());
//                AppManager.getAppManager().finishAllActivity();
//                ActivityUtils.next(activity, MainActivity.class);
//                adapter.notifyDataSetChanged();
                finish();
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
    protected void onResume() {
        super.onResume();

    }
}
