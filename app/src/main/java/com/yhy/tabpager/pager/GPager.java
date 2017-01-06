package com.yhy.tabpager.pager;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yhy.tpg.handler.ResultHandler;
import com.yhy.tpg.pager.TpgFragment;

import java.util.Random;

public class GPager extends TpgFragment {

    @Override
    protected View getSuccessView() {
        TextView tv = new TextView(getContext());
        tv.setText("G页面加载成功");
        tv.setTextColor(Color.RED);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    protected void initData(final ResultHandler handler) {
        final Random random = new Random();
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
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
