package com.yhy.tpg.global;

/**
 * 4种不同的页面状态
 * Created by 颜洪毅 on 2017/1/6 0006 10:19.
 */
public interface Const {

    /**
     * 页面加载状态常量
     */
    interface LoadingStatus {
        //加载中...
        int STATE_LOADING = 100;
        //加载错误
        int STATE_ERROR = 101;
        //加载成功，但数据为空
        int STATE_EMPTY = 102;
        //加载成功，且数据有效
        int STATE_SUCCESS = 103;
        //默认状态
        int STATE_DEFAULT = 104;
    }

    /**
     * 页面布局资源id默认值
     */
    interface PagerResIdDef {
        //没有设置默认资源ID时默认都为-1
        int PAGER_NO_RES_ID = -1;
    }
}
