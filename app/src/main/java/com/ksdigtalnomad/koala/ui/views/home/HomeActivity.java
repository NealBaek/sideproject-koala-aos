package com.ksdigtalnomad.koala.ui.views.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityHomeBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding mBinding;
    private HomeTapAdapter tapAdapter;

    public static Intent intent(Context context) {  return new Intent(context, HomeActivity.class);  }


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


        loadBannerRequest();


    }

    private void loadBannerRequest(){
        MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));

        AdRequest adRequest = new AdRequest.Builder().build();

        mBinding.adView.loadAd(adRequest);
        mBinding.adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.d("ABC", "banner_home onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d("ABC", "banner_home onAdFailedToLoad : " + i);

            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                Log.d("ABC", "banner_home onAdLeftApplication");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.d("ABC", "banner_home onAdOpened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("ABC", "banner_home onAdLoaded");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.d("ABC", "banner_home onAdClicked");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.d("ABC", "banner_home onAdImpression");
            }
        });
    }

}
