package com.yhy.tpg.cache;

import android.util.SparseArray;

import com.yhy.tpg.pager.TpgFragment;

/**
 * Created by 颜洪毅 on 2016/12/22 0022.
 */
public class PagerCache {
    private SparseArray<TpgFragment> mCache;

    public PagerCache() {
        mCache = new SparseArray<>();
    }

    public void savePager(int index, TpgFragment page) {
        if (null != mCache) {
            mCache.put(index, page);
        }
    }

    public TpgFragment getPager(int index) {
        if (null != mCache) {
            return mCache.get(index);
        }
        return null;
    }
}
