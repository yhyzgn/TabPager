package com.yhy.tpg.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yhy.tpg.adapter.base.BasePagerAdapter;
import com.yhy.tpg.config.PagerConfig;
import com.yhy.tpg.pager.TpgFragment;
import com.yhy.tpg.cache.PagerCache;
import com.yhy.tpg.widget.TpgView;

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
