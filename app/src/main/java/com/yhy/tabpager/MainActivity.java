package com.yhy.tabpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_tpg).setOnClickListener(this);
        findViewById(R.id.tv_nav).setOnClickListener(this);
        findViewById(R.id.tv_hybrid).setOnClickListener(this);
        findViewById(R.id.tv_recycler).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tpg:
                startActivity(new Intent(this, TpgActivity.class));
                break;
            case R.id.tv_nav:
                startActivity(new Intent(this, NavActivity.class));
                break;
            case R.id.tv_hybrid:
                startActivity(new Intent(this, HybridActivity.class));
                break;
            case R.id.tv_recycler:
                startActivity(new Intent(this, RecyclerActivity.class));
                break;
            default:
                break;
        }
    }
}
