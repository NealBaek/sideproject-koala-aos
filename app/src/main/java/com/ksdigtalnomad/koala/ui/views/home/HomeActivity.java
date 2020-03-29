package com.ksdigtalnomad.koala.ui.views.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.AlarmDailyController;
import com.ksdigtalnomad.koala.databinding.ActivityHomeBinding;
import com.ksdigtalnomad.koala.helpers.ui.BadgeHelper;
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

        int[] tabTitleList = new int[] { R.string.tap1_title, R.string.tap2_title, R.string.tap3_title, R.string.tap4_title };

        for(int i = 0; i < tabTitleList.length; ++i){
            RelativeLayout tabView = new RelativeLayout(mBinding.tabLayout.getContext());
            tabView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            TextView tabTitle = new TextView(mBinding.tabLayout.getContext());
            tabTitle.setText(getResources().getString(tabTitleList[i]));
            tabTitle.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tabTitle.setGravity(Gravity.CENTER);
            tabView.addView(tabTitle);

            TabLayout.Tab tab = mBinding.tabLayout.newTab();
            tab.setCustomView(tabView);
            mBinding.tabLayout.addTab(tab);

            // TODO: 다음 업데이트
//            if(i == tabTitleList.length-1){
//                BadgeHelper.showBadge(BadgeHelper.Key.v036_settingTapName, tabView, 20, 2, 2f);
//            }

        }

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
//        mBinding.adView.loadAd(new AdRequest.Builder().build());
    }


    @Override
    public void onBackPressed() {
        ExitDialog.newInstance().show(getFragmentManager(), "Exit Dialog");
    }

    public void onSookdakBannerClick(){
        Log.d("ABC", "onSookdakBannerClick");
        String url ="http://www.sookdoc.com/product/%EC%88%99%EC%B7%A8%EB%8B%A5%ED%84%B0-%EC%88%99%EB%8B%A5-%EB%AC%B4%EB%A3%8C%EC%B2%B4%ED%97%98%ED%8C%A9-%ED%8F%89%EC%9D%BC-10%EB%AA%85-%EC%84%A0%EC%B0%A9%EC%88%9C-%EB%A7%88%EA%B0%90/143/category/57/display/1/?utm_source=koala&utm_medium=banner&utm_campaign=promotion";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);

        FBEventLogHelper.onCustomAdsClick(FBEventLogHelper.Partner.sookDak, FBEventLogHelper.Screen.mainBanner);

    }
}
