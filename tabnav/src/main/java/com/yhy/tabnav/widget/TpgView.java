package com.yhy.tabnav.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhy.tabnav.R;
import com.yhy.tabnav.adapter.TpgAdapter;
import com.yhy.tabnav.listener.OnPageChangedListener;
import com.yhy.tabnav.tpg.Pager;
import com.yhy.tabnav.tpg.Tpg;
import com.yhy.tabnav.utils.DensityUtils;
import com.yhy.tabnav.utils.ViewUtils;
import com.yhy.tabnav.widget.pager.TpgViewPager;

import java.lang.reflect.Field;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:08
 * version: 1.0.0
 * desc   : 用于顶部选项卡布局类型页面
 */
public class TpgView extends LinearLayout implements Tpg {
    //包含了TabLayout和ImageView的布局
    private RelativeLayout rlTab;
    //TabLayout控件
    private TabLayout tlTabs;
    //TabLayout左边的TextView控件
    private TextView tvText;
    //可扩展的ImageView控件
    private ImageView ivExpand;
    // ViewPager显示区域
    private FrameLayout flContent;
    //ViewPager控件
    private ViewPager vpContent;
    //整个Tab栏的高度，默认48dp
    private int mTabHeight;
    //整个Tab栏的背景颜色，默认透明
    private int mTabBgColor;
    //普通字体颜色，默认#aaff4400
    private int mTabTextNormalColor;
    //选中字体颜色， 默认#ffff2200
    private int mTabTextSelectedColor;
    //选项指示条的颜色，默认#ffff2200
    private int mTabIndicatorColor;
    //选项指示条的高度，默认3dp
    private int mTabIndicatorHeight;
    //TabMode，API 18+有效，默认MODE_SCROLLABLE
    private int mTabMode;
    //RabGravity，默认GRAVITY_FILL
    private int mTabGravity;
    //是否显示左侧TextView控件
    private int mTextVisible;
    //左侧TextView内容
    private String mText;
    //左侧TextView字体颜色，默认#aaff4400
    private int mTextColor;
    //左侧TextView字体大小，默认14sp
    private float mTextSize;
    //左侧TextView左侧边距，默认8dp
    private int mTextMarginLeft;
    //左侧TextView右侧边距，默认8dp
    private int mTextMarginRight;
    //可扩展的图标是否显示，默认VISIBLE
    private int mExpandVisible;
    //可扩展图标资源
    private int mExpandIcon;
    //是否可滑动
    private boolean mScrollAble;

    //扩展图标点击监听事件
    private OnExpandListener mExpandListener;
    //ViewPager页面切换监听事件
    private OnPageChangedListener mPageChangedListener;
    //Tab背景图
    private int mTabBackgroundResId;

    public TpgView(Context context) {
        this(context, null);
    }

    public TpgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TpgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化控件
     *
     * @param context 上下文对象
     * @param attrs   属性集
     */
    private void init(Context context, AttributeSet attrs) {
        //获取自定义属性值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TpgView);
        mTabHeight = ta.getDimensionPixelSize(R.styleable.TpgView_tab_height, DensityUtils.dp2px(context, 48));
        mTabBgColor = ta.getColor(R.styleable.TpgView_tab_bg_color, Color.TRANSPARENT);
        mTabTextNormalColor = ta.getColor(R.styleable.TpgView_tab_text_normal_color, getResources().getColor(R.color.tab_def_normal_color));
        mTabTextSelectedColor = ta.getColor(R.styleable.TpgView_tab_text_selected_color, getResources().getColor(R.color.tab_def_selected_color));
        mTabIndicatorColor = ta.getColor(R.styleable.TpgView_tab_indicator_color, getResources().getColor(R.color.tab_def_indicator_color));
        mTabIndicatorHeight = ta.getDimensionPixelSize(R.styleable.TpgView_tab_indicator_height, DensityUtils.dp2px(context, 3));
        mTabMode = ta.getInt(R.styleable.TpgView_tab_mode, TabLayout.MODE_SCROLLABLE);
        mTabGravity = ta.getInt(R.styleable.TpgView_tab_gravity, TabLayout.GRAVITY_FILL);
        mTextVisible = ta.getInt(R.styleable.TpgView_text_visible, GONE);
        mText = ta.getString(R.styleable.TpgView_text_text);
        mTextColor = ta.getColor(R.styleable.TpgView_text_color, getResources().getColor(R.color.tab_def_normal_color));
        mTextSize = ta.getDimensionPixelSize(R.styleable.TpgView_text_size, DensityUtils.dp2px(context, 14));
        mTextMarginLeft = ta.getDimensionPixelSize(R.styleable.TpgView_text_size, DensityUtils.dp2px(context, 8));
        mTextMarginRight = ta.getDimensionPixelSize(R.styleable.TpgView_text_size, DensityUtils.dp2px(context, 8));
        mExpandVisible = ta.getInt(R.styleable.TpgView_expand_visible, VISIBLE);
        mExpandIcon = ta.getResourceId(R.styleable.TpgView_expand_icon, R.mipmap.ic_expand);
        mScrollAble = ta.getBoolean(R.styleable.TpgView_tab_scroll_able, true);
        mTabBackgroundResId = ta.getResourceId(R.styleable.TpgView_tab_background, -1);

        ta.recycle();
    }

