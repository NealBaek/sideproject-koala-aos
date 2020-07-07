package com.ksdigtalnomad.koala.ui.views.splash;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.AlarmDailyController;
import com.ksdigtalnomad.koala.data.MainDataController;
import com.ksdigtalnomad.koala.data.net.ServiceManager;
import com.ksdigtalnomad.koala.helpers.data.ADIDHelper;
import com.ksdigtalnomad.koala.helpers.data.FBRemoteConfigHelper;
import com.ksdigtalnomad.koala.helpers.data.LanguageHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.helpers.data.VersionCheckHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.views.guide.GuideActivity;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;

import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter {

    private SplashView view;
    private SplashHandler handler;

    private static final String drink = "drink";
    private static final String friend = "friend";

    SplashPresenter(SplashView view) {
        this.view = view;

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

        LanguageHelper.initLanguage(BaseApplication.getInstance());

        PreferenceHelper.setOpenCount(PreferenceHelper.getOpenCunt() + 1);
    }


    void getHostUrl(){
        FBRemoteConfigHelper.getInstance().getHostUrl((hostUrl)->{
            if(hostUrl == null || hostUrl == "") {
                view.showErrorMessage(new Error());
                return;
            }else{
                Log.d("ABC", "FB - API URL: " + hostUrl);

                BaseApplication.getInstance().baseUrl = hostUrl;

                checkVersion();

            }
        });
    }


    private void checkVersion(){
        ServiceManager.getInstance().getAppVersionService().getAppVersion("aOS")
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (result) -> {

                            Log.d("ABC", "API 앱 버전: " + result.toString());

                            VersionCheckHelper.getUpdateState(result, (SplashActivity) view, ()->{

                                // drink
                                getUnitList(drink);
                                getCategoryList(drink);

                                // friend
                                getUnitList(friend);
                                getCategoryList(friend);

                                // 앱 시
                                handler = new SplashHandler(view);
                                handler.sendEmptyMessageDelayed(0, 1000);

                            });
                        },
                        (error) -> view.showErrorMessage(error)
                );
    }

    void getUnitList(String type) {
        ServiceManager.getInstance().getDefaultDataService().getUnitList(type)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (result) -> {
                            Log.d("ABC", "getUnitList: " + result.toString());
                            switch (type){
                                case drink:
                                    MainDataController.setUnitDrinkList(result);
                                    Log.d("ABC", "주류 단위 리스트:" + MainDataController.getUnitDrinkList().toString());
                                    break;
                                case friend:
                                    break;
                            }
                        },
                        (error) -> view.showErrorMessage(error)
                );
    }

    void getCategoryList(String type){
        ServiceManager.getInstance().getDefaultDataService().getCategoryList(type)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (result) -> {
                            Log.d("ABC", "getCategoryList: " + result.toString());
                            switch (type){
                                case drink:
                                    MainDataController.setCategoryDrinkList(result);
                                    Log.d("ABC", "주류 카테고리 리스트:" + MainDataController.getCategoryDrinkList().toString());
                                    break;
                                case friend:
                                    break;
                            }
                        },
                        (error) -> view.showErrorMessage(error)
                );
    }



    private static class SplashHandler extends Handler {
        private SplashView view;

        SplashHandler(SplashView view) { this.view = view; }

        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(PreferenceHelper.isFirstOpen()){
                PreferenceHelper.setFirstOpen(false);
                AlarmDailyController.setAndStartAlarm();

                view.moveToGuide();
            }else{
                view.moveToHome();
            }
        }

    }
}
