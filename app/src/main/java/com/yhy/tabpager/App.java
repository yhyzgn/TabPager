package com.yhy.tabpager;

import android.app.Application;

import com.yhy.tabpager.utils.ToastUtils;

/**
 * Created by HongYi Yan on 2017/3/12 0:40.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ToastUtils.init(getApplicationContext());
    }
}
