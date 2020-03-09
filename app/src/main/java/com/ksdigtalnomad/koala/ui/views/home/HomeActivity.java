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
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.views.dialogs.ExitDialog;
import com.ksdigtalnomad.koala.helpers.data.FBEventLogHelper;
import com.ksdigtalnomad.koala.helpers.ui.InterviewHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;

import static com.ksdigtalnomad.koala.ui.views.home.HomeTapAdapter.FRAGMENT_CNT;
import static com.ksdigtalnomad.koala.ui.views.home.HomeTapAdapter.POS_STATISTICS;
import static com.ksdigtalnomad.koala.ui.views.home.HomeTapAdapter.POS_TODAY;

public class HomeActivity extends BaseActivity {
    public ActivityHomeBinding mBinding;
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
        mBinding.homeTabViewPager.setOffscreenPageLimit(FRAGMENT_CNT);
        mBinding.homeTabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == POS_TODAY){ tapAdapter.refreshTodayTab(); }
                else if(tab.getPosition() == POS_STATISTICS){ tapAdapter.refreshStatisticsTab(); }
                mBinding.homeTabViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // startAlarmDaily
        AlarmDailyController.setAndStartAlarm();

        // From AlarmDaily Noti
        if(getIntent().getBooleanExtra(KEY_NOTI_ALARM_DAILY, false)){
            mBinding.homeTabViewPager.postDelayed(()->tapAdapter.moveToTodayDetail(), 1000);
            FBEventLogHelper.onAlarmDailyPushClick(PreferenceHelper.getAlarmDailySettingTimeStr());
        }


        // @TODO: 설문 추가
//        InterviewHelper.showWhythisappIfPossible(getFragmentManager());
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
