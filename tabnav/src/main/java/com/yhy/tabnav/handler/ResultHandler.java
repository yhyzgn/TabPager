package com.yhy.tabnav.handler;

import android.os.Handler;

import com.yhy.tabnav.global.TpgConst;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:07
 * version: 1.0.0
 * desc   : 用来发送页面消息的Handler
 */
public class ResultHandler {
    private final Handler mHandler;

    /**
     * 创建结果Handler对象
     *
     * @param handler 用来发送消息的Handler
     */
    public ResultHandler(Handler handler) {
        mHandler = handler;
    }

    /**
     * 发送加载中消息
     */
    public void onLoading() {
        mHandler.sendEmptyMessage(TpgConst.LoadingStatus.STATE_LOADING);
    }

    /**
     * 发送错误消息
     */
    public void onError() {
        mHandler.sendEmptyMessage(TpgConst.LoadingStatus.STATE_ERROR);
    }

    /**
     * 发送空数据消息
     */
    public void onEmpty() {
        mHandler.sendEmptyMessage(TpgConst.LoadingStatus.STATE_EMPTY);
    }

    /**
     * 发送成功消息
     */
    public void onSuccess() {
        mHandler.sendEmptyMessage(TpgConst.LoadingStatus.STATE_SUCCESS);
    }
}
