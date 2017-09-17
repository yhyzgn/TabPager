package com.yhy.tabnav.adapter;

import android.support.v4.app.FragmentManager;

import com.yhy.tabnav.adapter.base.BasePagerAdapter;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.entity.NavTab;
import com.yhy.tabnav.widget.NavView;

import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:06
 * version: 1.0.0
 * desc   : NavView适配器
 */
public abstract class NavAdapter extends BasePagerAdapter<NavView> {
    // NavView底部子项模型集合
    private List<NavTab> mTabList;

    /**
     * 创建适配器
     *
     * @param fm      FragmentManager
     * @param tabList NavView底部子项模型集合
     */
    public NavAdapter(FragmentManager fm, List<NavTab> tabList) {
        this(fm, tabList, null);
    }

    /**
     * 创建适配器
     *
     * @param fm      FragmentManager
     * @param tabList NavView底部子项模型集合
     * @param config  页面配置参数
     */
    public NavAdapter(FragmentManager fm, List<NavTab> tabList, PagerConfig config) {
        super(fm, config);
        if (null == tabList) {
            throw new IllegalArgumentException("The argument tabList can not be null");
        }
        mTabList = tabList;
    }

    /**
     * 获取页面数量
     *
     * @return 页面数量
     */
    @Override
    public int getCount() {
        return null == mTabList ? 0 : mTabList.size();
    }

    /**
     * 获取页面标题
     *
     * @param position 页面索引
     * @return 页面标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position).tabText;
    }

    /**
     * 获取每个选项图标
     *
     * @param position 页面索引
     * @return 选项图标
     */
    public int getTabIconId(int position) {
        return mTabList.get(position).tabIconId;
    }
}
