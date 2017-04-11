package com.yhy.tabnav.widget.base;

/**
 * Created by HongYi Yan on 2017/3/12 23:17.
 */
public interface TpgInterface {

    /**
     * 设置当前页面
     *
     * @param index 页面索引
     */
    void setCurrentPager(int index);

    /**
     * 获取当前页面
     *
     * @return 当前页面索引
     */
    int getCurrentPager();
}
