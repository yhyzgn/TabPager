package com.yhy.tabnav.cache;

import android.util.SparseArray;

import com.yhy.tabnav.tpg.PagerFace;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:06
 * version: 1.0.0
 * desc   : 页面缓存
 */
public class PagerCache {
    private SparseArray<PagerFace> mCache;

    public PagerCache() {
        mCache = new SparseArray<>();
    }

    /**
     * 保存一页
     *
     * @param index 页面索引
     * @param page  页面
     */
    public void savePager(int index, PagerFace page) {
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
    public PagerFace getPager(int index) {
        if (null != mCache) {
            return mCache.get(index);
        }
        return null;
    }
}
