package com.yhy.tabnav.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.yhy.tabnav.R;
import com.yhy.tabnav.adapter.NavAdapter;
import com.yhy.tabnav.cache.PagerCache;
import com.yhy.tabnav.listener.OnPageChangedListener;
import com.yhy.tabnav.tpg.Badge;
import com.yhy.tabnav.tpg.Pager;
import com.yhy.tabnav.tpg.Tpg;
import com.yhy.tabnav.utils.DensityUtils;
import com.yhy.tabnav.utils.ViewUtils;
import com.yhy.tabnav.widget.pager.TpgViewPager;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import cn.bingoogolapple.badgeview.BGABadgeable;
import cn.bingoogolapple.badgeview.BGADragDismissDelegate;
import cn.bingoogolapple.badgeview.annotation.BGABadge;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:08
 * version: 1.0.0
 * desc   : 用于底部导航栏布局页面
 */
@BGABadge(RadioButton.class)
public class NavView extends RelativeLayout implements Tpg, Badge {
    // ViewPager的显示区域
    private FrameLayout flContent;
    // 显示内容的ViewPager
    private ViewPager vpContent;
    // 分割线
    private View vDivider;
    // 导航栏
    private RadioGroup rgTabs;
    //页面缓存
    private PagerCache mCache;
    //页面切换事件
    private OnPageChangedListener mPageChangedListener;
    //导航栏高度，默认为48dp
    private int mNavHeight;
    //整个导航栏背景色，默认为#ffffff
    private int mNavBgColor;
    //整个导航栏背景图，默认为null
    private Drawable mNavBgImg;
    //导航栏默认字体颜色，默认为#000000
    private int mNavTextDefaultColor;
    //导航栏选中的字体颜色，默认为#000000
    private int mNavTextCheckedColor;
    //导航栏选中的背景色，默认为透明
    private int mNavBgCheckedColor;
    //导航栏选中的背景图，默认为null
    private Drawable mNavBgCheckedImg;
    //导航栏与页面之间的分割线颜色，默认为透明，此时分割线不显示
    private int mNavDividerLineColor;
    //是否可滑动，默认可滑动
    private boolean mScrollAble;
    // 徽章背景颜色，默认：#ffff2200
    private int mBadgeBgColor;
    // 徽章字体颜色，默认：#ffffffff
    private int mBadgeTextColor;

    public NavView(Context context) {
        this(context, null);
    }

