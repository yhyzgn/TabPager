/*
 * Copyright © 2012-2016 昆明鞋包网科技有限公司 版权所有
 */

package com.yhy.tabpager.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtils {
    private static Context ctx;

    private ToastUtils() {
    }

    /**
     * 初始化，在Application中
     *
     * @param ctx 上下文对象
     */
    public static void init(Context ctx) {
        ToastUtils.ctx = ctx;
    }

    /**
     * 短时间显示
     *
     * @param text 提示的内容
     */
    public static void shortToast(String text) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     *
     * @param text 提示的内容
     */
    public static void longToast(String text) {
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 短时间显示
     *
     * @param resId 提示的内容资源id
     */
    public static void shortToast(int resId) {
        Toast.makeText(ctx, ctx.getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     *
     * @param resId 提示的内容资源id
     */
    public static void longToast(int resId) {
        Toast.makeText(ctx, ctx.getResources().getString(resId), Toast.LENGTH_LONG).show();
    }
}
