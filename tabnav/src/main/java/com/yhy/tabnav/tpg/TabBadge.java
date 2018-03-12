package com.yhy.tabnav.tpg;

import android.graphics.Bitmap;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-17 12:10
 * version: 1.0.0
 * desc   : 徽章控件相关的接口
 */
public interface TabBadge {

    /**
     * 显示圆点徽章
     *
     * @param index Tab的索引
     */
    void showCirclePointBadge(int index);

    /**
     * 显示文字徽章
     *
     * @param index     Tab的索引
     * @param badgeText 显示的文字
     */
    void showTextBadge(int index, String badgeText);

    /**
     * 隐藏徽章
     *
     * @param index Tab的索引
     */
    void dismissBadge(int index);

    /**
     * 显示图像徽章
     *
     * @param index  Tab的索引
     * @param bitmap 图标
     */
    void showDrawableBadge(int index, Bitmap bitmap);

    /**
     * 是否显示徽章
     *
     * @param index Tab的索引
     * @return 是否显示徽章
     */
    boolean isShowBadge(int index);

    /**
     * 徽章消失的回调方法
     *
     * @param index    Tab的索引
     * @param listener 回调事件
     */
    void setOnDismissListener(int index, OnDismissBadgeListener listener);

    /**
     * 徽章消失事件回调接口
     */
    interface OnDismissBadgeListener {
        void onDismiss();
    }
}