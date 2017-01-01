package com.yhy.tpg.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yhy.tpg.R;
import com.yhy.tpg.adapter.TpgAdapter;
import com.yhy.tpg.config.PagerConfig;
import com.yhy.tpg.pager.TpgFragment;
import com.yhy.tpg.cache.PagerCache;
import com.yhy.tpg.utils.DensityUtils;

/**
 * Created by 颜洪毅 on 2016/12/22 0022.
 */
public class TpgView extends LinearLayout {
    //一般状态下的字体颜色默认值
    private static final int TEXT_NORMAL_COLOR_DEF = Color.parseColor("#aaff4400");
    //选中状态下的字体颜色默认值
    private static final int TEXT_SELECTED_COLOR_DEF = Color.parseColor("#ffff2200");

    //包含了TabLayout和ImageView的布局
    private RelativeLayout rlTab;
    //TabLayout控件
    private TabLayout tlTabs;
    //可扩展的ImageView控件
    private ImageView ivExpand;
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
    //可扩展的图标是否显示，默认VISIBLE
    private int mExpandVisible;
    //可扩展图标资源
    private int mExpandIcon;

    //页面缓存
    private PagerCache mCache;

    private PagerConfig mConfig;

    //扩展图标点击监听事件
    private OnExpandListener mExpandListener;
    //ViewPager页面切换监听事件
    private OnPageChangedListener mPageChangedListener;

    public TpgView(Context context) {
        this(context, null);
    }

