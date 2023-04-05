package com.yhy.tabpager;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.yhy.tabnav.adapter.TpgAdapter;
import com.yhy.tabnav.tpg.PagerFace;
import com.yhy.tabnav.widget.TpgView;
import com.yhy.tabpager.entity.User;
import com.yhy.tabpager.pager.UserPager;
import com.yhy.tabpager.utils.ImgUrls;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-11-06 9:53
 * version: 1.0.0
 * desc   :
 */
public class RecyclerActivity extends AppCompatActivity {
    private final List<User> mUserList = new ArrayList<>();
    private final List<String> mTitleList = new ArrayList<>();

    private RecyclerView rvContent;
    private TpgView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        rvContent = findViewById(R.id.rv_content);
        tvContent = findViewById(R.id.tv_content);

        mUserList.add(new User("王生安", ImgUrls.getAImgUrl(), 23, "王生安王生安王生安王生安王生安王生安王生安王生安"));
        mUserList.add(new User("夏劲釜", ImgUrls.getAImgUrl(), 53, "夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜"));
        mUserList.add(new User("汤丞昱", ImgUrls.getAImgUrl(), 32, "汤丞昱"));
        mUserList.add(new User("欧贡界", ImgUrls.getAImgUrl(), 76, "欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界"));
        mUserList.add(new User("梁夜翊", ImgUrls.getAImgUrl(), 12, "梁夜翊梁夜翊梁夜翊梁夜翊梁夜翊"));

        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(new BaseQuickAdapter<>(R.layout.item_user_rv, mUserList) {
            @Override
            protected void convert(@NotNull BaseViewHolder holder, User item) {
                Glide.with(RecyclerActivity.this).load(item.avatar).into((ImageView) holder.getView(R.id.iv_avatar));
                holder.setText(R.id.tv_name, item.name);
                holder.setText(R.id.tv_introduction, item.introduction);
            }
        });

        mTitleList.add("第一页");
        mTitleList.add("第二页");
        mTitleList.add("第三页");

        tvContent.setAdapter(new TpgAdapter<String>(getSupportFragmentManager(), mTitleList) {
            @Override
            public PagerFace getPager(int position) {
                return new UserPager();
            }

            @Override
            public CharSequence getTitle(int position, String data) {
                return data;
            }
        });
    }
}
