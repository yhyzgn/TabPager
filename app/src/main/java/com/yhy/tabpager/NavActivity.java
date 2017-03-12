package com.yhy.tabpager;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yhy.tabpager.pager.BtgPager;
import com.yhy.tpg.adapter.NavAdapter;
import com.yhy.tpg.config.PagerConfig;
import com.yhy.tpg.entity.NavTab;
import com.yhy.tpg.pager.TpgFragment;
import com.yhy.tpg.widget.NavView;

import java.util.ArrayList;
import java.util.List;

public class NavActivity extends AppCompatActivity {

    private static final List<NavTab> TAB_LIST = new ArrayList<>();

    private NavView bvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        if (TAB_LIST.size() < 4) {
            TAB_LIST.add(new NavTab("首页", R.drawable.tab_home_selector));
            TAB_LIST.add(new NavTab("贷款", R.drawable.tab_loan_selector));
            TAB_LIST.add(new NavTab("通知", R.drawable.tab_notice_selector));
            TAB_LIST.add(new NavTab("我的", R.drawable.tab_mine_selector));
        }

        bvContent = (NavView) findViewById(R.id.bv_content);

        bvContent.setAdapter(new ContentAdapter(getSupportFragmentManager(), TAB_LIST, null));
    }

    private class ContentAdapter extends NavAdapter {

        public ContentAdapter(FragmentManager fm, List<NavTab> tabList, PagerConfig config) {
            super(fm, tabList, config);
        }

        @Override
        public TpgFragment getPager(int position) {
            return new BtgPager(position);
        }

        @Override
        public int getCount() {
            return TAB_LIST.size();
        }
    }
}
