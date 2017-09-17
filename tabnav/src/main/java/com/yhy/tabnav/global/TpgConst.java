package com.yhy.tabnav.global;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:07
 * version: 1.0.0
 * desc   : 一些常量
 */
public interface TpgConst {

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
        //没有设置默认资源ID时默认都为0
        int PAGER_NO_RES_ID = 0;
    }
}
