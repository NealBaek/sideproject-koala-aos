package com.ksdigtalnomad.koala.ui.views.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;


public class SplashActivity extends BaseActivity {

    private SplashHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new SplashHandler(this);
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    private static class SplashHandler extends Handler {
        private final Activity activity;
        SplashHandler(Activity activity) { this.activity = activity; }

        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            LanguageUtil.initLanguage(activity);

            // 1. RemoteControl -> Host Url
            // 2. Version 정보 가져오기
            // 2-1. Version 정보 있으면

            // 2-2. Version 정보 없으면


            activity.startActivity(HomeActivity.intent(activity));
            activity.finish();
        }


    }
}
