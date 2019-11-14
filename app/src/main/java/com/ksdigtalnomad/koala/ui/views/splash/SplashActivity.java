package com.ksdigtalnomad.koala.ui.views.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.views.guide.GuideActivity;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.util.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.util.LanguageHelper;
import com.ksdigtalnomad.koala.util.PreferenceHelper;
import com.ksdigtalnomad.koala.util.ToastHelper;
import com.ksdigtalnomad.koala.util.VersionCheckHelper;


public class SplashActivity extends BaseActivity {

    private SplashHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LanguageHelper.initLanguage(this);
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
                ToastHelper.writeBottomLongToast("서버 업데이트 중입니다.");
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
                // @TODO: Alarm Service 등록

                activity.startActivity(GuideActivity.intent(activity));
                activity.finish();
            }else{
                activity.startActivity(HomeActivity.intent(activity));
                activity.finish();
            }
        }

    }
}
