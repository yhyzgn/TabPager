package com.yhy.tabnav.entity;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:07
 * version: 1.0.0
 * desc   : NavView每个子项的模型
 */
public class NavTab {
    public String tabText;
    public int tabIconId;

    /**
     * 构造函数
     *
     * @param tabText   子项文本
     * @param tabIconId 子项图标
     */
    public NavTab(String tabText, int tabIconId) {
        this.tabText = tabText;
        this.tabIconId = tabIconId;
    }
}
