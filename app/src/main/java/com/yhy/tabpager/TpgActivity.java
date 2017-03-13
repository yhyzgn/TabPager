package com.yhy.tabpager;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yhy.tabpager.pager.factory.PagerFactory;
import com.yhy.tpg.adapter.TpgAdapter;
import com.yhy.tpg.listener.OnPageChangedListener;
import com.yhy.tpg.pager.TpgFragment;
import com.yhy.tpg.config.PagerConfig;
import com.yhy.tpg.widget.TpgView;

public class TpgActivity extends AppCompatActivity {
    private static final String[] TABS = {"菜单A", "菜单B", "菜单C", "菜单D", "菜单E", "菜单F", "菜单G", "菜单H"};

    private TpgView tvContent;
    //页面配置，只在当前TpgView有效
    private PagerConfig mConfig;
    private PagersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpg);

        //初始化ToastUtils，最后要在Application中初始化
//        ToastUtils.init(this);

        setTpgEmptyPage();

        tvContent = (TpgView) findViewById(R.id.tv_content);

        initData();

        initListener();
    }

    private void setTpgEmptyPage() {
        mConfig = new PagerConfig(this);
//        mConfig.setEmptyViewResId(R.layout.layout_view_empty);
    }

    private void initData() {
        mAdapter = new PagersAdapter(getSupportFragmentManager(), mConfig);
        tvContent.setAdapter(mAdapter);
    }

    private void initListener() {
        //扩展按钮点击事件
        tvContent.setOnExpandListener(new TpgView.OnExpandListener() {
            @Override
            public void onExpand(View view) {
//                if (null != mAdapter) {
//                    mAdapter.reloadDataForCurrentPager(TABS[tvContent.getCurrentPager()]);
//                }

                startActivity(new Intent(TpgActivity.this, NavActivity.class));
            }
        });

        //页面滑动事件
        tvContent.setOnPageChangedListener(new OnPageChangedListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class PagersAdapter extends TpgAdapter {

        public PagersAdapter(FragmentManager fm, PagerConfig config) {
            super(fm, config);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Override
        public TpgFragment getPager(int position) {
            return PagerFactory.create(position);
        }
    }
}
