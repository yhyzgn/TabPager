package com.yhy.tabnav.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by HongYi Yan on 2017/6/9 14:25.
 */
public class CtrlAbleViewPager extends ViewPager {
    //默认是可滑动的
    private boolean mScrollAble = true;

    public CtrlAbleViewPager(Context context) {
        super(context);
    }

    public CtrlAbleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否可滑动，默认可滑动
     *
     * @param scrollAble 是否可滑动
     */
    public void setScrollAble(boolean scrollAble) {
        mScrollAble = scrollAble;
    }

    /**
     * 触发拦截触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果不允许滑动，就直接拦截事件
        if (!mScrollAble) {
            // 彻底解决滑动冲突(将事件都交于子View处理)
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 设置禁止滑动 触发触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mScrollAble) {
            return false;//禁止滑动
        }
        return super.onTouchEvent(ev);
    }
}
