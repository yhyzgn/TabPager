package com.yhy.tabnav.adapter;

import android.view.View;

import com.yhy.tabnav.adapter.base.BasePagerAdapter;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.widget.TpgView;

import java.util.List;

import androidx.fragment.app.FragmentManager;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:06
 * version: 1.0.0
 * desc   : TpgView适配器
 */
public abstract class TpgAdapter<T> extends BasePagerAdapter<TpgView> {
    // Tab标题名称集合
    private List<T> mTabList;

    /**
     * 创建适配器
     *
     * @param fm      FragmentManager
     * @param tabList Tab标题名称集合
     */
    public TpgAdapter(FragmentManager fm, List<T> tabList) {
        this(fm, tabList, null);
    }

    /**
     * 创建适配器
     *
     * @param fm      FragmentManager
     * @param tabList Tab标题名称集合
     * @param config  页面配置参数
     */
    public TpgAdapter(FragmentManager fm, List<T> tabList, PagerConfig config) {
        super(fm, config);
        mTabList = tabList;
    }

    /**
     * 获取标题
     *
     * @param position 当前索引
     * @return 标题
     */
    @Override
    public final CharSequence getPageTitle(int position) {
        return getTitle(position, mTabList.get(position));
    }

    /**
     * 从子类中获取标题
     *
     * @param position 当前索引
     * @param data     tab项对象
     * @return 标题
     */
    public CharSequence getTitle(int position, T data) {
        return null;
    }

    /**
     * 获取自定义Tab
     *
     * @param position 当前索引
     * @param data     tab项对象
     * @return Tab
     */
    public View getCustomTabView(int position, T data) {
        return null;
    }

    /**
     * 获取Tab数据
     *
     * @param position 当前索引
     * @return tab数据
     */
    public final T getTab(int position) {
        return mTabList.get(position);
    }

    /**
     * 获取页面数量
     *
     * @return 页面数量
     */
    @Override
    public final int getCount() {
        return null == mTabList ? 0 : mTabList.size();
    }
}
