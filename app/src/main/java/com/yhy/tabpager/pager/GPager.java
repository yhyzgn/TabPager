package com.yhy.tabpager.pager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yhy.tabpager.utils.ToastUtils;
import com.yhy.tabnav.handler.ResultHandler;
import com.yhy.tabnav.pager.TpgFragment;

import java.util.Random;

public class GPager extends TpgFragment {

    private ResultHandler mResultHandler;

    @Override
    protected View getSuccessView(LayoutInflater inflater, ViewGroup container) {
        TextView tv = new TextView(getContext());
        tv.setText("G页面加载成功");
        tv.setTextColor(Color.RED);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }


    @Override
    protected void initData(ResultHandler handler) {
        mResultHandler = handler;

        getDataFromServer();
    }

    @Override
    public void reloadDate(Bundle args) {
        String temp = args.getString("args");
        ToastUtils.shortToast(temp + "页面重新加载数据");

        getDataFromServer();
    }

    private void getDataFromServer() {
        final Random random = new Random();
        new Thread() {
            @Override
            public void run() {
                //模拟网络加载延迟
                SystemClock.sleep(3000);

                //数据加载结束后，需要手动刷新页面状态
                int temp = random.nextInt(3);
                switch (temp) {
                    case 0:
                        mResultHandler.sendSuccessHandler();
                        break;
                    case 1:
                        mResultHandler.sendErrorHandler();
                        break;
                    case 2:
                        mResultHandler.sendEmptyHandler();
                        break;
                    default:
                        break;
                }
            }
        }.start();
    }
}