package com.yhy.tabpager.pager.factory;

import com.yhy.tabnav.tpg.PagerFace;
import com.yhy.tabpager.pager.APager;
import com.yhy.tabpager.pager.BPager;
import com.yhy.tabpager.pager.CPager;
import com.yhy.tabpager.pager.DPager;
import com.yhy.tabpager.pager.EPager;
import com.yhy.tabpager.pager.FPager;
import com.yhy.tabpager.pager.GPager;
import com.yhy.tabnav.pager.TpgFragment;

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
            case 3:
                pager = new DPager();
                break;
            case 4:
                pager = new EPager();
                break;
            case 5:
                pager = new FPager();
                break;
            case 6:
                pager = new GPager();
                break;
            default:
                break;
        }

        return pager;
    }
}
