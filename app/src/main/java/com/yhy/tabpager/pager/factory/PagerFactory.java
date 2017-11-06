package com.yhy.tabpager.pager.factory;

import com.yhy.tabnav.tpg.PagerFace;
import com.yhy.tabpager.pager.APager;
import com.yhy.tabpager.pager.BPager;
import com.yhy.tabpager.pager.CPager;

public class PagerFactory {

    public static PagerFace create(int position) {
        PagerFace pager = null;

        switch (position) {
            case 0:
                pager = new APager();
                break;
            case 1:
                pager = new BPager();
                break;
            case 2:
                pager = new CPager();
                break;
            default:
                break;
        }

        return pager;
    }
}
