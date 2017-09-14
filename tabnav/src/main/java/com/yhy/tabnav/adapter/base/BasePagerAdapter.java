package com.yhy.tabnav.adapter.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yhy.tabnav.cache.PagerCache;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.pager.TpgFragment;
import com.yhy.tabnav.tpg.PagerFace;
import com.yhy.tabnav.widget.base.TpgInterface;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:06
 * version: 1.0.0
 * desc   :
 */
public abstract class BasePagerAdapter<T extends TpgInterface> extends FragmentPagerAdapter {
    private PagerConfig mConfig;
    private PagerCache mCache;
    private T view;

    /**
     * 创建适配器
     *
     * @param fm FragmentManager
     */
    public BasePagerAdapter(FragmentManager fm) {
        this(fm, null);
    }

    /**
     * 创建适配器
     *
     * @param fm     FragmentManager
     * @param config 页面配置参数
     */
    public BasePagerAdapter(FragmentManager fm, PagerConfig config) {
        super(fm);
        mConfig = config;
        mCache = new PagerCache();
    }

    public void bindTpgView(T view) {
        this.view = view;
    }

    /**
     * 重试加载数据，适用于之前加载错误或者为空，点击后的重试操作
     */
    public void retryLoadDataForCurrentPager() {
        if (null != mCache && null != view) {
            //取得当前页面
            PagerFace pager = mCache.getPager(view.getCurrentPager());
            if (null != pager) {
                //重新判断是否加载数据并加载
                pager.shouldLoadData();
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        PagerFace pager = null;
        //先从缓存中获取页面
        if (null != mCache) {
            pager = mCache.getPager(position);
            //如果获取到页面，就直接返回
            if (null != pager) {
                return pager.getFragment();
            }
        }
        //如果没获取到缓存页面的话，就从子类获取
        pager = getPager(position);
        //获取到当前页面的页面配置参数，并设置给具体页面
        pager.setPagerConfig(mConfig);
        //将页面缓存起来
        mCache.savePager(position, pager);
        return pager.getFragment();
    }

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
    public abstract PagerFace getPager(int position);

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
    public void reloadDataForPager(int index, Bundle args) {
        PagerFace pager = mCache.getPager(index);
        if (null != pager) {
            if (null != pager.getRltHandler()) {
                //发送加载中消息
                pager.getRltHandler().sendLoadingHandler();
            }
            //重新加载数据
            pager.reloadData(args);
        }
    }

    /**
     * 重新加载当前页面的数据
     *
     * @param args 可能需要的参数
     */
    public void reloadDataForCurrentPager(Bundle args) {
        reloadDataForPager(view.getCurrentPager(), args);
    }
}
