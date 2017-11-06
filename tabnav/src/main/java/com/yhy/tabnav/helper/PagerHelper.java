package com.yhy.tabnav.helper;

import android.view.MotionEvent;

import com.yhy.tabnav.tpg.Pager;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-11-06 8:27
 * version: 1.0.0
 * desc   : ViewPager辅助类
 */
public class PagerHelper {
    // ViewPager接口
    private Pager mPager;
    // 是否可滑动
    private boolean mScrollAble;

    /**
     * 构造函数
     *
     * @param pager 当前实现的Pager接口
     */
    public PagerHelper(Pager pager) {
        mPager = pager;
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
     *
     * @param ev 事件
     * @return 拦截结果
     */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 处理缩放滑动异常
        try {
            //如果不允许滑动，就直接拦截事件
            if (!mScrollAble) {
                // 彻底解决滑动冲突(将事件都交于子View处理)
                return false;
            }
            return mPager.onSuperInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 设置禁止滑动 触发触摸事件
     *
     * @param ev 事件
     * @return 处理结果
     */
    public boolean onTouchEvent(MotionEvent ev) {
        // 处理缩放滑动异常
        try {
            if (!mScrollAble) {
                return false;//禁止滑动
            }
            return mPager.onSuperTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
