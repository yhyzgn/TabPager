package com.yhy.tpg.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yhy.tpg.config.PagerConfig;
import com.yhy.tpg.pager.TpgFragment;
import com.yhy.tpg.cache.PagerCache;
import com.yhy.tpg.widget.TpgView;

/**
 * Created by 颜洪毅 on 2016/12/22 0022.
 */
public abstract class TpgAdapter extends FragmentStatePagerAdapter {
    private PagerConfig mConfig;
    private PagerCache mCache;
    private TpgView mTpgView;

    /**
     * 创建适配器
     *
     * @param fm FragmentManager
     */
    public TpgAdapter(FragmentManager fm) {
        this(fm, null);
    }

    /**
     * 创建适配器
     *
     * @param fm     FragmentManager
     * @param config 页面配置参数
     */
    public TpgAdapter(FragmentManager fm, PagerConfig config) {
        super(fm);
        mConfig = config;
        mCache = new PagerCache();
    }

    public void bindTpgView(TpgView tpgView) {
        mTpgView = tpgView;
    }

    /**
     * 重试加载数据，适用于之前加载错误或者为空，点击后的重试操作
     */
    public void retryLoadDataForCurrentPager() {
        if (null != mCache && null != mTpgView) {
            //取得当前页面
            TpgFragment pager = mCache.getPager(mTpgView.getCurrentPager());
            if (null != pager) {
                //重新判断是否加载数据并加载
                pager.shouldLoadData();
            }
        }
    }

    @Override
    public abstract CharSequence getPageTitle(int position);

    @Override
    public TpgFragment getItem(int position) {
        TpgFragment pager = null;
        //先从缓存中获取页面
        if (null != mCache) {
            pager = mCache.getPager(position);
            //如果获取到页面，就直接返回
            if (null != pager) {
                return pager;
            }
        }
        //如果没获取到缓存页面的话，就从子类获取
        pager = getPager(position);
        //将页面缓存起来
        mCache.savePager(position, pager);
        //获取到当前页面的页面配置参数，并设置给具体页面
        pager.setPagerConfig(mConfig);
        return pager;
    }

    @Override
    public abstract int getCount();

    @Override
    public int getItemPosition(Object object) {
        //重写该方法并返回POSITION_NONE，达到在调用notifyDataSetChanged()方法时强制刷新ViewPager页面的效果
        return POSITION_NONE;
    }

    /**
     * 获取具体的页面
     *
     * @param position 页面索引
     * @return 一个页面
     */
    public abstract TpgFragment getPager(int position);

    /**
     * 获取页面的缓存
     *
     * @return 页面缓存
     */
    public PagerCache getPagerCache() {
        return mCache;
    }

    /**
     * 重新加载某一页的数据
     *
     * @param index 页面索引
     * @param args  可能需要的参数
     */
    public void reloadDataForPager(int index, Object... args) {
        TpgFragment pager = mCache.getPager(index);
        if (null != pager) {
            pager.reloadDate(args);
        }
    }

    /**
     * 重新加载当前页面的数据
     *
     * @param args 可能需要的参数
     */
    public void reloadDataForCurrentPager(Object... args) {
        reloadDataForPager(mTpgView.getCurrentPager(), args);
    }
}
