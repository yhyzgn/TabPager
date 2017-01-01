package com.yhy.tpg.dispatch;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

/**
 * 用于控制分发页面，根据不同的状态确定该显示的页面
 * Created by 颜洪毅 on 2016/12/22 0022.
 */
public abstract class DispatchLoading extends FrameLayout {
    private static final String TAG = "DispatchLoading";

    //用来给Handler发送更新UI消息的标记
    private static final int UPDATE_UI_HANDLER_WHAT = 1000;

    //4种不同的页面状态
    //加载中...
    private static final int STATE_LOADING = 100;
    //加载错误
    private static final int STATE_ERROR = 101;
    //加载成功，但数据为空
    private static final int STATE_EMPTY = 102;
    //加载成功，且数据有效
    private static final int STATE_SUCCESS = 103;

    //用来更新UI的Handler(为了提高更新UI的安全性，这里就用Handler来更新UI)
    private Handler mHandler;

    //加载中页面
    private View mLoadingView;
    //加载错误页面
    private View mErrorView;
    //空数据页面
    private View mEmptyView;
    //成功页面
    private View mSuccessView;

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
    private void init() {
        //初始化数据
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateUI();
            }
        };

        //获取具体页面
        if (null == mLoadingView) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
            mLoadingView.setVisibility(VISIBLE);
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

        //当前状态初始值
        mCurrentState = STATE_LOADING;
        //这里不引发界面更新，在ViewPager界面发生变化时调用shouldLoadData()方法更新界面
//        mHandler.sendEmptyMessage(UPDATE_UI_HANDLER_WHAT);
    }

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
        if (mCurrentState != STATE_SUCCESS) {
            //如果当前状态不是成功状态，就把当前状态改为加载中状态，并更新UI
            mCurrentState = STATE_LOADING;
            mHandler.sendEmptyMessage(UPDATE_UI_HANDLER_WHAT);

            //回调页面中的加载数据方法
            initData();
        }
    }

    /**
     * 更新UI
     */
    private void updateUI() {
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mCurrentState == STATE_LOADING ? VISIBLE : GONE);
            Logger.t(TAG).i("mLoadingView Visible = " + mLoadingView.getVisibility());
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(mCurrentState == STATE_ERROR ? VISIBLE : GONE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? VISIBLE : GONE);
        }
        if (null != mSuccessView) {
            mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? VISIBLE : GONE);
        }
    }

    /**
     * 根据加载结果状态刷新UI
     *
     * @param state 加载结构的状态
     */

    public void refresh(STATE state) {
        mCurrentState = state.getState();
        mHandler.sendEmptyMessage(UPDATE_UI_HANDLER_WHAT);
    }

    /**
     * 所有状态的枚举值（便于限制用户行为，只能从这几个值中选择）
     */
    public enum STATE {
        //        LOADING(STATE_LOADING),
        //加载中状态的枚举值这里不需要设置，因为这个状态不是由页面加载结构决定的，也不可以手动设置
        ERROR(STATE_ERROR), EMPTY(STATE_EMPTY), SUCCESS(STATE_SUCCESS);

        private int state;

        STATE(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
