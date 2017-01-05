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
    private ToastUtils() {
    }

    /**
     * 短时间显示
     *
     * @param context 上下文对象
     * @param text    提示的内容
     */
    public static void shortToast(Context context, String text) {
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     *
     * @param context 上下文对象
     * @param text    提示的内容
     */
    public static void longToast(Context context, String text) {
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    /**
     * 短时间显示
     *
     * @param context 上下文对象
     * @param resId   提示的内容资源id
     */
    public static void shortToast(Context context, int resId) {
        Toast.makeText(context.getApplicationContext(), context.getResources().getString(resId),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     *
     * @param context 上下文对象
     * @param resId   提示的内容资源id
     */
    public static void longToast(Context context, int resId) {
        Toast.makeText(context.getApplicationContext(), context.getResources().getString(resId),
                Toast.LENGTH_LONG).show();
    }
}
