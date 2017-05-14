package com.yhy.tabnav.config;

import com.yhy.tabnav.global.TpgConst;

public class PagerConfig {
    private int mLoadingViewLayoutId;

    private int mErrorViewLayoutId;

    private int mErrorViewRetryResId;

    private int mEmptyViewLayoutId;

    private int mEmptyViewRetryResId;

    /**
     * 创建页面配置对象
     */
    public PagerConfig() {
        //默认值
        mLoadingViewLayoutId = TpgConst.PagerResIdDef.PAGER_NO_RES_ID;
        mErrorViewLayoutId = TpgConst.PagerResIdDef.PAGER_NO_RES_ID;
        mEmptyViewLayoutId = TpgConst.PagerResIdDef.PAGER_NO_RES_ID;
        mErrorViewRetryResId = TpgConst.PagerResIdDef.PAGER_NO_RES_ID;
        mEmptyViewRetryResId = TpgConst.PagerResIdDef.PAGER_NO_RES_ID;
    }

    /**
     * 设置加载中页面
     *
     * @param layoutId 页面资源id
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setLoadingViewLayoutId(int layoutId) {
        mLoadingViewLayoutId = layoutId;
        return this;
    }

    /**
     * 获取加载中页面资源id
     *
     * @return layoutId
     */
    public int getLoadingViewLayoutId() {
        return mLoadingViewLayoutId;
    }

    /**
     * 设置错误页面
     *
     * @param layoutId   页面资源id
     * @param retryResId 错误页面的重试按钮资源id
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setErrorViewLayoutId(int layoutId, int retryResId) {
        mErrorViewLayoutId = layoutId;
        mErrorViewRetryResId = retryResId;
        return this;
    }

    /**
     * 获取错误页面资源id
     *
     * @return layoutId
     */
    public int getErrorViewResId() {
        return mErrorViewLayoutId;
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
     * @param layoutId   页面资源id
     * @param retryResId 空数据页面的重试按钮资源id
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setEmptyViewLayoutId(int layoutId, int retryResId) {
        mEmptyViewLayoutId = layoutId;
        mEmptyViewRetryResId = retryResId;
        return this;
    }

    /**
     * 获取空数据页面资源id
     *
     * @return layoutId
     */
    public int getEmptyViewResId() {
        return mEmptyViewLayoutId;
    }

    /**
     * 获取空数据页面的重试按钮资源id
     *
     * @return 重试按钮资源id
     */
    public int getEmptyViewRetryResId() {
        return mEmptyViewRetryResId;
    }
}
