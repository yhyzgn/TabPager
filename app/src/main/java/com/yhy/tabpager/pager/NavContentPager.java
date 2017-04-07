package com.yhy.tabpager.pager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yhy.tabpager.TpgActivity;
import com.yhy.tabpager.utils.ToastUtils;
import com.yhy.tpg.handler.ResultHandler;
import com.yhy.tpg.pager.TpgFragment;

import java.util.Random;

public class NavContentPager extends TpgFragment {

    private boolean isLoaded;

    private ResultHandler mResultHandler;

    @Override
    protected View getSuccessView() {
        TextView tv = new TextView(getContext());
        tv.setText("NavContentPager加载成功");
        tv.setTextColor(Color.RED);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, TpgActivity.class);
                startActivity(intent);
            }
        });

        return tv;
    }

    /**
     * 由于该页面是第一页，所以需要重写该方法，并返回true
     *
     * @return 是否首先加载
     */
    @Override
    public boolean shouldLoadDataAtFirst() {
        Bundle args = getArguments();
        boolean firstPage = args.getBoolean("firstPage");
        return !isLoaded && firstPage;
    }

    @Override
    protected void initData(ResultHandler handler) {
        mResultHandler = handler;

        isLoaded = true;

        getDataFromServer();
    }

    @Override
    public void reloadDate(Bundle args) {
        //调用父类方法才会重新显示加载中页面，否则只是执行重新加载操作，不会显示加载中页面
        super.reloadDate(args);

        //子类的具体操作...
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
                Logger.i(temp + "");
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
