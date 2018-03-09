package com.yhy.tabpager.pager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yhy.tabnav.pager.TpgFragment;
import com.yhy.tabnav.tpg.PagerFace;
import com.yhy.tabpager.MainActivity;
import com.yhy.tabpager.R;
import com.yhy.tabpager.utils.ToastUtils;
import com.yhy.tabnav.adapter.TpgAdapter;
import com.yhy.tabnav.listener.OnPageChangedListener;
import com.yhy.tabnav.widget.TpgView;

import java.util.Arrays;
import java.util.Random;

public class NavPager extends TpgFragment<MainActivity> {
    private static final String[] TABS = {"菜单A", "菜单B", "菜单C"};
    private TpgView tpgView;
    private PagersAdapter mAdapter;
    private Bundle mArgs;
    private boolean mIsHybrid;

    @Override
    public View getSuccessView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mArgs = getArguments();
        mArgs = getParams();
        mIsHybrid = mArgs.getBoolean("isHybrid");
        if (mIsHybrid) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.activity_tpg, null);
            tpgView = (TpgView) view.findViewById(R.id.tv_content);
            return view;
        }

        TextView tv = new TextView(getContext());
        tv.setText("Nav页面加载成功");
        tv.setTextColor(Color.RED);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    public void initData() {
        final Random random = new Random();
        new Thread() {
            @Override
            public void run() {
                //模拟网络加载延迟
                SystemClock.sleep(2000);

                //数据加载结束后，需要手动刷新页面状态
                int temp = random.nextInt(3);
                switch (temp) {
                    case 0:
//                        mRltHandler.tpgSuccess();
                        tpgSuccess();
                        break;
                    case 1:
//                        mRltHandler.tpgError();
                        tpgError();
                        break;
                    case 2:
//                        mRltHandler.tpgEmpty();
                        tpgEmpty();
                        break;
                    default:
                        break;
                }
            }
        }.start();

        if (mIsHybrid) {
            //mAdapter = new PagersAdapter(getFragmentManager());
        /*
        //这里需要用getChildFragmentManager()
        getChildFragmentManager()是fragment中的方法, 返回的是管理当前fragment内部子fragments的manager.
        getFragmentManager()在activity和fragment中都有.
        在activity中, 如果用的是v4 support库, 方法应该用getSupportFragmentManager(),
        返回的是管理activity中fragments的manager.
        在fragment中, 还叫getFragmentManager(), 返回的是把自己加进来的那个manager.

        也即, 如果fragment在activity中, fragment.getFragmentManager()得到的是activity中管理fragments的那个manager.
        如果fragment是嵌套在另一个fragment中, fragment.getFragmentManager()
        得到的是它的parent的getChildFragmentManager().

        总结就是: getFragmentManager()是本级别管理者, getChildFragmentManager()是下一级别管理者.
        这实际上是一个树形管理结构.
        */
            mAdapter = new PagersAdapter(getChildFragmentManager());
            tpgView.setAdapter(mAdapter);
        }
    }

    @Override
    public void initListener() {
        if (mIsHybrid) {
            tpgView.setOnPageChangedListener(new OnPageChangedListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int
                        positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    ToastUtils.shortToast("position = " + position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    private class PagersAdapter extends TpgAdapter<String> {

        public PagersAdapter(FragmentManager fm) {
            super(fm, Arrays.asList(TABS));
        }

        @Override
        public PagerFace getPager(int position) {
            Bundle args = new Bundle();
            args.putBoolean("firstPage", position == 0);
            PagerFace fragment = new HybridPager();
            fragment.getFragment().setArguments(args);
            return fragment;
        }

        @Override
        public CharSequence getTitle(int position, String data) {
            return data;
        }
    }
}