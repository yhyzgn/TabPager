package com.yhy.tabnav.interceptor;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-03-09 23:42
 * version: 1.0.0
 * desc   : 成功拦截器
 */
public interface SuccessInterceptor {

    /**
     * 执行拦截器操作
     *
     * @return 是否继续切换到成功页面
     */
    boolean processAhead();
}
