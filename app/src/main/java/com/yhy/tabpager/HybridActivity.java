package com.yhy.tabpager;

import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yhy.tabnav.tpg.PagerFace;
import com.yhy.tabpager.pager.NavPager;
import com.yhy.tabpager.utils.ToastUtils;
import com.yhy.tabnav.adapter.NavAdapter;
import com.yhy.tabnav.config.PagerConfig;
import com.yhy.tabnav.entity.NavTab;
import com.yhy.tabnav.listener.OnPageChangedListener;
import com.yhy.tabnav.pager.TpgFragment;
import com.yhy.tabnav.widget.NavView;

import java.util.ArrayList;
import java.util.List;

public class HybridActivity extends AppCompatActivity {

    private static final List<NavTab> TAB_LIST = new ArrayList<>();

    private NavView bvContent;
    private PagerConfig mConfig;
    private ContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);

        if (TAB_LIST.size() < 4) {
            TAB_LIST.add(new NavTab("首页", R.drawable.tab_home_selector));
            TAB_LIST.add(new NavTab("贷款", R.drawable.tab_loan_selector));
            TAB_LIST.add(new NavTab("通知", R.drawable.tab_notice_selector));
            TAB_LIST.add(new NavTab("我的", R.drawable.tab_mine_selector));
        }

        bvContent = (NavView) findViewById(R.id.bv_content);

        //页面配置参数
        mConfig = new PagerConfig();
        mConfig.setEmptyViewLayoutId(R.layout.layout_view_empty, R.id.tv_retry);

        //设置适配器
        mAdapter = new ContentAdapter(getSupportFragmentManager(), TAB_LIST, mConfig);
        bvContent.setAdapter(mAdapter);

        //徽章测试
        bvContent.showCirclePointBadge(0);
        bvContent.showTextBadge(1, "2");

        //页面切换事件测试
        bvContent.setOnPageChangedListener(new OnPageChangedListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 适配器
     */
    private class ContentAdapter extends NavAdapter {
        public ContentAdapter(FragmentManager fm, List<NavTab> tabList, PagerConfig config) {
            super(fm, tabList, config);
        }

        @Override
        public PagerFace getPager(int position) {
            Bundle args = new Bundle();
            args.putBoolean("isHybrid", true);
            args.putBoolean("firstPage", position == 0);
            PagerFace fragment = new NavPager();
            fragment.getFragment().setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_LIST.size();
        }
    }
}
