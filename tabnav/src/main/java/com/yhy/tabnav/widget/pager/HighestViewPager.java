package com.yhy.tabnav.widget.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yhy.tabnav.helper.PagerHelper;
import com.yhy.tabnav.tpg.Pager;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-11-06 9:50
 * version: 1.0.0
 * desc   : ViewPager按最高方式显示
 */
public class HighestViewPager extends ViewPager implements Pager {

    private PagerHelper mHelper;

    public HighestViewPager(Context context) {
        this(context, null);
    }

    public HighestViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new PagerHelper(this);
    }

    @Override
    public ViewPager getViewPager() {
        return this;
    }

    @Override
    public void setScrollAble(boolean scrollAble) {
        mHelper.setScrollAble(scrollAble);
    }

    @Override
    public boolean onSuperInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onSuperTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
