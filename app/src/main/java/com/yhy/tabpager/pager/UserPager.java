package com.yhy.tabpager.pager;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yhy.tabnav.pager.TpgFragment;
import com.yhy.tabpager.R;
import com.yhy.tabpager.entity.User;
import com.yhy.tabpager.utils.ImgUrls;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-11-06 10:29
 * version: 1.0.0
 * desc   :
 */
public class UserPager extends TpgFragment {
    private final List<User> mUserList = new ArrayList<>();
    private RecyclerView rvUser;

    @Override
    public View getSuccessView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_user_rv, null, false);
        rvUser = view.findViewById(R.id.rv_user);
        return view;
    }

    @Override
    public void initData() {
        mUserList.add(new User("潘恩依", ImgUrls.getAImgUrl(), 23, "王生安王生安王生安王生安王生安王生安王生安王生安"));
        mUserList.add(new User("王施峪", ImgUrls.getAImgUrl(), 53, "夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜夏劲釜"));
        mUserList.add(new User("郭磊留", ImgUrls.getAImgUrl(), 32, "汤丞昱"));
        mUserList.add(new User("柯纤翊", ImgUrls.getAImgUrl(), 76, "欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界欧贡界"));
        mUserList.add(new User("张昧谡", ImgUrls.getAImgUrl(), 12, "梁夜翊梁夜翊梁夜翊梁夜翊梁夜翊"));

        tpgSuccess();

        rvUser.setLayoutManager(new LinearLayoutManager(mActivity));
        rvUser.setAdapter(new BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_user_rv, mUserList) {
            @Override
            protected void convert(BaseViewHolder helper, User item) {
                Glide.with(mContext).load(item.avatar).into((ImageView) helper.getView(R.id.iv_avatar));
                helper.setText(R.id.tv_name, item.name);
                helper.setText(R.id.tv_introduction, item.introduction);
            }
        });
    }
}
