package com.yhy.tabnav.widget.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yhy.tabnav.helper.PagerHelper;
import com.yhy.tabnav.tpg.Pager;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:09
 * version: 1.0.0
 * desc   : 可控制是否可滑动的ViewPager
 */
public class TpgViewPager extends ViewPager implements Pager {
    // ViewPager辅助实例
    private PagerHelper mHelper;

    public TpgViewPager(Context context) {
        this(context, null);
    }

    public TpgViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new PagerHelper(this);
    }

    /**
     * 获取当前ViewPager
     *
     * @return 当前ViewPager
     */
    @Override
    public ViewPager getViewPager() {
        return this;
    }

    /**
     * 控制页面是否可滑动
     *
     * @param scrollAble 是否可滑动
     */
    @Override
    public void setScrollAble(boolean scrollAble) {
        mHelper.setScrollAble(scrollAble);
    }

    /**
     * 获取父类事件拦截器
     *
     * @param ev 事件
     * @return 拦截结果
     */
    @Override
    public boolean onSuperInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 获取父类事件处理器
     *
     * @param ev 事件
     * @return 处理结果
     */
    @Override
    public boolean onSuperTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    /**
     * 触发拦截触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.onInterceptTouchEvent(ev);
    }

    /**
     * 设置禁止滑动 触发触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mHelper.onTouchEvent(ev);
    }
}
