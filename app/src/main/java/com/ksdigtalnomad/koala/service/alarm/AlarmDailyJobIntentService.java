package com.ksdigtalnomad.koala.service.alarm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.ksdigtalnomad.koala.data.AlarmDailyController;
import com.ksdigtalnomad.koala.data.models.alarmDaily.AlarmDailyBody;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.helpers.ui.NotificationHelper;

/**
 * Created by ooddy on 14/11/2019.
 */

public class AlarmDailyJobIntentService extends JobIntentService implements AlarmDailyController.CheckInterface {

    /* Give the Job a Unique Id */
    private static final int JOB_ID = 1000;
    public static void enqueueWork(Context ctx, Intent intent) {
        enqueueWork(ctx, AlarmDailyJobIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        AlarmDailyController.checkAlarmDailyState(this);
    }


    // AlarmDailyController - CheckInterface
    @Override
    public void stop() {
        stopSelf();
    }
    @Override
    public void reset() {
        AlarmDailyController.setAndStartAlarm();
        stopSelf();
    }
    @Override
    public void sendAndReset() {
        AlarmDailyBody alarmDailyBody = AlarmDailyController.getAlarmDailyData();

        Intent newIntent = HomeActivity.intentFromNotiAlarmDaily(BaseApplication.getInstance());
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationHelper.sendNotification(alarmDailyBody.getTitle(), alarmDailyBody.getContent(), newIntent);

        AlarmDailyController.setAndStartAlarm();
        stopSelf();
    }
}