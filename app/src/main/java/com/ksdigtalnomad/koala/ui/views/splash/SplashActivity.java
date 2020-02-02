package com.ksdigtalnomad.koala.ui.views.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.AlarmDailyController;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.views.guide.GuideActivity;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.helpers.data.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.helpers.data.LanguageHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.helpers.data.VersionCheckHelper;


public class SplashActivity extends BaseActivity {

    private SplashHandler handler;

    public static Intent intent(Context context) {  return new Intent(context, SplashActivity.class);  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LanguageHelper.initLanguage(this);

        PreferenceHelper.setOpenCount(PreferenceHelper.getOpenCunt() + 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 1. Version 정보 가져오기
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
