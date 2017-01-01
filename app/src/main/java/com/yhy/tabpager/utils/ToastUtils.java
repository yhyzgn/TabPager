/*
 * Copyright © 2012-2016 昆明鞋包网科技有限公司 版权所有
 */

package com.yhy.tabpager.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * 
 * @ClassName: ToastUtil
 * @Description: TODO
 * @author SilentWolf
 * @date 2016年8月1日 下午3:53:55
 */
public class ToastUtils {
	private ToastUtils() {
	}
	
	/**
	 * 短时间显示
	 * 
	 * @Title: shortToast
	 * @Description: TODO
	 * @param @param
	 *            context 上下文对象
	 * @param @param
	 *            text 显示的内容
	 * @return void
	 * @throws null
	 */
	public static void shortToast(Context context, String text) {
		Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 长时间显示
	 * 
	 * @Title: longToast
	 * @Description: TODO
	 * @param @param
	 *            context 上下文对象
	 * @param @param
	 *            text 显示的内容
	 * @return void
	 * @throws null
	 */
	public static void longToast(Context context, String text) {
		Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 短时间显示
	 * 
	 * @Title: shortToast
	 * @Description: TODO
	 * @param @param
	 *            context 上下文对象
	 * @param @param
	 *            text 显示的内容
	 * @return void
	 * @throws null
	 */
	public static void shortToast(Context context, int resId) {
		Toast.makeText(context.getApplicationContext(), context.getResources().getString(resId),
				Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 长时间显示
	 * 
	 * @Title: longToast
	 * @Description: TODO
	 * @param @param
	 *            context 上下文对象
	 * @param @param
	 *            text 显示的内容
	 * @return void
	 * @throws null
	 */
	public static void longToast(Context context, int resId) {
		Toast.makeText(context.getApplicationContext(), context.getResources().getString(resId),
				Toast.LENGTH_LONG).show();
	}
}
