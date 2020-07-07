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
import com.ksdigtalnomad.koala.data.MainDataController;
import com.ksdigtalnomad.koala.data.net.ServiceManager;
import com.ksdigtalnomad.koala.helpers.data.ADIDHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.views.guide.GuideActivity;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.helpers.data.FBRemoteConfigHelper;
import com.ksdigtalnomad.koala.helpers.data.LanguageHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.helpers.data.VersionCheckHelper;

import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends BaseActivity implements SplashView {

    private SplashPresenter presenter;

    public static Intent intent(Context context) {  return new Intent(context, SplashActivity.class);  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getHostUrl();
    }

    @Override
    public void moveToGuide() {
        startActivity(GuideActivity.intent(this));
        finish();
    }

    @Override
    public void moveToHome() {
        startActivity(HomeActivity.intent(this));
        finish();
    }

    @Override
    public void showErrorMessage(Throwable error) {
        ToastHelper.writeBottomLongToast(getResources().getString(R.string.warning_server_updating));
    }


}

