package com.ubiRobot.modules.robot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.ubiRobot.R;

public class RobotMorePopupWindow extends PopupWindow implements View.OnClickListener {

    private View mPopView;
    private Activity ctx;


    public RobotMorePopupWindow(Activity context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.ctx = context;
        init(context);
        setPopupWindow();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Activity context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.robot_more_layout, null);
        mPopView.findViewById(R.id.robot_more).setOnClickListener(this);
        mPopView.findViewById(R.id.robot_more_ll).setOnClickListener(this);
        mPopView.findViewById(R.id.robot_buy_log).setOnClickListener(this);
        mPopView.findViewById(R.id.robot_transfer_the_money).setOnClickListener(this);
        mPopView.findViewById(R.id.robot_rewards_record).setOnClickListener(this);

    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
//        this.setAnimationStyle(R.style.pop_animation);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x30000000));// 设置背景透明
        this.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        this.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        this.setFocusable(true);
    }


    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(String type);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.robot_more:
                dismiss();
                break;
            case R.id.robot_more_ll:
                break;
            case R.id.robot_buy_log:
                break;
            case R.id.robot_transfer_the_money:
//                MainActivity.getApp().setIndex(1);
//                FragmentMarket.index = 2;
//                ctx.startActivity(new Intent(ctx, MainActivity.class));
                dismiss();
                break;
            case R.id.robot_rewards_record:
                break;

        }
    }


}
