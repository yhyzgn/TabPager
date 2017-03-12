package com.yhy.tpg.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.yhy.tpg.R;
import com.yhy.tpg.adapter.NavAdapter;
import com.yhy.tpg.cache.PagerCache;
import com.yhy.tpg.listener.OnPageChangedListener;
import com.yhy.tpg.pager.TpgFragment;
import com.yhy.tpg.utils.DensityUtils;
import com.yhy.tpg.widget.base.TpgInterface;

import java.nio.channels.Selector;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;

/**
 * Created by HongYi Yan on 2017/3/11 23:02.
 */
public class NavView extends RelativeLayout implements TpgInterface {
    private ViewPager vpContent;
    private RadioGroup rgTabs;

    //是否手动滑动过ViewPager（用于区分是ViewPager联动RadioGroup还是RadioGroup联动ViewPager）
    private boolean mDragScrolledFlag = false;

    //页面缓存
    private PagerCache mCache;

    //页面切换事件
    private OnPageChangedListener mPageChangedListener;
    private int mNavBgColor;
    private Drawable mNavBgImg;
    private int mNavTextDefaultColor;
    private int mNavTextCheckedColor;

    public NavView(Context context) {
        this(context, null);
    }

    public NavView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.NavViewAttrs);

        mNavBgColor = ta.getColor(R.styleable.NavViewAttrs_nav_bg_color, Color.WHITE);

        mNavBgImg = ta.getDrawable(R.styleable.NavViewAttrs_nav_bg_img);
        mNavTextDefaultColor = ta.getColor(R.styleable.NavViewAttrs_nav_text_default_color, Color
                .BLACK);
        mNavTextCheckedColor = ta.getColor(R.styleable.NavViewAttrs_nav_text_checked_color, Color
                .BLACK);

        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //获取控件
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_nav, this);
        vpContent = (ViewPager) view.findViewById(R.id.vp_content);
        rgTabs = (RadioGroup) view.findViewById(R.id.rg_tabs);

        //如果同时设置了颜色和图片做背景，以图片为主
        rgTabs.setBackgroundColor(mNavBgColor);
        if (null != mNavBgImg) {
            rgTabs.setBackgroundDrawable(mNavBgImg);
        }
    }

    /**
     * 设置适配器
     *
     * @param adapter 适配器
     */
    public void setAdapter(NavAdapter adapter) {
        //动态生成菜单
        int pageCount = adapter.getCount();
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams
                .WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT, 1);
        BGABadgeRadioButton tab = null;
        int drawablePadding = DensityUtils.dp2px(getContext(), 2);
        int padding = DensityUtils.dp2px(getContext(), 4);
        //先移除所有的菜单项
        rgTabs.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            tab = new BGABadgeRadioButton(getContext());
            tab.setLayoutParams(params);
            tab.setButtonDrawable(null);
            tab.setCompoundDrawablePadding(drawablePadding);
            tab.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(adapter
                    .getTabIconId(i)), null, null);
            tab.setGravity(Gravity.CENTER);
            tab.setText(adapter.getPageTitle(i));
            tab.setPadding(padding, padding, padding, padding);
            tab.setTextSize(12);
            tab.setTextColor(mNavTextDefaultColor);
            rgTabs.addView(tab, i);
        }

        //从适配器获取页面缓存
        mCache = adapter.getPagerCache();

        //设置ViewPager适配器
        vpContent.setAdapter(adapter);

        //设置默认选中的菜单选项
        rgTabs.check(rgTabs.getChildAt(vpContent.getCurrentItem()).getId());
        ((BGABadgeRadioButton) rgTabs.getChildAt(vpContent.getCurrentItem())).setTextColor
                (mNavTextCheckedColor);

        //初始化事件
        initListener();

        //绑定适配器与BtgView，为了在适配器中能获取到BtgView中的某些数据，比如当前页面
        adapter.bindTpgView(this);
    }

    private void initListener() {
        //触摸事件，便于获取到是否是手动滑动的标识
        vpContent.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDragScrolledFlag = true;
                return false;
            }
        });

        //页面切换事件
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                if (null != mPageChangedListener) {
                    mPageChangedListener.onPageScrolled(position, positionOffset,
                            positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                //页面切换后设置按钮选中状态

                //如果是手动滑动ViewPager，就需要联动RadioGroup，并在联动后将该标识设置为初始值
                if (mDragScrolledFlag && position != getTabIndexByResId(rgTabs
                        .getCheckedRadioButtonId())) {
                    //联动RadioGroup
                    rgTabs.check(rgTabs.getChildAt(position).getId());
                }

                TpgFragment page = mCache.getPager(position);
                if (null != page) {
                    page.shouldLoadData();
                }
                if (null != mPageChangedListener) {
                    mPageChangedListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (null != mPageChangedListener) {
                    mPageChangedListener.onPageScrollStateChanged(state);
                }
            }
        });

        //RadioGroup选中事件
        rgTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //底部菜单点击改变时设置页面选中状态
                int currentIndex = getTabIndexByResId(checkedId);

                for (int i = 0; i < rgTabs.getChildCount(); i++) {
                    if (i == currentIndex) {
                        ((BGABadgeRadioButton) rgTabs.getChildAt(currentIndex)).setTextColor
                                (mNavTextCheckedColor);
                    } else {
                        ((BGABadgeRadioButton) rgTabs.getChildAt(i)).setTextColor
                                (mNavTextDefaultColor);
                    }
                }

                //如果不是手动滑动ViewPager时，才联动ViewPager
                if (!mDragScrolledFlag && currentIndex != vpContent.getCurrentItem()) {
                    vpContent.setCurrentItem(currentIndex, true);
                }

                //将标识设置为初始值
                mDragScrolledFlag = false;
            }
        });
    }

    /**
     * 设置页面切换监听器
     *
     * @param listener 页面切换监听器
     */
    public void setOnPageChangedListener(OnPageChangedListener listener) {
        mPageChangedListener = listener;
    }

    /**
     * 设置当前页
     *
     * @param index 页面索引
     */
    @Override
    public void setCurrentPager(int index) {
        //由于手动滑动标识在RadioGroup的监听事件中重置，所以这里最好通过RadioGroup来设置当前页
//        vpContent.setCurrentItem(index, true);
        rgTabs.check(rgTabs.getChildAt(index).getId());
    }

    /**
     * 获取当前页面
     */
    @Override
    public int getCurrentPager() {
        return vpContent.getCurrentItem();
    }

    private int getTabIndexByResId(int resId) {
        for (int i = 0; i < rgTabs.getChildCount(); i++) {
            if (resId == rgTabs.getChildAt(i).getId()) {
                return i;
            }
        }
        return -1;
    }
}
