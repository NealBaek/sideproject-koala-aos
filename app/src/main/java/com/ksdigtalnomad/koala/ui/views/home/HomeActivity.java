package com.ksdigtalnomad.koala.ui.views.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityHomeBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding mBinding;
    private HomeTapAdapter tapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mBinding.setActivity(this);

        tapAdapter = new HomeTapAdapter(getSupportFragmentManager());

        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(getResources().getString(R.string.tap1_title)));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(getResources().getString(R.string.tap2_title)));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(getResources().getString(R.string.tap3_title)));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(getResources().getString(R.string.tap4_title)));

        mBinding.homeTabViewPager.setAdapter(tapAdapter);
        mBinding.homeTabViewPager.setOffscreenPageLimit(4);
//        mBinding.homeTabViewPager.setPagingEnabled(true);
        mBinding.homeTabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.homeTabViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
