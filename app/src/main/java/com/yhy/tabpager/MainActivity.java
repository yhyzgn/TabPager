package com.yhy.tabpager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_tpg).setOnClickListener(v -> to(TpgActivity.class));
        findViewById(R.id.tv_tpg_custom).setOnClickListener(v -> to(TpgCustomActivity.class));
        findViewById(R.id.tv_nav).setOnClickListener(v -> to(NavActivity.class));
        findViewById(R.id.tv_hybrid).setOnClickListener(v -> to(HybridActivity.class));
        findViewById(R.id.tv_recycler).setOnClickListener(v -> to(RecyclerActivity.class));
    }

    private void to(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }
}
