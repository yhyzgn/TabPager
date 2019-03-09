package com.yhy.tabnav.interceptor;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-03-09 23:11
 * version: 1.0.0
 * desc   : 加载中拦截器
 */
public interface LoadingInterceptor {

    /**
     * 执行拦截器操作
     *
     * @return 是否继续切换到加载中页面
     */
    boolean processAhead();
}
