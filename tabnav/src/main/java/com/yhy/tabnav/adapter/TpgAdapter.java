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
 * desc   :
 */
public abstract class TpgAdapter extends BasePagerAdapter<TpgView> {

    private List<String> mTabList;

    /**
     * 创建适配器
     *
     * @param fm FragmentManager
     */
    public TpgAdapter(FragmentManager fm, List<String> tabList) {
        this(fm, tabList, null);
    }

    /**
     * 创建适配器
     *
     * @param fm     FragmentManager
     * @param config 页面配置参数
     */
    public TpgAdapter(FragmentManager fm, List<String> tabList, PagerConfig config) {
        super(fm, config);
        mTabList = tabList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null == mTabList ? null : mTabList.get(position);
    }

    @Override
    public int getCount() {
        return null == mTabList ? 0 : mTabList.size();
    }
}
