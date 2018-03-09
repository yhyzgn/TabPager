package com.yhy.tabnav.pager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.helper.TpgHelper;
import com.yhy.tabnav.tpg.PagerFace;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-14 21:08
 * version: 1.0.0
 * desc   : 所有页面的父类，如果自定义的话请参照此处自行实现PagerFace接口
 */
public abstract class TpgFragment<RT> extends Fragment implements PagerFace<RT> {
    //当前Activity对象
    public Activity mActivity;
    //页面助手，用于创建各种View，比如错误页面等（必要）。便于自定义该页面时直接使用。
    public TpgHelper<RT> mHelper = new TpgHelper<>();

    /**
     * Fragment生命周期方法--创建
     *
     * @param savedInstanceState 保存的参数
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取Activity
        getPagerActivity(getActivity());
    }

    /**
     * Fragment生命周期方法--依附到Activity
     *
     * @param context 上下文对象
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 为了避免Activity为空，这里需要再次通过context获取Activity
        getPagerActivity(context);
    }

    /**
     * Fragment生命周期方法--View创建
     *
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存的参数
     * @return 真正显示的View
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreatePagerView(inflater, container, savedInstanceState);
        // 由于setUserVisibleHint方法再onCreateView之前执行，所以要实现懒加载的话，就再onCreateView方法中手动调用加载数据方法，并在setUserVisibleHint方法中判断是否显示
        shouldLoadData();
        return view;
    }

    /**
     * Fragment的显示或者隐藏
     * <p>
     * 必须在切换页面时手动调用
     * 这里的页面都用在ViewPager中，在FragmentPagerAdapter中已经主动触发
     *
     * @param isVisibleToUser 是否显示
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // 记录下当前状态（由于setUserVisibleHint方法再onCreateView之前执行，所以要实现懒加载的话，就再onCreateView方法中手动调用加载数据方法，并在setUserVisibleHint方法中判断是否显示）
        mHelper.onPagerVisible(this, isVisibleToUser);
    }

    /**
     * PagerFace接口方法--适配器中获取Fragment使用
     *
     * @return 当前Fragment
     */
    @Override
    public Fragment getFragment() {
        return this;
    }

    /**
     * PagerFace接口方法--设置根页面
     *
     * @param root 根页面
     */
    @Override
    public void setRoot(RT root) {
        mHelper.setRoot(root);
    }

    /**
     * 获取根页面
     *
     * @return 根页面
     */
    @Override
    public RT getRoot() {
        return mHelper.getRoot();
    }

    /**
     * 设置Bundle参数
     *
     * @param params Bundle参数
     */
    @Override
    public void setParams(Bundle params) {
        setArguments(params);
    }

    /**
     * 获取Bundle参数
     *
     * @return Bundle参数
     */
    @Override
    public Bundle getParams() {
        return getArguments();
    }

    /**
     * PagerFace接口方法--设置页面参数
     *
     * @param config 页面参数
     */
    @Override
    public void setPagerConfig(PagerConfig config) {
        mHelper.setPagerConfig(config);
    }

    /**
     * PagerFace接口方法--获取当前Activity
     *
     * @param context 上下文对象
     */
    @Override
    public void getPagerActivity(Context context) {
        if (null == mActivity) {
            mActivity = mHelper.getPagerActivity(context);
        }
    }

    /**
     * PagerFace接口方法--创建Fragment真正显示的View
     *
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存的参数
     * @return 真正显示的View
     */
    @Override
    public View onCreatePagerView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return mHelper.onCreatePagerView(this, inflater, container, savedInstanceState);
    }

    /**
     * PagerFace接口方法--页面显示或隐藏时触发
     *
     * @param isVisible 是否显示
     */
    @Override
    public void onPagerVisible(boolean isVisible) {
        //页面显示时自动调用shouldLoadData()方法加载数据
        if (isVisible) {
            shouldLoadData();
        }
    }

    /**
     * PagerFace接口方法--获取加载中状态时的View
     *
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存的参数
     * @return 加载中状态时的View
     */
    @Override
    public View getLoadingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mHelper.getLoadingView(inflater, container, savedInstanceState);
    }

    /**
     * PagerFace接口方法--获取空数据状态时显示的View
     *
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存的参数
     * @return 空数据状态时显示的View
     */
    @Override
    public View getEmptyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mHelper.getEmptyView(this, inflater, container, savedInstanceState);
    }

    /**
     * PagerFace接口方法--获取错误状态时显示的View
     *
     * @param inflater           布局映射器
     * @param container          容器
     * @param savedInstanceState 保存的参数
     * @return 错误状态时显示的View
     */
    @Override
    public View getErrorView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mHelper.getErrorView(this, inflater, container, savedInstanceState);
    }

    /**
     * PagerFace接口方法--初始化事件
     */
    @Override
    public void initListener() {
    }

    /**
     * PagerFace接口方法--主动加载数据
     */
    @Override
    public void shouldLoadData() {
        mHelper.shouldLoadData();
    }

    /**
     * PagerFace接口方法--重新重新加载数据
     *
     * @param args 携带的参数
     */
    @Override
    public void reloadData(Bundle args) {
    }

    /**
     * PagerFace接口方法--将页面切换为加载中状态
     */
    @Override
    public void tpgLoading() {
        mHelper.onLoading();
    }

    /**
     * PagerFace接口方法--将页面切换为成功状态
     */
    @Override
    public void tpgSuccess() {
        mHelper.onSuccess();
    }

    /**
     * PagerFace接口方法--将页面切换为空数据状态
     */
    @Override
    public void tpgEmpty() {
        mHelper.onEmpty();
    }

    /**
     * PagerFace接口方法--将页面切换为错误状态
     */
    @Override
    public void tpgError() {
        mHelper.onError();
    }
}
