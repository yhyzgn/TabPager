package com.yhy.tabnav.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.tabnav.R;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.dispatch.DispatchLoading;
import com.yhy.tabnav.global.TpgConst;
import com.yhy.tabnav.handler.ResultHandler;
import com.yhy.tabnav.tpg.PagerFace;
import com.yhy.tabnav.utils.ViewUtils;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-15 14:55
 * version: 1.0.0
 * desc   : 用来配合PagerFace完成页面依附的辅助类
 */
public class TpgHelper<RT> {
    protected RT mRoot;
    //每个页面中分发页面的对象
    private DispatchLoading mDispatch;
    //页面配置
    private PagerConfig mConfig;
    //当前Activity对象
    public Activity mActivity;
    //记录页面显示或隐藏的状态
    private boolean mIsVisible;

    /**
     * 获取Activity
     *
     * @param ctx 上下文对象
     * @return 当前Activity对象
     */
    public Activity getPagerActivity(Context ctx) {
        mActivity = null == ctx ? null : (Activity) ctx;
        return mActivity;
    }

    /**
     * 设置根页面
     *
     * @param root 根页面
     */
    public void setRoot(RT root) {
        mRoot = root;
    }

    /**
     * 设置页面配置参数
     *
     * @param config 页面配置参数
     */
    public void setPagerConfig(PagerConfig config) {
        mConfig = config;
    }

    /**
     * 页面显示或隐藏状态改变时触发
     *
     * @param face      当前页面
     * @param isVisible 是否显示
     */
    public void onPagerVisible(PagerFace face, boolean isVisible) {
        // 记录下当前状态（由于setUserVisibleHint方法再onCreateView之前执行，所以要实现懒加载的话，就再onCreateView方法中手动调用加载数据方法，并在setUserVisibleHint方法中判断是否显示）
        mIsVisible = isVisible;
        // 触发页面状态改变事件
        face.onPagerVisible(mIsVisible);
    }

    /**
     * 获取页面状态
     *
     * @return 页面状态
     */
    public boolean getIsVisible() {
        return mIsVisible;
    }

    /**
     * 创建页面真正显示的View
     *
     * @param face               当前页面
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存参数
     * @return 真正显示的View
     */
    public View onCreatePagerView(final PagerFace face, final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        if (null == mDispatch) {
            //创建mDispatch对象，实现其抽象方法，并回调页面中相应的抽象方法
            mDispatch = new DispatchLoading(mActivity) {
                @Override
                public View getSuccessView() {
                    return face.getSuccessView(inflater, container, savedInstanceState);
                }

                @Override
                public View getLoadingView() {
                    return face.getLoadingView(inflater, container, savedInstanceState);
                }

                @Override
                public View getErrorView() {
                    return face.getErrorView(inflater, container, savedInstanceState);
                }

                @Override
                public View getEmptyView() {
                    return face.getEmptyView(inflater, container, savedInstanceState);
                }

                @Override
                public void initData() {
                    // 初始化数据
                    face.initData();
                    // 初始化事件一些事件监听
                    face.initListener();
                }
            };
        } else {
            //由于一个View不能同时有两个parent，而当mDispatch不为空时说明当前页面（View）已经添加过给其他parent了，
            //所以这里需要把mDispatch从原来的parent中移除
            ViewUtils.removeFromParent(mDispatch);
        }
        return mDispatch;
    }

    /**
     * 获取[加载中]页面
     *
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存参数
     * @return [加载中]页面
     */
    public View getLoadingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != mConfig) {
            int loadingViewResId = mConfig.getLoadingViewLayoutId();
            if (loadingViewResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID) {
                return inflater.inflate(loadingViewResId, container, false);
            }
        }
        View loadingView = inflater.inflate(R.layout.layout_def_loading, container, false);
        return loadingView;
    }

    /**
     * 获取[空数据]页面
     *
     * @param face               当前页面
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存参数
     * @return [空数据]页面
     */
    public View getEmptyView(final PagerFace face, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View emptyView = null;
        View retryView = null;

        //如果设置过页面参数，就以该参数为主
        if (null != mConfig) {
            //获取界面View
            int emptyViewResId = mConfig.getEmptyViewResId();
            if (emptyViewResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID) {
                emptyView = inflater.inflate(emptyViewResId, container, false);
            }
            //获取重试按钮View
            int retryResId = mConfig.getEmptyViewRetryResId();
            if (retryResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID && null != emptyView) {
                retryView = emptyView.findViewById(retryResId);
            }
        }

        //如果没有设置过页面参数，就使用默认的页面
        if (null == emptyView) {
            emptyView = inflater.inflate(R.layout.layout_def_empty, container, false);
        }
        if (null == retryView) {
            //默认将整个默认页面设置为重试View
            retryView = emptyView;
        }

        //设置重试加载事件
        retryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                face.shouldLoadData();
            }
        });
        return emptyView;
    }

    /**
     * 获取[错误]页面
     *
     * @param face               当前页面
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存参数
     * @return [错误]页面
     */
    public View getErrorView(final PagerFace face, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View errorView = null;
        View retryView = null;

        //如果设置过页面参数，就以该参数为主
        if (null != mConfig) {
            //获取界面View
            int errorViewResId = mConfig.getErrorViewResId();
            if (errorViewResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID) {
                errorView = inflater.inflate(errorViewResId, container, false);
            }
            //获取重试按钮View
            int retryResId = mConfig.getErrorViewRetryResId();
            if (retryResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID && null != errorView) {
                retryView = errorView.findViewById(retryResId);
            }
        }

        //如果没有设置过页面参数，就使用默认的页面
        if (null == errorView) {
            errorView = inflater.inflate(R.layout.layout_def_error, container, false);
        }
        if (null == retryView) {
            //默认将整个默认页面设置为重试View
            retryView = errorView;
        }

        //设置重试加载事件
        retryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                face.shouldLoadData();
            }
        });
        return errorView;
    }

    /**
     * 请求数据
     */
    public void shouldLoadData() {
        // 当页面初始化完成并且显示后才能往下执行
        if (null != mDispatch && mIsVisible) {
            mDispatch.shouldLoadData();
        }
    }

    /**
     * 获取到用来发送页面切换通知消息的handler
     *
     * @return 用来发送消息的handler
     */
    public ResultHandler getRltHandler() {
        return null != mDispatch ? mDispatch.getRltHandler() : null;
    }

    /**
     * 将页面切换为[加载中]状态
     */
    public void onLoading() {
        getRltHandler().onLoading();
    }

    /**
     * 将页面切换为[成功]状态
     */
    public void onSuccess() {
        getRltHandler().onSuccess();
    }

    /**
     * 将页面切换为[空数据]状态
     */
    public void onEmpty() {
        getRltHandler().onEmpty();
    }

    /**
     * 将页面切换为[错误]状态
     */
    public void onError() {
        getRltHandler().onError();
    }
}
