package com.ubiRobot.modules.main.my.selectnode;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ubiRobot.R;
import com.ubiRobot.adapter.ETZNodeAdapter;
import com.ubiRobot.base.BaseActivity;
import com.ubiRobot.base.BaseUrl;
import com.ubiRobot.bean.NodeEntity;
import com.ubiRobot.bean.ResponseGasBean;
import com.ubiRobot.http.HttpRequets;
import com.ubiRobot.http.callback.JsonCallback;
import com.ubiRobot.modules.normalvp.NormalPresenter;
import com.ubiRobot.modules.normalvp.NormalView;
import com.ubiRobot.modules.tools.threads.ETZExecutor;
import com.ubiRobot.utils.MyLog;
import com.ubiRobot.utils.SharedPrefsUitls;
import com.ubiRobot.utils.ToastUtils;
import com.ubiRobot.utils.Util;
import com.ubiRobot.view.BRDialogView;
import com.ubiRobot.view.ETZDialog;
import com.ubiRobot.view.MText;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.model.Response;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ETZNodeSelectionActivity extends BaseActivity<NormalView, NormalPresenter> implements NormalView {

    private RecyclerView etz_node_lv;
    AlertDialog mDialog;
    ETZNodeAdapter adapter;
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
        setCenterTitle(getResources().getString(R.string.my_qhjd));
        etz_node_lv = findViewById(R.id.etz_node_lv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        etz_node_lv.setLayoutManager(linearLayoutManager);
        adapter = new ETZNodeAdapter(R.layout.node_list_item, getNodeList());
        etz_node_lv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NodeEntity item = (NodeEntity) adapter.getItem(position);
                SharedPrefsUitls.getInstance().putCurrentNode(item.getNode());
                BaseUrl.node = item.getNode();
                adapter.notifyDataSetChanged();
            }

        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
    }

    private List<NodeEntity> getNodeList() {

        List<NodeEntity> list;
            list = new ArrayList<>();
            list.add(new NodeEntity(getString(R.string.node1), "http://182.61.166.62:8001"));
            list.add(new NodeEntity(getString(R.string.node2), "http://182.61.110.193:8001"));
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void createDialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ETZNodeSelectionActivity.this);
        final TextView customTitle = new TextView(this);

        customTitle.setGravity(Gravity.CENTER);
        customTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        int pad32 = Util.getPixelsFromDps(ETZNodeSelectionActivity.this, 32);
        int pad16 = Util.getPixelsFromDps(ETZNodeSelectionActivity.this, 16);
        customTitle.setPadding(pad16, pad16, pad16, pad16);
        customTitle.setText(getString(R.string.NodeSelector_enterTitle));
        customTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        customTitle.setTypeface(null, Typeface.BOLD);
        alertDialog.setCustomTitle(customTitle);
        alertDialog.setMessage(getString(R.string.NodeSelector_etzenterBody));

        final EditText input = new EditText(ETZNodeSelectionActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        int pix = Util.getPixelsFromDps(ETZNodeSelectionActivity.this, 24);

        input.setPadding(pix, 0, pix, pix);
        input.setLayoutParams(lp);
        input.setText("http://");
        input.setSelection(input.getText().length());
        alertDialog.setView(input);

        alertDialog.setNegativeButton(getString(R.string.Button_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.setPositiveButton(getString(R.string.Button_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        mDialog = alertDialog.show();

        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        mDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = input.getText().toString().trim();
                if (!Util.isNullOrEmpty(str) && str.contains("http") && Patterns.WEB_URL.matcher(str).matches()) {
                    ValidationOfTheNode(str);
                } else {
                    Toast.makeText(getApplication(), R.string.NodeSelector_error_node, Toast.LENGTH_LONG).show();
                }

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                input.requestFocus();
                final InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(input, 0);
            }
        }, 200);
    }

    protected void ValidationOfTheNode(final String mnode) {
        ETZExecutor.getInstance().forLightWeightBackgroundTasks().execute(new Runnable() {
            @Override
            public void run() {
                final String ethRpcUrl = mnode;
                final JSONObject payload = new JSONObject();
                final JSONArray params = new JSONArray();

                try {
                    payload.put("jsonrpc", "2.0");
                    payload.put("method", "web3_clientVersion");
                    payload.put("params", params);
                    payload.put("id", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyLog.i("ValidationOfTheNode=" + ethRpcUrl.toString());
                HttpRequets.postRequest(ethRpcUrl, getClass(), payload.toString(), new JsonCallback<ResponseGasBean>() {
                    @Override
                    public void onSuccess(Response<ResponseGasBean> response) {
                        MyLog.i("ValidationOfTheNode="+response.body().result);
                        if (response.body().result != null) {
                            List<NodeEntity> nList = adapter.getData();
                            boolean isAdd = true;
                            for (NodeEntity item : nList) {
                                if (item.getNode().equals(mnode)) isAdd = false;
                            }
                            if (isAdd) {
                                nList.add(new NodeEntity(getString(R.string.NodeSelector_Custom_rpc) + "(" + (nList.size() - 2) + ")", mnode));
//                                SharedPrefsUitls.getInstance().putNodeList(nList);
                            }
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Response<ResponseGasBean> response) {
                        super.onError(response);
                    }
                    //                    @Override
//                    public void onError(Response<ResponseGasBean> response) {
//
//                        MyLog.i("ValidationOfTheNode="+response.body().toString());
//                        if (response.body().error != null) {
//                            ETZExecutor.getInstance().forMainThreadTasks().execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ToastUtils.showLongToast(response.body().error.message);
//                                }
//                            });
//                        }
//                    }
                });
            }
        });
    }
}
