package com.yhy.tpg.adapter;

import android.support.v4.app.FragmentManager;

import com.yhy.tpg.adapter.base.BasePagerAdapter;
import com.yhy.tpg.config.PagerConfig;
import com.yhy.tpg.entity.NavTab;
import com.yhy.tpg.widget.NavView;

import java.util.List;

/**
 * Created by HongYi Yan on 2017/3/11 23:10.
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
