package com.ksdigtalnomad.koala.ui.views.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.google.android.gms.ads.AdRequest;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.AlarmDailyController;
import com.ksdigtalnomad.koala.databinding.ActivityHomeBinding;
import com.ksdigtalnomad.koala.service.alarm.AlarmDailyReceiver;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.views.dialogs.ExitDialog;
import com.ksdigtalnomad.koala.util.FBEventLogHelper;
import com.ksdigtalnomad.koala.util.PreferenceHelper;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding mBinding;
    private HomeTapAdapter tapAdapter;
    private static final String KEY_NOTI_ALARM_DAILY = "NOTI_ALARM_DAILY";

    public static Intent intent(Context context) {  return new Intent(context, HomeActivity.class);  }
    public static Intent intentFromNotiAlarmDaily(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(KEY_NOTI_ALARM_DAILY, true);
        return intent;
    }


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
        mBinding.homeTabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){ tapAdapter.refreshTodayTab(); }
                mBinding.homeTabViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(PreferenceHelper.isFirstOpen()){
            mBinding.homeTabViewPager.setCurrentItem(1);
            PreferenceHelper.setFirstOpen(false);
        }

        // startAlarmDaily
        AlarmDailyController.setAndStartAlarm();

        // From AlarmDaily Noti
        if(getIntent().getBooleanExtra(KEY_NOTI_ALARM_DAILY, false)){
            mBinding.homeTabViewPager.setCurrentItem(1);
            mBinding.homeTabViewPager.postDelayed(()->tapAdapter.moveToTodayDetail(), 1000);
            FBEventLogHelper.onAlarmDailyPushClick(PreferenceHelper.getAlarmDailySettingTimeStr());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.adView.loadAd(new AdRequest.Builder().build());
    }


    @Override
    public void onBackPressed() {
        ExitDialog.newInstance().show(getFragmentManager(), "Exit Dialog");
    }
}
