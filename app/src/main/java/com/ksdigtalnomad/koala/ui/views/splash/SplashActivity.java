package com.ksdigtalnomad.koala.ui.views.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.AlarmDailyController;
import com.ksdigtalnomad.koala.data.net.ServiceManager;
import com.ksdigtalnomad.koala.helpers.data.ADIDHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.views.guide.GuideActivity;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.helpers.data.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.helpers.data.LanguageHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.helpers.data.VersionCheckHelper;

import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends BaseActivity {

    private SplashHandler handler;

    public static Intent intent(Context context) {  return new Intent(context, SplashActivity.class);  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Adid 저장
        ADIDHelper.saveAdid((adId)->{
            if(adId != null && !adId.isEmpty()){
                PreferenceHelper.setAdid(adId);
                FirebaseAnalytics.getInstance(BaseApplication.getInstance()).setUserId(adId);
            }
        });


        // 첫 오픈 시 캘린더 데이터 생성
        if(PreferenceHelper.isFirstOpen()){
            Executors.newScheduledThreadPool(1).execute(()->{
                CalendarDataController.getCalendarModel();
            });
        }


        LanguageHelper.initLanguage(this);

        PreferenceHelper.setOpenCount(PreferenceHelper.getOpenCunt() + 1);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 1. 마이그레이션 체크 // 21.02.23
        if(!PreferenceHelper.isMg1Done() && !PreferenceHelper.isFirstOpen()){
            PreferenceHelper.setMg1Done(true);
            CalendarDataController.migrate1();
        }

        // 2. Version 정보 가져오기
        getVersion();
    }

    private void getVersion(){
        FBRemoteControlHelper.getInstance().getVersion((versionStr)->{
            if(versionStr == null || versionStr == "") {
                ToastHelper.writeBottomLongToast(getResources().getString(R.string.warning_server_updating));
                return;
            }else{

                VersionCheckHelper.getUpdateState(versionStr, this, ()->{
                    handler = new SplashHandler(this);
                    handler.sendEmptyMessageDelayed(0, 1000);
                });
            }
        });
    }

    private static class SplashHandler extends Handler {
        private final Activity activity;
        SplashHandler(Activity activity) { this.activity = activity; }

        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(PreferenceHelper.isFirstOpen()){
                PreferenceHelper.setFirstOpen(false);
                AlarmDailyController.setAndStartAlarm();

                activity.startActivity(GuideActivity.intent(activity));
                activity.finish();
            }else{
                activity.startActivity(HomeActivity.intent(activity));
                activity.finish();
            }
        }

    }
}
