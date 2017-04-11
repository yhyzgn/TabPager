package com.yhy.tabnav.cache;

import android.util.SparseArray;

import com.yhy.tabnav.pager.TpgFragment;

/**
 * Created by 颜洪毅 on 2016/12/22 0022.
 */
public class PagerCache {
    private SparseArray<TpgFragment> mCache;

    public PagerCache() {
        mCache = new SparseArray<>();
    }

    /**
     * 保存一页
     *
     * @param index 页面索引
     * @param page  页面
     */
    public void savePager(int index, TpgFragment page) {
        if (null != mCache) {
            mCache.put(index, page);
        }
    }

    /**
     * 获取某一页
     *
     * @param index 页面索引
     * @return 页面
     */
    public TpgFragment getPager(int index) {
        if (null != mCache) {
            return mCache.get(index);
        }
        return null;
    }
}
