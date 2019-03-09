package com.yhy.tabnav.interceptor;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-03-09 23:41
 * version: 1.0.0
 * desc   : 错误拦截器
 */
public interface ErrorInterceptor {

    /**
     * 执行拦截器操作
     *
     * @return 是否继续切换到错误月面
     */
    boolean processAhead();
}
