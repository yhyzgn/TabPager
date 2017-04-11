package com.yhy.tabnav.config;

import android.content.Context;

import com.yhy.tabnav.global.Const;

public class PagerConfig {
    private Context mCtx;

    private int mLoadingViewResId;

    private int mErrorViewResId;

    private int mErrorViewRetryResId;

    private int mEmptyViewResId;

    private int mEmptyViewRetryResId;

    /**
     * 创建页面配置对象
     *
     * @param ctx 上下文对象
     */
    public PagerConfig(Context ctx) {
        //初始值
        mCtx = ctx;
        //默认值
        mLoadingViewResId = Const.PagerResIdDef.PAGER_NO_RES_ID;
        mErrorViewResId = Const.PagerResIdDef.PAGER_NO_RES_ID;
        mEmptyViewResId = Const.PagerResIdDef.PAGER_NO_RES_ID;
        mErrorViewRetryResId = Const.PagerResIdDef.PAGER_NO_RES_ID;
        mEmptyViewRetryResId = Const.PagerResIdDef.PAGER_NO_RES_ID;
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
     * @param resId      页面资源id
     * @param retryResId 错误页面的重试按钮资源id
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setErrorViewResId(int resId, int retryResId) {
        checkContext();
        mErrorViewResId = resId;
        mErrorViewRetryResId = retryResId;
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
     * 获取错误页面的重试按钮资源id
     *
     * @return 重试按钮资源id
     */
    public int getErrorViewRetryResId() {
        return mErrorViewRetryResId;
    }

    /**
     * 设置空数据页面
     *
     * @param resId      页面资源id
     * @param retryResId 空数据页面的重试按钮资源id
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setEmptyViewResId(int resId, int retryResId) {
        checkContext();
        mEmptyViewResId = resId;
        mEmptyViewRetryResId = retryResId;
        return this;
    }

    /**
     * 获取空数据页面资源id
     *
     * @return resId
     */
    public int getEmptyViewResId() {
        return mEmptyViewResId;
    }

    /**
     * 获取空数据页面的重试按钮资源id
     *
     * @return 重试按钮资源id
     */
    public int getEmptyViewRetryResId() {
        return mEmptyViewRetryResId;
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
