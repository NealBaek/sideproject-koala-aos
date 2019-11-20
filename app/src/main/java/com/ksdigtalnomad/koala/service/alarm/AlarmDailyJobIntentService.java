package com.ksdigtalnomad.koala.service.alarm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.data.models.alarmDaily.AlarmDaily;
import com.ksdigtalnomad.koala.data.models.alarmDaily.AlarmDailyBody;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.util.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.util.LanguageHelper;
import com.ksdigtalnomad.koala.util.NotificationHelper;
import com.ksdigtalnomad.koala.util.PreferenceHelper;

import java.util.ArrayList;

/**
 * Created by ooddy on 14/11/2019.
 */

public class AlarmDailyJobIntentService extends JobIntentService {

    /* Give the Job a Unique Id */
    private static final int JOB_ID = 1000;
    public static void enqueueWork(Context ctx, Intent intent) {
        enqueueWork(ctx, AlarmDailyJobIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        // 1. 푸시 허용 & 어제 입력을 안했으면 푸시 알림 @TODO:
        if(PreferenceHelper.isAlarmDailyEnabled() && !CalendarDataController.getYesterdayModel().isSaved){

            // 알림 내용 받아오기
            AlarmDaily alarmDaily = new Gson().fromJson(FBRemoteControlHelper.getInstance().getAlarmDaily(), AlarmDaily.class);
            AlarmDailyBody alarmDailyBody = AlarmDailyBody.builder().build();
            ArrayList<AlarmDailyBody> bodyList =  alarmDaily.getBodyList();
            if(bodyList == null || bodyList.size() <= 0) return;
            for(AlarmDailyBody item : bodyList){
                if(LanguageHelper.isSameLanguage(item.getLangCode())){
                    alarmDailyBody = item;
                    break;
                }
            }

            Intent newIntent = HomeActivity.intentFromNotiAlarmDaily(BaseApplication.getInstance());
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            NotificationHelper.sendNotification(alarmDailyBody.getTitle(), alarmDailyBody.getContent(), newIntent);
        }


        // 2. 알람 리셋
        AlarmDailyReceiver.setAlarm();
        stopSelf();
    }

}