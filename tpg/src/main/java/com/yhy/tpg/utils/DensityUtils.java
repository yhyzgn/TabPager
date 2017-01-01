/*
 * Copyright © 2012-2016 昆明鞋包网科技有限公司 版权所有
 */

/**
 * @Title: DensityUtils.java 
 * @Package com.kmxbw.daidaibang.utils 
 * @Description: TODO
 * @author SilentWolf   
 * @date 2016年8月23日 下午1:56:00 
 * @version V1.0
 */
package com.yhy.tpg.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * @ClassName: DensityUtils
 * @Description: TODO
 * @author SilentWolf
 * @date 2016年8月23日 下午1:56:00
 */
public class DensityUtils {
	private DensityUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	/**
	 * dp转px
	 * 
	 * @param context
	 * @param val
	 * @return
	 */
	public static int dp2px(Context context, float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
	}
	
	/**
	 * sp转px
	 * 
	 * @param context
	 * @param val
	 * @return
	 */
	public static int sp2px(Context context, float spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
	}
	
	/**
	 * px转dp
	 * 
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2dp(Context context, float pxVal) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}
	
	/**
	 * px转sp
	 * 
	 * @param fontScale
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(Context context, float pxVal) {
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}
	
}