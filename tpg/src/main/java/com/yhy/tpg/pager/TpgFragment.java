package com.yhy.tpg.pager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.tpg.R;
import com.yhy.tpg.dispatch.DispatchLoading;
import com.yhy.tpg.config.PagerConfig;
import com.yhy.tpg.global.Const;
import com.yhy.tpg.handler.ResultHandler;
import com.yhy.tpg.utils.ViewUtils;

/**
 * 所有页面的父类
 * Created by 颜洪毅 on 2016/12/22 00:22.
 */
public abstract class TpgFragment extends Fragment {
    //每个页面中分发页面的对象
    private DispatchLoading mDispatch;
    //页面配置
    private PagerConfig mConfig;
    //结果集Handler对象
    private ResultHandler mHandler;
    //当前Activity对象
    public Activity mActivity;

    /**
     * 设置当前页面的一些参数，比如错误页面之类等
     *
     * @param config 页面参数
     */
    public void setPagerConfig(PagerConfig config) {
        mConfig = config;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        //如果mDispatch为空，就创建，否则就不创建。
        //由于ViewPager的页面缓存特性，它只会缓存当前页面及其前后各一页（共3页）的页面。
        //当mDispatch不为空时，直接把mDispatc返回就能达到不重复创建mDispatch的效果。
        if (null == mDispatch) {
            //创建mDispatch对象，实现其抽象方法，并回调页面中相应的抽象方法
            mDispatch = new DispatchLoading(mActivity) {
                @Override
                public View getSuccessView() {
                    return TpgFragment.this.getSuccessView();
                }

                @Override
                public View getLoadingView() {
                    return TpgFragment.this.getLoadingView();
                }

                @Override
                public View getErrorView() {
                    return TpgFragment.this.getErrorView();
                }

                @Override
                public View getEmptyView() {
                    return TpgFragment.this.getEmptyView();
                }

                @Override
                public void initData(ResultHandler handler) {
                    mHandler = handler;
                    TpgFragment.this.initData(mHandler);
                }
            };
        } else {
            //由于一个View不能同时有两个parent，而当mDispatch不为空时说明当前页面（View）已经添加过给其他parent了，
            //所以这里需要把mDispatch从原来的parent中移除
            ViewUtils.removeFromParent(mDispatch);
        }
        return mDispatch;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //如果需要首先加载该页面数据，就自动调用shouldLoadData()方法加载数据
        if (shouldLoadDataAtFirst()) {
            shouldLoadData();
        }

        //初始化事件一些事件监听
        initListener();
    }

    /**
     * 获取加载成功页面
     *
     * @return 加载成功页面
     */
    protected abstract View getSuccessView();

    /**
     * 获取加载中页面
     * 先从全局配置中获取，如果未设置的话再使用默认页面
     *
     * @return 加载中页面
     */
    protected View getLoadingView() {
        if (null != mConfig) {
            int loadingViewResId = mConfig.getLoadingViewResId();
            if (loadingViewResId > Const.PagerResIdDef.PAGER_NO_RES_ID) {
                return LayoutInflater.from(mConfig.getContext()).inflate(loadingViewResId, null);
            }
        }
        View loadingView = LayoutInflater.from(mActivity).inflate(R.layout.layout_def_loading,
                null);
        return loadingView;
    }

    /**
     * 获取加载错误页面
     * 先从全局配置中获取，如果未设置的话再使用默认页面
     *
     * @return 错误页面
     */
    protected View getErrorView() {
        View errorView = null;
        View retryView = null;

        //如果设置过页面参数，就以该参数为主
        if (null != mConfig) {
            //获取界面View
            int errorViewResId = mConfig.getErrorViewResId();
            if (errorViewResId > Const.PagerResIdDef.PAGER_NO_RES_ID) {
                errorView = LayoutInflater.from(mConfig.getContext()).inflate(errorViewResId, null);
            }
            //获取重试按钮View
            int retryResId = mConfig.getErrorViewRetryResId();
            if (retryResId > Const.PagerResIdDef.PAGER_NO_RES_ID && null != errorView) {
                retryView = errorView.findViewById(retryResId);
            }
        }

        //如果没有设置过页面参数，就使用默认的页面
        if (null == errorView) {
            errorView = LayoutInflater.from(mActivity).inflate(R.layout.layout_def_error, null);
        }
        if (null == retryView) {
            //默认将整个默认页面设置为重试View
            retryView = errorView;
        }

        //设置重试加载事件
        retryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shouldLoadData();
            }
        });
        return errorView;
    }

    /**
     * 获取空数据页面
     * 先从全局配置中获取，如果未设置的话再使用默认页面
     *
     * @return 空数据页面
     */
    protected View getEmptyView() {
        View emptyView = null;
        View retryView = null;

        //如果设置过页面参数，就以该参数为主
        if (null != mConfig) {
            //获取界面View
            int emptyViewResId = mConfig.getEmptyViewResId();
            if (emptyViewResId > Const.PagerResIdDef.PAGER_NO_RES_ID) {
                emptyView = LayoutInflater.from(mConfig.getContext()).inflate(emptyViewResId, null);
            }
            //获取重试按钮View
            int retryResId = mConfig.getErrorViewRetryResId();
            if (retryResId > Const.PagerResIdDef.PAGER_NO_RES_ID && null != emptyView) {
                retryView = emptyView.findViewById(retryResId);
            }
        }

        //如果没有设置过页面参数，就使用默认的页面
        if (null == emptyView) {
            emptyView = LayoutInflater.from(mActivity).inflate(R.layout.layout_def_empty, null);
        }
        if (null == retryView) {
            //默认将整个默认页面设置为重试View
            retryView = emptyView;
        }

        //设置重试加载事件
        retryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shouldLoadData();
            }
        });
        return emptyView;
    }

    /**
     * 初始化数据，DispatchLoading中的initData()方法就回调该方法。
     * 在子类中实现该方法。
     * 注意：必须在该方法中加载数据完成(无论成功与否)以后调用refresh(DispatchLoading.STATE state)
     * 方法刷新加载结果状态，否则不会自动更新UI。
     *
     * @param handler 子类用来发送结果状态的ResultHandler对象
     */
    protected abstract void initData(ResultHandler handler);

    /**
     * 子类初始化一些事件监听
     */
    protected void initListener() {
    }

    /**
     * 判断是否应该加载数据(交给mDispatch处理)
     */
    public void shouldLoadData() {
        if (null != mDispatch) {
            mDispatch.shouldLoadData();
        }
    }

    /**
     * 是否需要首先加载该页面（比如第一个页面，子类重写该方法，并返回true；其他页面无需重写）
     * 由于加载数据时在ViewPager的页面切换时加载的，而第一次加载页面时不会引起页面切换，
     * 故不会引起数据加载，所以这里需要判断是不是第一页，是的话就在onActivityCreated方法中加载数据。
     *
     * @return 是否需要首先加载该页面
     */
    public boolean shouldLoadDataAtFirst() {
        return false;
    }

    /**
     * 重新加载数据，主要为了能在适配器中直接重新加载
     *
     * @param args 重新加载数据时可能需要的参数
     */
    public void reloadDate(Object... args) {
        if (null != mHandler) {
            //发送加载中消息
            mHandler.sendLoadingHandler();
        }
    }
}
