package com.yhy.tabpager.pager;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yhy.tabpager.utils.ToastUtils;
import com.yhy.tpg.handler.ResultHandler;
import com.yhy.tpg.pager.TpgFragment;

import java.util.Random;

public class APager extends TpgFragment {

    private ResultHandler mResultHandler;

    @Override
    protected View getSuccessView() {
        TextView tv = new TextView(getContext());
        tv.setText("A页面加载成功");
        tv.setTextColor(Color.RED);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    /**
     * 由于该页面是第一页，所以需要重写该方法，并返回true
     *
     * @return 是否首先加载
     */
    @Override
    public boolean shouldLoadDataAtFirst() {
        return true;
    }

    @Override
    protected void initData(ResultHandler handler) {
        mResultHandler = handler;

        getDataFromServer();
    }

    @Override
    public void reloadDate(Object... args) {
        //调用父类方法才会重新显示加载中页面，否则只是执行重新加载操作，不会显示加载中页面
        super.reloadDate();

        //子类的具体操作...
        String temp = "";
        if (null != args && args.length > 0 && args[0] instanceof String) {
            temp = (String) args[0];
        }
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
