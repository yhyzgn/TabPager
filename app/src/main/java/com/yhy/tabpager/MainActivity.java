package com.yhy.tabpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import com.yhy.tabpager.pager.factory.PagerFactory;
import com.yhy.tabpager.utils.ToastUtils;
import com.yhy.tpg.adapter.TpgAdapter;
import com.yhy.tpg.pager.TpgFragment;
import com.yhy.tpg.config.PagerConfig;
import com.yhy.tpg.widget.TpgView;

public class MainActivity extends AppCompatActivity {
    private static final String[] TABS = {"菜单A", "菜单B", "菜单C", "菜单D", "菜单E", "菜单F", "菜单G", "菜单H"};

    private TpgView tvContent;
    private PagerConfig mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setGlobalLoadingPage();

        tvContent = (TpgView) findViewById(R.id.tv_content);

        initData();
    }

    private void setGlobalLoadingPage() {
        mConfig = new PagerConfig(this);
//        mConfig.setEmptyViewResId(R.layout.layout_view_empty);
    }

    private void initData() {
        tvContent.setAdapter(new TpgAdapter(getSupportFragmentManager(), mConfig) {
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
        });


        tvContent.setOnExpandListener(new TpgView.OnExpandListener() {
            @Override
            public void onExpand(View view) {
                ToastUtils.shortToast(MainActivity.this, "展开");
            }
        });
    }
}
