package com.yhy.tabnav.pager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.tabnav.R;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.dispatch.DispatchLoading;
import com.yhy.tabnav.global.TpgConst;
import com.yhy.tabnav.handler.ResultHandler;
import com.yhy.tabnav.helper.TpgHelper;
import com.yhy.tabnav.tpg.PagerFace;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:08
 * version: 1.0.0
 * desc   : 所有页面的父类
 */
public abstract class TpgFragment<RT> extends Fragment implements PagerFace<RT> {
    //当前Activity对象
    public Activity mActivity;
    //结果集Handler对象
    public ResultHandler mRltHandler;
    //页面助手，用于创建各种View，比如错误页面等
    private TpgHelper<RT> mHelper = new TpgHelper<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取Activity
        getPagerActivity(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 为了避免Activity为空，这里需要再次通过context获取Activity
        getPagerActivity(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreatePagerView(inflater, container, savedInstanceState);
        mRltHandler = getRltHandler();
        shouldLoadData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // 记录下当前状态（由于setUserVisibleHint方法再onCreateView之前执行，所以要实现懒加载的话，就再onCreateView方法中手动调用加载数据方法，并在其中判断是否显示）
//        mIsVisible = isVisibleToUser;
        mHelper.onPagerVisible(this, isVisibleToUser);

        // 出发页面切换方法
//        onPagerVisible(mIsVisible);
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void setRoot(RT root) {
//        mRoot = root;
        mHelper.setRoot(root);
    }

    @Override
    public void setPagerConfig(PagerConfig config) {
//        mConfig = config;
        mHelper.setPagerConfig(config);
    }

    @Override
    public void getPagerActivity(Context context) {
        if (null == mActivity) {
//            mActivity = null == context ? getActivity() : (Activity) context;
            mActivity = mHelper.getPagerActivity(context);
        }
    }

    @Override
    public View onCreatePagerView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
//        if (null == mDispatch) {
//            //创建mDispatch对象，实现其抽象方法，并回调页面中相应的抽象方法
//            mDispatch = new DispatchLoading(mActivity) {
//                @Override
//                public View getSuccessView() {
//                    return TpgFragment.this.getSuccessView(inflater, container, savedInstanceState);
//                }
//
//                @Override
//                public View getLoadingView() {
//                    return TpgFragment.this.getLoadingView(inflater, container, savedInstanceState);
//                }
//
//                @Override
//                public View getErrorView() {
//                    return TpgFragment.this.getErrorView(inflater, container, savedInstanceState);
//                }
//
//                @Override
//                public View getEmptyView() {
//                    return TpgFragment.this.getEmptyView(inflater, container, savedInstanceState);
//                }
//
//                @Override
//                public void initData(ResultHandler handler) {
//                    mRltHandler = handler;
//                    // 初始化数据
//                    TpgFragment.this.initData();
//                    // 初始化事件一些事件监听
//                    TpgFragment.this.initListener();
//                }
//            };
//        } else {
//            //由于一个View不能同时有两个parent，而当mDispatch不为空时说明当前页面（View）已经添加过给其他parent了，
//            //所以这里需要把mDispatch从原来的parent中移除
//            ViewUtils.removeFromParent(mDispatch);
//        }
//        return mDispatch;
        return mHelper.onCreatePagerView(this, inflater, container, savedInstanceState);
    }

    @Override
    public void onPagerVisible(boolean isVisible) {
        //页面显示时自动调用shouldLoadData()方法加载数据
        if (isVisible) {
            shouldLoadData();
        }
    }

    @Override
    public View getLoadingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (null != mConfig) {
//            int loadingViewResId = mConfig.getLoadingViewLayoutId();
//            if (loadingViewResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID) {
//                return inflater.inflate(loadingViewResId, container, false);
//            }
//        }
//        View loadingView = inflater.inflate(R.layout.layout_def_loading, container, false);
//        return loadingView;
        return mHelper.getLoadingView(inflater, container, savedInstanceState);
    }

    @Override
    public View getEmptyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View emptyView = null;
//        View retryView = null;
//
//        //如果设置过页面参数，就以该参数为主
//        if (null != mConfig) {
//            //获取界面View
//            int emptyViewResId = mConfig.getEmptyViewResId();
//            if (emptyViewResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID) {
//                emptyView = inflater.inflate(emptyViewResId, container, false);
//            }
//            //获取重试按钮View
//            int retryResId = mConfig.getEmptyViewRetryResId();
//            if (retryResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID && null != emptyView) {
//                retryView = emptyView.findViewById(retryResId);
//            }
//        }
//
//        //如果没有设置过页面参数，就使用默认的页面
//        if (null == emptyView) {
//            emptyView = inflater.inflate(R.layout.layout_def_empty, container, false);
//        }
//        if (null == retryView) {
//            //默认将整个默认页面设置为重试View
//            retryView = emptyView;
//        }
//
//        //设置重试加载事件
//        retryView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shouldLoadData();
//            }
//        });
//        return emptyView;
        return mHelper.getEmptyView(this, inflater, container, savedInstanceState);
    }

    @Override
    public View getErrorView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View errorView = null;
//        View retryView = null;
//
//        //如果设置过页面参数，就以该参数为主
//        if (null != mConfig) {
//            //获取界面View
//            int errorViewResId = mConfig.getErrorViewResId();
//            if (errorViewResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID) {
//                errorView = inflater.inflate(errorViewResId, container, false);
//            }
//            //获取重试按钮View
//            int retryResId = mConfig.getErrorViewRetryResId();
//            if (retryResId > TpgConst.PagerResIdDef.PAGER_NO_RES_ID && null != errorView) {
//                retryView = errorView.findViewById(retryResId);
//            }
//        }
//
//        //如果没有设置过页面参数，就使用默认的页面
//        if (null == errorView) {
//            errorView = inflater.inflate(R.layout.layout_def_error, container, false);
//        }
//        if (null == retryView) {
//            //默认将整个默认页面设置为重试View
//            retryView = errorView;
//        }
//
//        //设置重试加载事件
//        retryView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shouldLoadData();
//            }
//        });
//        return errorView;
        return mHelper.getErrorView(this, inflater, container, savedInstanceState);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void shouldLoadData() {
//        if (null != mDispatch && mHelper.getIsVisible()) {
//            mDispatch.shouldLoadData();
//        }
        mHelper.shouldLoadData();
    }

    @Override
    public void reloadData(Bundle args) {
    }

    @Override
    public ResultHandler getRltHandler() {
//        return mRltHandler;
        return mHelper.getRltHandler();
    }
}
