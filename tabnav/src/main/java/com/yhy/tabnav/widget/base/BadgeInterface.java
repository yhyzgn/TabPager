package com.yhy.tabnav.widget.base;

import android.graphics.Bitmap;

/**
 * Created by HongYi Yan on 2017/3/13 12:55.
 */
public interface BadgeInterface {

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
    void hiddenBadge(int index);

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
}
