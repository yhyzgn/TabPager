package com.yhy.tabnav.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:09
 * version: 1.0.0
 * desc   : 可控制是否可滑动的ViewPager
 */
public class TpgViewPager extends ViewPager {
    // 默认是可滑动的
    private boolean mScrollAble = true;

    public TpgViewPager(Context context) {
        super(context);
    }

    public TpgViewPager(Context context, AttributeSet attrs) {
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
        // 处理缩放滑动异常
        try {
            //如果不允许滑动，就直接拦截事件
            if (!mScrollAble) {
                // 彻底解决滑动冲突(将事件都交于子View处理)
                return false;
            }
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 设置禁止滑动 触发触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 处理缩放滑动异常
        try {
            if (!mScrollAble) {
                return false;//禁止滑动
            }
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
