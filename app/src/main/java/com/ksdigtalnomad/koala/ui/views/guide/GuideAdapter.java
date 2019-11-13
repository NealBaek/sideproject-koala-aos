package com.ksdigtalnomad.koala.ui.views.guide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;

    public GuideAdapter(BaseActivity activity) {
        super(activity.getSupportFragmentManager());

        this.fragmentList = new ArrayList<>();
        this.fragmentList.add(GuideFragment.newInstance(GuideActivity.GUIDE_FIRST));
        this.fragmentList.add(GuideFragment.newInstance(GuideActivity.GUIDE_SECOND));
        this.fragmentList.add(GuideFragment.newInstance(GuideActivity.GUIDE_THIRD));
    }

    @Override public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override public int getCount() {
        return fragmentList.size();
    }
}
