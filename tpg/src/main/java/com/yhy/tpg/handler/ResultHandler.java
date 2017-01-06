package com.yhy.tpg.handler;

import android.os.Handler;

import com.yhy.tpg.global.Const;

/**
 * Created by 颜洪毅 on 2017/1/6 0006 10:09.
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
        mHandler.obtainMessage(Const.LoadingStatus.STATE_LOADING).sendToTarget();
    }

    /**
     * 发送错误消息
     */
    public void sendErrorHandler() {
        mHandler.obtainMessage(Const.LoadingStatus.STATE_ERROR).sendToTarget();
    }

    /**
     * 发送空数据消息
     */
    public void sendEmptyHandler() {
        mHandler.obtainMessage(Const.LoadingStatus.STATE_EMPTY).sendToTarget();
    }

    /**
     * 发送成功消息
     */
    public void sendSuccessHandler() {
        mHandler.obtainMessage(Const.LoadingStatus.STATE_SUCCESS).sendToTarget();
    }
}
