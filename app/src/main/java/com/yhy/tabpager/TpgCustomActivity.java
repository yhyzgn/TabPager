package com.yhy.tabpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yhy.tabnav.adapter.TpgAdapter;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.listener.OnPageChangedListener;
import com.yhy.tabnav.tpg.PagerFace;
import com.yhy.tabnav.widget.TpgView;
import com.yhy.tabpager.pager.factory.PagerFactory;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class TpgCustomActivity extends AppCompatActivity {
    private static final String[] TABS = {"菜单A", "菜单B菜单B菜单B", "菜单C"};

    private TpgView tvContent;
    //页面配置，只在当前TpgView有效
    private PagerConfig mConfig;
    private PagersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpg_custom);

        setTpgEmptyPage();

        tvContent = findViewById(R.id.tv_content);

        initData();

        initListener();
    }

    private void setTpgEmptyPage() {
        mConfig = new PagerConfig();
        mConfig.setEmptyViewLayoutId(R.layout.layout_view_empty, R.id.tv_retry);
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
                if (null != mAdapter) {
                    Bundle args = new Bundle();
                    args.putString("args", TABS[tvContent.getCurrentPager()]);
                    mAdapter.reloadDataForCurrentPager(args);
                }
            }
        });

        //页面滑动事件
        tvContent.setOnPageChangedListener(new OnPageChangedListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //第二页不可滑动
                tvContent.setScrollAble(position != 1);

                //第三页选项卡栏高度为0
                if (position == 2) {
                    tvContent.setTabHeight(0);
                } else {
                    tvContent.setTabHeight(48);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class PagersAdapter extends TpgAdapter<String> {

        public PagersAdapter(FragmentManager fm, PagerConfig config) {
            super(fm, Arrays.asList(TABS), config);
        }

        @Override
        public PagerFace getPager(int position) {
            return PagerFactory.create(position);
        }

        @Override
        public View getCustomTabView(int position, String data) {
            TextView tv = (TextView) LayoutInflater.from(TpgCustomActivity.this).inflate(R.layout.tab_custom_view, null);
            tv.setText(data);
            return tv;
        }
    }
}
