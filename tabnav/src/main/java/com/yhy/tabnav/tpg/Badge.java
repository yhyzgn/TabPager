package com.yhy.tabnav.tpg;

import android.graphics.Bitmap;

import cn.bingoogolapple.badgeview.BGADragDismissDelegate;

/**
 * Created by HongYi Yan on 2017/3/13 12:55.
 */
public interface Badge {

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