package com.yhy.tabnav.tpg;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:10
 * version: 1.0.0
 * desc   : 整个框架的接口，在TpgView和NavView中实现
 */
public interface Tpg {

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
