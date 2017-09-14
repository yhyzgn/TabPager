package com.yhy.tabnav.handler;

import android.os.Handler;

import com.yhy.tabnav.global.TpgConst;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:07
 * version: 1.0.0
 * desc   :
 */
public class ResultHandler {
    private Handler mHandler;

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
    public void sendLoadingHandler() {
        mHandler.obtainMessage(TpgConst.LoadingStatus.STATE_LOADING).sendToTarget();
    }

    /**
     * 发送错误消息
     */
    public void sendErrorHandler() {
        mHandler.obtainMessage(TpgConst.LoadingStatus.STATE_ERROR).sendToTarget();
    }

    /**
     * 发送空数据消息
     */
    public void sendEmptyHandler() {
        mHandler.obtainMessage(TpgConst.LoadingStatus.STATE_EMPTY).sendToTarget();
    }

    /**
     * 发送成功消息
     */
    public void sendSuccessHandler() {
        mHandler.obtainMessage(TpgConst.LoadingStatus.STATE_SUCCESS).sendToTarget();
    }
}
