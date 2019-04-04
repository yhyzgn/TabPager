package com.yhy.tabnav.tpg;

import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-11-06 8:23
 * version: 1.0.0
 * desc   : ViewPager接口
 */
public interface Pager {

    /**
     * 获取当前ViewPager
     *
     * @return 当前ViewPager
     */
    ViewPager getViewPager();

    /**
     * 控制页面是否可滑动
     *
     * @param scrollAble 是否可滑动
     */
    void setScrollAble(boolean scrollAble);

    /**
     * 获取父类事件拦截器
     *
     * @param ev 事件
     * @return 拦截结果
     */
    boolean onSuperInterceptTouchEvent(MotionEvent ev);

    /**
     * 获取父类事件处理器
     *
     * @param ev 事件
     * @return 处理结果
     */
    boolean onSuperTouchEvent(MotionEvent ev);
}
