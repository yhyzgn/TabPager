package com.yhy.tpg.config;

import android.content.Context;

import com.yhy.tpg.global.Const;

public class PagerConfig {
    private Context mCtx;

    private int mLoadingViewResId;

    private int mErrorViewResId;

    private int mEmpryViewResId;

    /**
     * 创建页面配置对象
     *
     * @param ctx 上下文对象
     */
    public PagerConfig(Context ctx) {
        //初始值
        mCtx = ctx;
        //默认值
        mLoadingViewResId = Const.PagerResIdDef.PAGER_RES_ID_DEF;
        mErrorViewResId = Const.PagerResIdDef.PAGER_RES_ID_DEF;
        mEmpryViewResId = Const.PagerResIdDef.PAGER_RES_ID_DEF;
    }

    /**
     * 获取上下文对象
     *
     * @return 上下文对象
     */
    public Context getContext() {
        return mCtx;
    }

    /**
     * 设置加载中页面
     *
     * @param resId 页面资源id
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setLoadingViewResId(int resId) {
        checkContext();
        mLoadingViewResId = resId;
        return this;
    }

    /**
     * 获取加载中页面资源id
     *
     * @return resId
     */
    public int getLoadingViewResId() {
        return mLoadingViewResId;
    }

    /**
     * 设置错误页面
     *
     * @param resId 页面资源id
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setErrorViewResId(int resId) {
        checkContext();
        mErrorViewResId = resId;
        return this;
    }

    /**
     * 获取错误页面资源id
     *
     * @return resId
     */
    public int getErrorViewResId() {
        return mErrorViewResId;
    }

    /**
     * 设置空数据页面
     *
     * @param resId 页面资源id
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setEmptyViewResId(int resId) {
        checkContext();
        mEmpryViewResId = resId;
        return this;
    }

    /**
     * 获取空数据页面资源id
     *
     * @return resId
     */
    public int getEmptyViewResId() {
        return mEmpryViewResId;
    }

    /**
     * 检查是否已设置上下文对象
     */
    private void checkContext() {
        if (null == mCtx) {
            throw new RuntimeException("上下文对象为空，请在创建PagerConfig对象时设置上下文环境对象");
        }
    }
}
