package com.yhy.tabnav.interceptor;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-03-09 23:41
 * version: 1.0.0
 * desc   : 空数据拦截器
 */
public interface EmptyInterceptor {

    /**
     * 执行拦截器操作
     *
     * @return 是否继续切换到空数据页面
     */
    boolean processAhead();
}
