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

//    private BtgView mBtgView;
//    private PagerCache mCache;
//    private PagerConfig mConfig;

    public NavAdapter(FragmentManager fm, List<NavTab> tabList) {
        this(fm, tabList, null);
    }

    public NavAdapter(FragmentManager fm, List<NavTab> tabList, PagerConfig config) {
        super(fm, config);
        if (null == tabList) {
            throw new IllegalArgumentException("The argument tabList can not be null");
        }
        mTabList = tabList;
//        mCache = new PagerCache();
//        mConfig = config;
    }

    //
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position).tabText;
    }

    public int getTabIconId(int position) {
        return mTabList.get(position).tabIconId;
    }
//
//    @Override
//    public TpgFragment getItem(int position) {
//        TpgFragment pager = null;
//        //先从缓存中获取页面
//        if (null != mCache) {
//            pager = mCache.getPager(position);
//            //如果获取到页面，就直接返回
//            if (null != pager) {
//                return pager;
//            }
//        }
//        //如果没获取到缓存页面的话，就从子类获取
//        pager = getPager(position);
//        //将页面缓存起来
//        mCache.savePager(position, pager);
//        //获取到当前页面的页面配置参数，并设置给具体页面
//        pager.setPagerConfig(mConfig);
//        return pager;
//    }
//
//    public abstract TpgFragment getPager(int position);
//
//    @Override
//    public int getItemPosition(Object object) {
//        //重写该方法并返回POSITION_NONE，达到在调用notifyDataSetChanged()方法时强制刷新ViewPager页面的效果
//        return POSITION_NONE;
//    }
//
//
//    public void bindTpgView(BtgView btgView) {
//        mBtgView = btgView;
//    }
//
//    /**
//     * 获取页面的缓存
//     *
//     * @return 页面缓存
//     */
//    public PagerCache getPagerCache() {
//        return mCache;
//    }
//
//    /**
//     * 重新加载某一页的数据
//     *
//     * @param index 页面索引
//     * @param args  可能需要的参数
//     */
//    public void reloadDataForPager(int index, Object... args) {
//        TpgFragment pager = mCache.getPager(index);
//        if (null != pager) {
//            pager.reloadDate(args);
//        }
//    }
//
//    /**
//     * 重新加载当前页面的数据
//     *
//     * @param args 可能需要的参数
//     */
//    public void reloadDataForCurrentPager(Object... args) {
//        reloadDataForPager(mBtgView.getCurrentPager(), args);
//    }
}
