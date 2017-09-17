package com.yhy.tabnav.adapter;

import android.support.v4.app.FragmentManager;

import com.yhy.tabnav.adapter.base.BasePagerAdapter;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.widget.TpgView;

import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:06
 * version: 1.0.0
 * desc   : TpgView适配器
 */
public abstract class TpgAdapter extends BasePagerAdapter<TpgView> {
    // Tab标题名称集合
    private List<String> mTabList;

    /**
     * 创建适配器
     *
     * @param fm      FragmentManager
     * @param tabList Tab标题名称集合
     */
    public TpgAdapter(FragmentManager fm, List<String> tabList) {
        this(fm, tabList, null);
    }

    /**
     * 创建适配器
     *
     * @param fm      FragmentManager
     * @param tabList Tab标题名称集合
     * @param config  页面配置参数
     */
    public TpgAdapter(FragmentManager fm, List<String> tabList, PagerConfig config) {
        super(fm, config);
        mTabList = tabList;
    }

    /**
     * 获取页面标题
     *
     * @param position 页面索引
     * @return 页面标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return null == mTabList ? null : mTabList.get(position);
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
}
