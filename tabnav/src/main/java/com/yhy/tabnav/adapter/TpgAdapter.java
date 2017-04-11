package com.yhy.tabnav.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yhy.tabnav.adapter.base.BasePagerAdapter;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.pager.TpgFragment;
import com.yhy.tabnav.cache.PagerCache;
import com.yhy.tabnav.widget.TpgView;

/**
 * Created by 颜洪毅 on 2016/12/22 0022.
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
