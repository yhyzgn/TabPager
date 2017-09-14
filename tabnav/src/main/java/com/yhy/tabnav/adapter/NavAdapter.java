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
 * desc   :
 */
public abstract class NavAdapter extends BasePagerAdapter<NavView> {
    private List<NavTab> mTabList;

    public NavAdapter(FragmentManager fm, List<NavTab> tabList) {
        this(fm, tabList, null);
    }

    public NavAdapter(FragmentManager fm, List<NavTab> tabList, PagerConfig config) {
        super(fm, config);
        if (null == tabList) {
            throw new IllegalArgumentException("The argument tabList can not be null");
        }
        mTabList = tabList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position).tabText;
    }

    public int getTabIconId(int position) {
        return mTabList.get(position).tabIconId;
    }
}
