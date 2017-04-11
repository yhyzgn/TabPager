package com.yhy.tabnav.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by 颜洪毅 on 2016/12/23 0023.
 */
public class ViewUtils {

    private ViewUtils() {
    }

    /**
     * 从View的Parent中移除该View
     *
     * @param view 要移除的View对象
     */
    public static void removeFromParent(View view) {
        if (null != view) {
            ViewParent parent = view.getParent();
            if (null != parent && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view);
            }
        }
    }
}
