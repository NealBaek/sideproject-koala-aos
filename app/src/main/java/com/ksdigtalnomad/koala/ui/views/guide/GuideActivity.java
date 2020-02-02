package com.ksdigtalnomad.koala.ui.views.guide;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityGuideBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;

public class GuideActivity extends BaseActivity {

    public static final String GUIDE_1 = "GUIDE_1";
    public static final String GUIDE_2 = "GUIDE_2";
    public static final String GUIDE_3 = "GUIDE_3";
    public static final String GUIDE_4 = "GUIDE_4";

    private ActivityGuideBinding mBinding;


    private GuideAdapter viewPagerAdapter;


    public static Intent intent(Context context) {
        return new Intent(context, GuideActivity.class);
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_guide);
        mBinding.setActivity(this);

        this.viewPagerAdapter = new GuideAdapter(this);
        initViewPager();
    }

    private void initViewPager() {
        mBinding.pageIndicatorView.setCount(4);
        mBinding.pageIndicatorView.setSelected(0);
        mBinding.viewPager.setAdapter(viewPagerAdapter);
        mBinding.viewPager.addOnPageChangeListener(new onPageChangeListenerClass());
    }



    public void onNextBtnClick() {
        int current = mBinding.viewPager.getCurrentItem();
        mBinding.viewPager.setCurrentItem(current + 1);
    }

    public void onStartBtnClick() {
        startActivity(HomeActivity.intent(this));
        finish();
    }

    class onPageChangeListenerClass implements ViewPager.OnPageChangeListener {

        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override public void onPageSelected(int position) {
            if (position >= 3) {
                mBinding.nextBtn.setVisibility(View.GONE);
                mBinding.startBtn.setVisibility(View.VISIBLE);
            } else {
                mBinding.nextBtn.setVisibility(View.VISIBLE);
                mBinding.startBtn.setVisibility(View.GONE);
            }
        }

        @Override public void onPageScrollStateChanged(int state) {

        }
    }

}
