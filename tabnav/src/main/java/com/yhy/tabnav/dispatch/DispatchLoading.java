package com.yhy.tabnav.dispatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.yhy.tabnav.handler.ResultHandler;
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
 * desc   : 用于控制分发页面，根据不同的状态确定该显示的页面
 */
public abstract class DispatchLoading extends FrameLayout {
    private static final String TAG = "DispatchLoading";

    //用来更新UI的Handler(为了提高更新UI的安全性，这里就用Handler来更新UI)
    private Handler mHandler;

    //用来回调发送结果状态的ResultHandler对象
    private ResultHandler mResultHandler;

    //加载中页面
    private View mLoadingView;
    //加载错误页面
    private View mErrorView;
    //空数据页面
    private View mEmptyView;
    //成功页面
    private View mSuccessView;

    // 加载中拦截器
    private LoadingInterceptor mLoadingInterceptor;
    // 空数据拦截器
    private EmptyInterceptor mEmptyInterceptor;
    // 错误拦截器
    private ErrorInterceptor mErrorInterceptor;
    // 成功拦截器
    private SuccessInterceptor mSuccessInterceptor;

    //当前页面的状态
    private int mCurrentState;

    public DispatchLoading(Context context) {
        this(context, null);
    }

    public DispatchLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DispatchLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    @SuppressLint("HandlerLeak")
    private void init() {
        //初始化数据
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //只有要更新的状态不等于当前状态时，才有效
                if (null != msg && msg.what != mCurrentState) {
                    //更新状态
                    mCurrentState = msg.what;
                    //更新UI
                    updateUI();
                }
            }
        };

        //创建结果集Handler
        mResultHandler = new ResultHandler(mHandler);

        // 获取拦截器
        if (null == mLoadingInterceptor) {
            mLoadingInterceptor = getLoadingInterceptor();
        }
        if (null == mEmptyInterceptor) {
            mEmptyInterceptor = getEmptyInterceptor();
        }
        if (null == mErrorInterceptor) {
            mErrorInterceptor = getErrorInterceptor();
        }
        if (null == mSuccessInterceptor) {
            mSuccessInterceptor = getSuccessInterceptor();
        }

        //获取具体页面
        if (null == mLoadingView) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
            mLoadingView.setVisibility(mLoadingInterceptor.processAhead() ? VISIBLE : GONE);
        }
        if (null == mErrorView) {
            mErrorView = getErrorView();
            addView(mErrorView);
            mErrorView.setVisibility(GONE);
        }
        if (null == mEmptyView) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
            mEmptyView.setVisibility(GONE);
        }
        if (null == mSuccessView) {
            mSuccessView = getSuccessView();
            addView(mSuccessView);
            mSuccessView.setVisibility(GONE);
        }

        //当前状态为默认状态
        mCurrentState = TpgConst.LoadingStatus.STATE_DEFAULT;
    }

    /**
     * 获取加载中拦截器
     *
     * @return 加载中拦截器
     */
    protected abstract LoadingInterceptor getLoadingInterceptor();

    /**
     * 获取空数据拦截器
     *
     * @return 空数据拦截器
     */
    protected abstract EmptyInterceptor getEmptyInterceptor();

    /**
     * 获取错误拦截器
     *
     * @return 错误拦截器
     */
    protected abstract ErrorInterceptor getErrorInterceptor();

    /**
     * 获取成功拦截器
     *
     * @return 成功拦截器
     */
    protected abstract SuccessInterceptor getSuccessInterceptor();

    /**
     * 获取到成功页面
     *
     * @return 成功页面
     */
    protected abstract View getSuccessView();

    /**
     * 获取到加载中页面
     *
     * @return 加载中页面
     */
    protected abstract View getLoadingView();

    /**
     * 获取到加载错误页面
     *
     * @return 加载错误页面
     */
    protected abstract View getErrorView();

    /**
     * 获取到数据为空页面
     *
     * @return 数据为空页面
     */
    protected abstract View getEmptyView();

    /**
     * 初始化数据，用来回调具体页面的初始化数据方法
     */
    protected abstract void initData();

    /**
     * 判断是否应该加载数据，应该（当前状态不是成功）的话就加载
     */
    public void shouldLoadData() {
        if (mCurrentState != TpgConst.LoadingStatus.STATE_SUCCESS && mCurrentState != TpgConst.LoadingStatus.STATE_LOADING) {
            //如果当前状态不是成功状态或者加载中状态，就把当前状态改为加载中状态，请求数据并更新UI
            mResultHandler.onLoading();

            //回调页面中的加载数据方法
            initData();
        }
    }

    /**
     * 更新UI
     */
    private void updateUI() {
        // 加载中
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mCurrentState == TpgConst.LoadingStatus.STATE_LOADING && mLoadingInterceptor.processAhead() ? VISIBLE : GONE);
        }
        if (null != mLoadingView) {
            mEmptyView.setVisibility(mCurrentState == TpgConst.LoadingStatus.STATE_EMPTY && mEmptyInterceptor.processAhead() ? VISIBLE : GONE);
        }
        if (null != mLoadingView) {
            mErrorView.setVisibility(mCurrentState == TpgConst.LoadingStatus.STATE_ERROR && mErrorInterceptor.processAhead() ? VISIBLE : GONE);
        }
        if (null != mLoadingView) {
            mSuccessView.setVisibility(mCurrentState == TpgConst.LoadingStatus.STATE_SUCCESS && mSuccessInterceptor.processAhead() ? VISIBLE : GONE);
        }

        // 加上拦截器后，改用以上方法切换页面
//        if (null != mLoadingView) {
//            mLoadingView.setVisibility(mCurrentState == TpgConst.LoadingStatus.STATE_LOADING ? VISIBLE : GONE);
//        }
//        if (null != mErrorView) {
//            mErrorView.setVisibility(mCurrentState == TpgConst.LoadingStatus.STATE_ERROR ? VISIBLE : GONE);
//        }
//        if (null != mEmptyView) {
//            mEmptyView.setVisibility(mCurrentState == TpgConst.LoadingStatus.STATE_EMPTY ? VISIBLE : GONE);
//        }
//        if (null != mSuccessView) {
//            mSuccessView.setVisibility(mCurrentState == TpgConst.LoadingStatus.STATE_SUCCESS ? VISIBLE : GONE);
//        }
    }

    /**
     * 获取用来回调发送结果状态的ResultHandler对象
     *
     * @return 用来回调发送结果状态的ResultHandler对象
     */
    public ResultHandler getRltHandler() {
        return mResultHandler;
    }
}
