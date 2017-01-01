package com.yhy.tpg.config;

import android.content.Context;

public class PagerConfig {
    private Context mCtx;

    private int mLoadingViewResId;

    private int mErrorViewResId;

    private int mEmpryViewResId;

    public PagerConfig(Context ctx) {
        //初始值
        mCtx = ctx;
        mLoadingViewResId = -1;
        mErrorViewResId = -1;
        mEmpryViewResId = -1;
    }

    public Context getContext() {
        return mCtx;
    }

    public PagerConfig setLoadingViewResId(int resId) {
        checkContext();
        mLoadingViewResId = resId;
        return this;
    }

    public int getLoadingViewResId() {
        return mLoadingViewResId;
    }

    public PagerConfig setErrorViewResId(int resId) {
        checkContext();
        mErrorViewResId = resId;
        return this;
    }

    public int getErrorViewResId() {
        return mErrorViewResId;
    }

    public PagerConfig setEmptyViewResId(int resId) {
        checkContext();
        mEmpryViewResId = resId;
        return this;
    }

    public int getEmptyViewResId() {
        return mEmpryViewResId;
    }

    private void checkContext() {
        if (null == mCtx) {
            throw new RuntimeException("在配置加载中页面、加载错误页面或者空数据页面之前，请先设置setContext()方法设置上下文环境对象");
        }
    }
}
