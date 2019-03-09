package com.yhy.tabnav.config;

import com.yhy.tabnav.global.TpgConst;
import com.yhy.tabnav.interceptor.EmptyInterceptor;
import com.yhy.tabnav.interceptor.ErrorInterceptor;
import com.yhy.tabnav.interceptor.LoadingInterceptor;
import com.yhy.tabnav.interceptor.SuccessInterceptor;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:06
 * version: 1.0.0
 * desc   : 页面配置
 */
public class PagerConfig {
    private int mLoadingViewLayoutId;
    private int mErrorViewLayoutId;
    private int mErrorViewRetryResId;
    private int mEmptyViewLayoutId;
    private int mEmptyViewRetryResId;

    // 各种拦截器
    private LoadingInterceptor mLoadingInterceptor;
    private EmptyInterceptor mEmptyInterceptor;
    private ErrorInterceptor mErrorInterceptor;
    private SuccessInterceptor mSuccessInterceptor;

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

    /**
     * 设置加载中拦截器
     *
     * @param interceptor 加载中拦截器
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setLoadingInterceptor(LoadingInterceptor interceptor) {
        mLoadingInterceptor = interceptor;
        return this;
    }

    /**
     * 获取加载中拦截器
     *
     * @return 加载中拦截器
     */
    public LoadingInterceptor getLoadingInterceptor() {
        return mLoadingInterceptor;
    }

    /**
     * 设置空数据拦截器
     *
     * @param interceptor 空数据拦截器
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setEmptyInterceptor(EmptyInterceptor interceptor) {
        mEmptyInterceptor = interceptor;
        return this;
    }

    /**
     * 获取空数据拦截器
     *
     * @return 空数据拦截器
     */
    public EmptyInterceptor getEmptyInterceptor() {
        return mEmptyInterceptor;
    }

    /**
     * 设置错误拦截器
     *
     * @param interceptor 错误拦截器
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setErrorInterceptor(ErrorInterceptor interceptor) {
        mErrorInterceptor = interceptor;
        return this;
    }

    /**
     * 获取错误拦截器
     *
     * @return 错误拦截器
     */
    public ErrorInterceptor getErrorInterceptor() {
        return mErrorInterceptor;
    }

    /**
     * 设置成功拦截器
     *
     * @param interceptor 成功拦截器
     * @return 当前对象，便于链式编程
     */
    public PagerConfig setSuccessInterceptor(SuccessInterceptor interceptor) {
        mSuccessInterceptor = interceptor;
        return this;
    }

    /**
     * 获取成功拦截器
     *
     * @return 成功拦截器
     */
    public SuccessInterceptor getSuccessInterceptor() {
        return mSuccessInterceptor;
    }
}