    /**
     * 当View中所有的子控件均被映射成xml后触发
     * 可以在这里获取具体的子控件
     */
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
                throw new IllegalStateException("TpgView must has 0 or 1 child that implement 'Pager' interface and exptend 'ViewPager' class at same time.");
            }
        } else {
            // 否则默认创建
            vpContent = new TpgViewPager(getContext());
        }
        // 设置默认Id
        if (vpContent.getId() == View.NO_ID) {
            vpContent.setId(vpContent.hashCode());
        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_tpg, this);
        rlTab = view.findViewById(R.id.rl_tab);
        tvText = view.findViewById(R.id.tv_text);
        tlTabs = view.findViewById(R.id.tl_tabs);
        ivExpand = view.findViewById(R.id.iv_expand);
        flContent = view.findViewById(R.id.fl_content);

        // 将ViewPager添加到界面
        if (null != vpContent) {
            ViewUtils.removeFromParent(vpContent);
            flContent.addView(vpContent);
        }

        //设置自定义属性值到相应控件上
        //设置整个Tab栏的高度和背景颜色
        setTabHeight((int) DensityUtils.px2dp(getContext(), mTabHeight));
        setTabBgColor(mTabBgColor);

        //设置TabLayout的字体颜色、TabMode和TabGravity
        setTabTextColor(mTabTextNormalColor, mTabTextSelectedColor);
        setTabIndicatorColor(mTabIndicatorColor);
        setTabIndicatorHeight((int) DensityUtils.px2dp(getContext(), mTabIndicatorHeight));
        setTabMode(mTabMode);
        setTabGravity(mTabGravity);

        //设置TabLayout左边的TextView
        mTextSize = DensityUtils.px2sp(getContext(), mTextSize);
        mTextMarginLeft = (int) DensityUtils.px2dp(getContext(), mTextMarginLeft);
        mTextMarginRight = (int) DensityUtils.px2dp(getContext(), mTextMarginRight);
        setText(mText);
        setTextColor(mTextColor);
        setTextSize(mTextSize);
        setTextVisible(mTextVisible);
        setTextMarginLeft(mTextMarginLeft);
        setTextMarginRight(mTextMarginRight);

        //设置是否显示可扩展图标
        setExpandVisible(mExpandVisible);
        //设置图标资源
        setExpandIcon(mExpandIcon);
        //设置是否可滑动
        setScrollAble(mScrollAble);
    }

    /**
     * 设置整个Tab栏高度
     *
     * @param dpHeight Tab栏高度
     */
    public void setTabHeight(int dpHeight) {
        mTabHeight = DensityUtils.dp2px(getContext(), dpHeight);
        ViewGroup.LayoutParams params = rlTab.getLayoutParams();
        params.height = mTabHeight;
        rlTab.setLayoutParams(params);
    }

    /**
     * 设置Tab栏背景颜色
     *
     * @param color Tab栏背景颜色值
     */
    public void setTabBgColor(int color) {
        mTabBgColor = color;
        rlTab.setBackgroundColor(color);
    }

    /**
     * 设置Tab栏背景颜色
     *
     * @param resId Tab栏背景颜色资源id
     */
    public void setTabBgColorResId(int resId) {
        rlTab.setBackgroundColor(getContext().getResources().getColor(resId));
    }

    /**
     * 设置Tab中的字体颜色
     *
     * @param normalColor   普通字体颜色值
     * @param selectedColor 选中字体颜色值
     */
    public void setTabTextColor(int normalColor, int selectedColor) {
        mTabTextNormalColor = normalColor;
        mTabTextSelectedColor = selectedColor;
        tlTabs.setTabTextColors(normalColor, selectedColor);
    }

    /**
     * 设置Tab中的字体颜色
     *
     * @param normalColorResId   普通字体颜色资源id
     * @param selectedColorResId 选中字体颜色资源id
     */
    public void setTabTextColorResId(int normalColorResId, int selectedColorResId) {
        tlTabs.setTabTextColors(getContext().getResources().getColor(normalColorResId),
                getContext().getResources().getColor(selectedColorResId));
    }

    /**
     * 设置选项指示条高度，单位dp
     *
     * @param dpHeight 选项指示条高度
     */
    public void setTabIndicatorHeight(int dpHeight) {
        mTabIndicatorHeight = DensityUtils.dp2px(getContext(), dpHeight);
        tlTabs.setSelectedTabIndicatorHeight(mTabIndicatorHeight);
    }

    /**
     * 设置选项指示条颜色
     *
     * @param color 指示条颜色值
     */
    public void setTabIndicatorColor(int color) {
        mTabIndicatorColor = color;
        tlTabs.setSelectedTabIndicatorColor(color);
    }

    /**
     * 设置选项指示条颜色
     *
     * @param resId 指示条颜色资源id
     */
    public void setTabIndicatorColorResId(int resId) {
        tlTabs.setSelectedTabIndicatorColor(getContext().getResources().getColor(resId));
    }

    /**
     * 设置Tab背景图
     *
     * @param resId 背景图资源id
     */
    public void setTabBackground(int resId) {
        mTabBackgroundResId = resId;
        if (mTabBackgroundResId > 0) {
            Field tabViewField;
            TabLayout.Tab tab;
            Object tabObj;
            View tabView;
            for (int i = 0; i < tlTabs.getTabCount(); i++) {
                try {
                    tab = tlTabs.getTabAt(i);
                    tabViewField = tab.getClass().getDeclaredField("mView");
                    tabViewField.setAccessible(true);
                    tabObj = tabViewField.get(tab);
                    if (null != tabObj && tabObj instanceof View) {
                        tabView = (View) tabObj;
                        ViewCompat.setBackground(tabView, AppCompatResources.getDrawable(getContext(), mTabBackgroundResId));
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置TabMode
     *
     * @param tabMode TabMode值
     */
    public void setTabMode(int tabMode) {
        mTabMode = tabMode;
        tlTabs.setTabMode(tabMode);
    }

    /**
     * 设置TabGravity
     *
     * @param tabGravity TabGravity值
     */
    public void setTabGravity(int tabGravity) {
        mTabGravity = tabGravity;
        tlTabs.setTabGravity(tabGravity);
    }

    /**
     * 设置TabLayout左侧TextView内容
     *
     * @param text 内容
     */
    public void setText(String text) {
        mText = text;
        tvText.setText(text);
    }

    /**
     * 设置TabLayout左侧TextView字体颜色
     *
     * @param color 字体颜色
     */
    public void setTextColor(int color) {
        mTextColor = color;
        tvText.setTextColor(color);
    }

    /**
     * 设置TabLayout左侧TextView 字体大小
     *
     * @param sp 字体大小
     */
    public void setTextSize(float sp) {
        mTextSize = sp;
        tvText.setTextSize(sp);
    }

    /**
     * 设置TabLayout左侧TextView是否显示
     *
     * @param visible 是否显示
     */
    public void setTextVisible(int visible) {
        mTextVisible = visible;
        tvText.setVisibility(visible);
    }

    /**
     * 设置TabLayout左侧TextView左侧边距
     *
     * @param dp 左侧边距
     */
    public void setTextMarginLeft(int dp) {
        mTextMarginLeft = dp;
        ((RelativeLayout.LayoutParams) tvText.getLayoutParams()).leftMargin = dp;
    }

    /**
     * 设置TabLayout左侧TextView右侧边距
     *
     * @param dp 右侧边距
     */
    public void setTextMarginRight(int dp) {
        mTextMarginRight = dp;
        ((RelativeLayout.LayoutParams) tvText.getLayoutParams()).rightMargin = dp;
    }

    /**
     * 设置是否显示扩展图标
     *
     * @param visible 是否显示扩展图标
     */
    public void setExpandVisible(int visible) {
        mExpandVisible = visible;
        ivExpand.setVisibility(visible);
    }

    /**
     * 设置扩展图标
     *
     * @param resId 扩展图标资源id
     */
    public void setExpandIcon(int resId) {
        mExpandIcon = resId;
        ivExpand.setImageResource(resId);
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
     * 设置选中页面，支持滑动
     *
     * @param index 页面的索引
     */
    @Override
    public void setCurrentPager(int index) {
        setCurrentPager(index, true);
    }

    /**
     * 设置选中页面，可选是否支持滑动
     *
     * @param index        页面的索引
     * @param smoothScroll 是否支持滑动
     */
    @SuppressWarnings("SameParameterValue")
    public void setCurrentPager(int index, boolean smoothScroll) {
        vpContent.setCurrentItem(index, smoothScroll);
    }

    /**
     * 获取当前页面索引
     *
     * @return 当前页面索引
     */
    @Override
    public int getCurrentPager() {
        return vpContent.getCurrentItem();
    }

    /**
     * 设置ViewPager的适配器，并初始化相应事件
     *
     * @param adapter ViewPager的适配器
     */
    public void setAdapter(TpgAdapter adapter) {
        if (null == adapter) {
            throw new RuntimeException("The adapter of TpgView can not be null");
        }

        // 设置适配器
        vpContent.setAdapter(adapter);
        tlTabs.setupWithViewPager(vpContent);
        // 设置tab背景图
        setTabBackground(mTabBackgroundResId);
        // 设置自定义tab
        setCustomTabView(adapter);

        //初始化事件
        initPagerListener();

        //绑定适配器与TpgView，为了在适配器中能获取到TpgView中的某些数据，比如当前页面
        //noinspection unchecked
        adapter.bindTpgView(this);
    }

    /**
     * 设置自定义tab
     *
     * @param adapter 适配器
     */
    private void setCustomTabView(TpgAdapter adapter) {
        TabLayout.Tab tab;
        View tabView;
        View tabParent;
        for (int i = 0; i < tlTabs.getTabCount(); i++) {
            tab = tlTabs.getTabAt(i);
            tabView = adapter.getCustomTabView(i, adapter.getTab(i));
            if (null != tab && null != tabView) {
                tabParent = (View) tabView.getParent();
                if (null != tabParent) {
                    tabParent = tabView;
                    tabParent.setTag(i);
                    tabParent.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vpContent.setCurrentItem((Integer) v.getTag());
                        }
                    });
                }
                tab.setCustomView(tabView);
            }
        }
    }

    /**
     * 初始化事件
     */
    private void initPagerListener() {
        //ViewPager的页面变化事件，设置具体的回调方法
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (null != mPageChangedListener) {
                    mPageChangedListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
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

        //可扩展图标的点击事件
        ivExpand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mExpandListener) {
                    mExpandListener.onExpand(view);
                }
            }
        });
    }

    /**
     * 设置可扩展图标的点击事件
     *
     * @param listener 可扩展图标点击事件
     */
    public void setOnExpandListener(OnExpandListener listener) {
        mExpandListener = listener;
    }

    /**
     * 设置ViewPager的页面切换事件
     *
     * @param listener ViewPager的页面切换事件
     */
    public void setOnPageChangedListener(OnPageChangedListener listener) {
        mPageChangedListener = listener;
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
     * 可扩展图标点击事件监听器
     */
    public interface OnExpandListener {

        /**
         * 当点击扩展图标时出发的回调方法
         *
         * @param view 点击的控件
         */
        void onExpand(View view);
    }
}