    public NavView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化一些属性
     *
     * @param context 上下文对象
     * @param attrs   属性集
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.NavView);
        mNavHeight = (int) ta.getDimensionPixelSize(R.styleable.NavView_nav_height, DensityUtils.dp2px(context, 48));
        mNavBgColor = ta.getColor(R.styleable.NavView_nav_bg_color, Color.TRANSPARENT);
        mNavBgImg = ta.getDrawable(R.styleable.NavView_nav_bg_img);
        mNavTextDefaultColor = ta.getColor(R.styleable.NavView_nav_text_default_color, Color.BLACK);
        mNavTextCheckedColor = ta.getColor(R.styleable.NavView_nav_text_checked_color, Color.BLACK);
        mNavBgCheckedColor = ta.getColor(R.styleable.NavView_nav_bg_checked_color, Color.TRANSPARENT);
        mNavBgCheckedImg = ta.getDrawable(R.styleable.NavView_nav_bg_checked_img);
        mNavDividerLineColor = ta.getColor(R.styleable.NavView_nav_divider_line_color, Color.TRANSPARENT);
        mScrollAble = ta.getBoolean(R.styleable.NavView_nav_scroll_able, true);
        mBadgeBgColor = ta.getColor(R.styleable.NavView_nav_badge_bg_color, Color.parseColor("#ffff2200"));
        mBadgeTextColor = ta.getColor(R.styleable.NavView_nav_badge_text_color, Color.WHITE);

        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 动态配置ViewPager
        if (getChildCount() == 1) {
            // 如果有一个子控件，就尝试把它当作ViewPager
            View child = getChildAt(0);
            if (child instanceof ViewPager && child instanceof Pager) {
                vpContent = (ViewPager) child;
            } else {
                throw new IllegalStateException("NavView must has 0 or 1 child that implement 'Pager' interface and exptend 'ViewPager' class at same time.");
            }
        } else {
            // 否则默认创建
            vpContent = new TpgViewPager(getContext());
        }
        // 设置默认Id
        if (vpContent.getId() == View.NO_ID) {
            vpContent.setId(vpContent.hashCode());
        }

        //获取控件
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_nav, this);
        vDivider = view.findViewById(R.id.v_divider);
        rgTabs = view.findViewById(R.id.rg_tabs);
        flContent = view.findViewById(R.id.fl_content);

        // 将ViewPager添加到界面
        if (null != vpContent) {
            ViewUtils.removeFromParent(vpContent);
            flContent.addView(vpContent);
        }

        setNavHeight((int) DensityUtils.px2dp(getContext(), mNavHeight));

        //设置整个导航栏的背景。如果同时设置了颜色和图片做背景，以图片为主
        rgTabs.setBackgroundColor(mNavBgColor);
        if (null != mNavBgImg) {
            rgTabs.setBackgroundDrawable(mNavBgImg);
        }

        if (mNavDividerLineColor == Color.TRANSPARENT) {
            //如果颜色透明，就隐藏分割线
            vDivider.setVisibility(View.GONE);
        } else {
            //否则就显示分割线，并设置相应颜色
            vDivider.setBackgroundColor(mNavDividerLineColor);
            vDivider.setVisibility(View.VISIBLE);
        }

        //设置是否可滑动
        setScrollAble(mScrollAble);
    }

    /**
     * 设置整个导航栏高度
     *
     * @param dpHeight 导航栏高度
     */
    public void setNavHeight(int dpHeight) {
        mNavHeight = DensityUtils.dp2px(getContext(), dpHeight);
        ViewGroup.LayoutParams params = rgTabs.getLayoutParams();
        params.height = mNavHeight;
        rgTabs.setLayoutParams(params);
    }

    /**
     * 设置是否可滑动，默认可滑动
     *
     * @param scrollAble 是否可滑动
     */
    public void setScrollAble(boolean scrollAble) {
        mScrollAble = scrollAble;
        if (vpContent instanceof Pager) {
            ((Pager) vpContent).setScrollAble(mScrollAble);
        } else {
            throw new UnsupportedOperationException("Not support setScrollAble unless implement 'Pager' interface.");
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
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT, 1);
        //先移除所有的菜单项
        rgTabs.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            BGABadgeRadioButton tab = (BGABadgeRadioButton) LayoutInflater.from(getContext()).inflate(R.layout.view_nav_tab, null);
            tab.setLayoutParams(params);
            tab.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(adapter.getTabIconId(i)), null, null);
            tab.setText(adapter.getPageTitle(i));
            tab.setTextColor(mNavTextDefaultColor);
            tab.setTag(i);
            tab.getBadgeViewHelper().setBadgeBgColorInt(mBadgeBgColor);
            tab.getBadgeViewHelper().setBadgeTextColorInt(mBadgeTextColor);

            rgTabs.addView(tab, i);
        }

        //从适配器获取页面缓存
        mCache = adapter.getPagerCache();

        //设置ViewPager适配器
        vpContent.setAdapter(adapter);

        //设置默认选中的菜单选项
        ((RadioButton) rgTabs.getChildAt(vpContent.getCurrentItem())).setChecked(true);
        //设置选中项样式
        setTabStyle((RadioButton) rgTabs.getChildAt(vpContent.getCurrentItem()), true);

        //初始化事件
        initListener();

        //绑定适配器与NavView，为了在适配器中能获取到NavView中的某些数据，比如当前页面
        adapter.bindTpgView(this);
    }

    private void initListener() {
        //页面切换事件
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int mPreviousState;
            private int mCurrentStat;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (null != mPageChangedListener) {
                    mPageChangedListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
//                rgTabs.check(rgTabs.getChildAt(position).getId());
                // 上述方法会导致其onCheckedChanged回调方法多次执行，只能用一下方法来设置选中，使onCheckedChanged方法只执行一次
                ((RadioButton) rgTabs.getChildAt(position)).setChecked(true);

                if (null != mPageChangedListener) {
                    mPageChangedListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mPreviousState = mCurrentStat;
                mCurrentStat = state;

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
                int currentIndex = (int) group.findViewById(checkedId).getTag();

                for (int i = 0; i < rgTabs.getChildCount(); i++) {
                    //设置相关值
                    setTabStyle(((RadioButton) rgTabs.getChildAt(i)), i == currentIndex);
                }
                vpContent.setCurrentItem(currentIndex, true);
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
        //设置页面标识设为true
        rgTabs.check(rgTabs.getChildAt(index).getId());
    }

    /**
     * 获取当前页面
     */
    @Override
    public int getCurrentPager() {
        return vpContent.getCurrentItem();
    }

    /**
     * 显示圆点徽章
     *
     * @param index Tab的索引
     */
    @Override
    public void showCirclePointBadge(int index) {
        getTabByIndex(index).showCirclePointBadge();
    }

    /**
     * 显示文本徽章
     *
     * @param index     Tab的索引
     * @param badgeText 显示的文字
     */
    @Override
    public void showTextBadge(int index, String badgeText) {
        getTabByIndex(index).showTextBadge(badgeText);
    }

    /**
     * 隐藏徽章
     *
     * @param index Tab的索引
     */
    @Override
    public void dismissBadge(int index) {
        getTabByIndex(index).hiddenBadge();
    }

    /**
     * 显示图片徽章
     *
     * @param index  Tab的索引
     * @param bitmap 图标
     */
    @Override
    public void showDrawableBadge(int index, Bitmap bitmap) {
        getTabByIndex(index).showDrawableBadge(bitmap);
    }

    /**
     * 徽章是否显示
     *
     * @param index Tab的索引
     * @return 是否显示
     */
    @Override
    public boolean isShowBadge(int index) {
        return getTabByIndex(index).isShowBadge();
    }

    /**
     * 设置徽章背景颜色
     * <p>
     * 作用范围：全部徽章
     *
     * @param color 背景颜色
     */
    public void setBadgeBgColor(int color) {
        setBadgeBgColor(-1, color);
    }

    /**
     * 设置徽章背景颜色
     * <p>
     * 作用范围：指定索引的徽章
     *
     * @param index 徽章索引
     * @param color 背景颜色
     */
    public void setBadgeBgColor(int index, int color) {
        BGABadgeRadioButton tab;
        if (index > -1) {
            tab = getTabByIndex(index);
            if (null != tab) {
                tab.getBadgeViewHelper().setBadgeBgColorInt(color);
            }
        } else {
            int count = rgTabs.getChildCount();
            for (int i = 0; i < count; i++) {
                tab = getTabByIndex(i);
                if (null != tab) {
                    tab.getBadgeViewHelper().setBadgeBgColorInt(color);
                }
            }
        }
    }

    /**
     * 设置徽章字体颜色
     * <p>
     * 作用范围：全部徽章
     *
     * @param color 字体颜色
     */
    public void setBadgeTextColor(int color) {
        setBadgeTextColor(-1, color);
    }

    /**
     * 设置徽章字体颜色
     * <p>
     * 作用范围：指定索引的徽章
     *
     * @param index 徽章索引
     * @param color 字体颜色
     */
    public void setBadgeTextColor(int index, int color) {
        BGABadgeRadioButton tab;
        if (index > -1) {
            tab = getTabByIndex(index);
            if (null != tab) {
                tab.getBadgeViewHelper().setBadgeTextColorInt(color);
            }
        } else {
            int count = rgTabs.getChildCount();
            for (int i = 0; i < count; i++) {
                tab = getTabByIndex(i);
                if (null != tab) {
                    tab.getBadgeViewHelper().setBadgeTextColorInt(color);
                }
            }
        }
    }

    /**
     * 设置徽章销毁时的回调事件
     *
     * @param index    Tab的索引
     * @param listener 回调事件
     */
    @Override
    public void setOnDismissListener(int index, final OnDismissBadgeListener listener) {
        getTabByIndex(index).setDragDismissDelegate(new BGADragDismissDelegate() {
            @Override
            public void onDismiss(BGABadgeable badgeable) {
                if (null != listener) {
                    listener.onDismiss();
                }
            }
        });
    }

    /**
     * 获取当前ViewPager
     *
     * @return 当前ViewPager
     */
    public ViewPager getViewPager() {
        return vpContent;
    }

    /**
     * 设置Tab样式
     *
     * @param rb      Tab项
     * @param checked 是否选中
     */
    private void setTabStyle(RadioButton rb, boolean checked) {
        if (checked) {
            rb.setTextColor(mNavTextCheckedColor);
            if (null == mNavBgCheckedImg) {
                rb.setBackgroundColor(mNavBgCheckedColor);
            } else {
                rb.setBackgroundDrawable(mNavBgCheckedImg);
            }
        } else {
            rb.setTextColor(mNavTextDefaultColor);
            rb.setBackgroundColor(Color.TRANSPARENT);
            rb.setBackgroundDrawable(null);
        }
    }

    /**
     * 按索引获取Tab
     *
     * @param index 索引
     * @return Tab项
     */
    private BGABadgeRadioButton getTabByIndex(int index) {
        int count = rgTabs.getChildCount();
        if (index < 0 || index > count) {
            throw new IllegalArgumentException("The argument index must between 0 and RadioGroup's childCount");
        }
        return (BGABadgeRadioButton) rgTabs.getChildAt(index);
    }
}