    public TpgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TpgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化控件
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.widget_tpg, this);

        //获取自定义属性值
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TpgViewAttrs);
        mTabHeight = (int) ta.getDimension(R.styleable.TpgViewAttrs_tab_height, 48f);
        mTabBgColor = ta.getColor(R.styleable.TpgViewAttrs_tab_bg_color, Color.TRANSPARENT);
        mTabTextNormalColor = ta.getColor(R.styleable.TpgViewAttrs_tab_text_normal_color,
                TEXT_NORMAL_COLOR_DEF);
        mTabTextSelectedColor = ta.getColor(R.styleable.TpgViewAttrs_tab_text_selected_color,
                TEXT_SELECTED_COLOR_DEF);
        mTabIndicatorColor = ta.getColor(R.styleable.TpgViewAttrs_tab_indicator_color,
                TEXT_SELECTED_COLOR_DEF);
        mTabIndicatorHeight = (int) ta.getDimension(R.styleable
                .TpgViewAttrs_tab_indicator_height, 3f);
        mTabMode = ta.getInt(R.styleable.TpgViewAttrs_tab_mode, TabLayout.MODE_SCROLLABLE);
        mTabGravity = ta.getInt(R.styleable.TpgViewAttrs_tab_gravity, TabLayout.GRAVITY_FILL);
        mExpandVisible = ta.getInt(R.styleable.TpgViewAttrs_expand_visible, VISIBLE);
        mExpandIcon = ta.getResourceId(R.styleable.TpgViewAttrs_expand_icon, R.drawable.ic_expand);

        ta.recycle();
    }

    /**
     * 当View中所有的子控件均被映射成xml后触发
     * 可以在这里获取具体的子控件
     */
    @Override
    protected void onFinishInflate() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_tpg, this);
        rlTab = (RelativeLayout) view.findViewById(R.id.rl_tab);
        tlTabs = (TabLayout) view.findViewById(R.id.tl_tabs);
        ivExpand = (ImageView) view.findViewById(R.id.iv_expand);
        vpContent = (ViewPager) view.findViewById(R.id.vp_content);

        //设置自定义属性值到相应控件上
        //设置整个Tab栏的高度和背景颜色
        setTabHeight(mTabHeight);
        setTabBgColor(mTabBgColor);

        //设置TabLayout的字体颜色、TabMode和TabGravity
        setTabTextColor(mTabTextNormalColor, mTabTextSelectedColor);
        setTabIndicatorColor(mTabIndicatorColor);
        setTabIndicatorHeight(mTabIndicatorHeight);
        setTabMode(mTabMode);
        setTabGravity(mTabGravity);

        //设置是否显示可扩展图标
        setExpandVisible(mExpandVisible);
        //设置图标资源
        setExpandIcon(mExpandIcon);
    }

    /**
     * 设置整个Tab栏高度
     *
     * @param dpHeight Tab栏高度
     */
    public void setTabHeight(int dpHeight) {
        ViewGroup.LayoutParams params = rlTab.getLayoutParams();
        params.height = DensityUtils.dp2px(getContext(), dpHeight);
        rlTab.setLayoutParams(params);
    }

    /**
     * 设置Tab栏背景颜色
     *
     * @param color Tab栏背景颜色值
     */
    public void setTabBgColor(int color) {
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
        tlTabs.setSelectedTabIndicatorHeight(DensityUtils.dp2px(getContext(), dpHeight));
    }

    /**
     * 设置选项指示条颜色
     *
     * @param color 指示条颜色值
     */
    public void setTabIndicatorColor(int color) {
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
     * 设置TabMode
     *
     * @param tabMode TabMode值
     */
    public void setTabMode(int tabMode) {
        tlTabs.setTabMode(tabMode);
    }

    /**
     * 设置TabGravity
     *
     * @param tabGravity TabGravity值
     */
    public void setTabGravity(int tabGravity) {
        tlTabs.setTabGravity(tabGravity);
    }

    /**
     * 设置是否显示扩展图标
     *
     * @param visible 是否显示扩展图标
     */
    public void setExpandVisible(int visible) {
        ivExpand.setVisibility(visible);
    }

    /**
     * 设置扩展图标
     *
     * @param resId 扩展图标资源id
     */
    public void setExpandIcon(int resId) {
        ivExpand.setImageResource(resId);
    }

    /**
     * 设置选中页面，支持滑动
     *
     * @param index 页面的索引
     */
    public void setCurrentPager(int index) {
        setCurrentPager(index, true);
    }

    /**
     * 设置选中页面，可选是否支持滑动
     *
     * @param index        页面的索引
     * @param smoothScroll 是否支持滑动
     */
    public void setCurrentPager(int index, boolean smoothScroll) {
        vpContent.setCurrentItem(index, smoothScroll);
    }

    /**
     * 获取当前页面索引
     *
     * @return 当前页面索引
     */
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
        //从适配器获取页面缓存
        mCache = adapter.getPagerCache();

        // 设置适配器
        vpContent.setAdapter(adapter);
        tlTabs.setupWithViewPager(vpContent);

        //初始化事件
        initPagerListener();

        //绑定适配器与TpgView，为了在适配器中能获取到TpgView中的某些数据，比如当前页面
        adapter.bindTpgView(this);
    }

    /**
     * 初始化事件
     */
    private void initPagerListener() {
        //ViewPager的页面变化事件，设置具体的回调方法
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

    /**
     * ViewPager页面切换事件监听器
     */
    public interface OnPageChangedListener {

        /**
         * 当页面滑动时
         *
         * @param position             当前页面序号，只有翻页成功的情况下最后一次调用才会变为目标页面。
         * @param positionOffset       是当前页面滑动比例
         * @param positionOffsetPixels 是当前页面滑动像素，变化情况和positionOffset一致。
         */
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        /**
         * 页面切换成功
         *
         * @param position 当前页面序号
         */
        void onPageSelected(int position);

        /**
         * 手指操作屏幕的时候发生变化。有三个值：0（END）,1(PRESS) , 2(UP)。
         * 当setCurrentItem翻页时，会执行这个方法两次，state值分别为2 , 0 。
         *
         * @param state 有三个值：0（END）,1(PRESS) , 2(UP)
         */
        void onPageScrollStateChanged(int state);
    }
}
