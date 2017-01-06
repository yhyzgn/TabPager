package com.yhy.tabpager.pager;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yhy.tpg.handler.ResultHandler;
import com.yhy.tpg.pager.TpgFragment;

import java.util.Random;

public class APager extends TpgFragment {

    @Override
    protected View getSuccessView() {
        TextView tv = new TextView(getContext());
        tv.setText("A页面加载成功");
        tv.setTextColor(Color.RED);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    /**
     * 由于该页面是第一页，所以需要重写该方法，并返回true
     *
     * @return 是否首先加载
     */
    @Override
    public boolean shouldLoadDataAtFirst() {
        return true;
    }

    @Override
    protected void initData(final ResultHandler handler) {
        final Random random = new Random();
        new Thread() {
            @Override
            public void run() {
                //模拟网络加载延迟
                SystemClock.sleep(3000);

                //数据加载结束后，需要手动刷新页面状态
                int temp = random.nextInt(3);
                switch (temp) {
                    case 0:
                        handler.sendSuccessHandler();
                        break;
                    case 1:
                        handler.sendErrorHandler();
                        break;
                    case 2:
                        handler.sendEmptyHandler();
                        break;
                    default:
                        break;
                }
            }
        }.start();
    }
}
