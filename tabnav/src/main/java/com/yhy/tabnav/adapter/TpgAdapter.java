package com.yhy.tabnav.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yhy.tabnav.adapter.base.BasePagerAdapter;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.pager.TpgFragment;
import com.yhy.tabnav.cache.PagerCache;
import com.yhy.tabnav.widget.TpgView;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:06
 * version: 1.0.0
 * desc   :
 */
public abstract class TpgAdapter extends BasePagerAdapter<TpgView> {

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
        super(fm, config);
    }
}
