package com.yhy.tabnav.tpg;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.handler.ResultHandler;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:11
 * version: 1.0.0
 * desc   :
 */
public interface PagerFace<RT> {
    Fragment getFragment();

    void setRoot(RT root);

    void setPagerConfig(PagerConfig config);

    void getPagerActivity(Context context);

    View onCreatePagerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void onPagerVisible(boolean isVisible);

    View getLoadingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    View getEmptyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    View getErrorView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    View getSuccessView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    void initData();

    void initListener();

    void shouldLoadData();

    void reloadData(Bundle args);

    ResultHandler getRltHandler();
}
