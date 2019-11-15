package com.ksdigtalnomad.koala.service.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.AlarmDaily;
import com.ksdigtalnomad.koala.data.models.AlarmDailyBody;
import com.ksdigtalnomad.koala.service.PackageReceiver;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.util.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.util.LanguageHelper;
import com.ksdigtalnomad.koala.util.PreferenceHelper;

import java.util.ArrayList;

/**
 * Created by ooddy on 14/11/2019.
 */

public class DailyAlarmJobIntentService extends JobIntentService {

    private final static int NOTICATION_ID = 111;
    private static final String CHANNEL_ID = "koala_alarm_daily";


    /* Give the Job a Unique Id */
    private static final int JOB_ID = 1000;
    public static void enqueueWork(Context ctx, Intent intent) {
        enqueueWork(ctx, DailyAlarmJobIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        // 1. 푸시 허용 & 어제 입력을 안했으면 푸시 알림 @TODO:
//        if(PreferenceHelper.isAlarmDailyEnabled() && !CalendarDataController.getYesterdayModel().isSaved){
        if(!CalendarDataController.getYesterdayModel().isSaved){

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
            PendingIntent pendingIntent = PendingIntent.getActivity(BaseApplication.getInstance(), 0 /* Request code */, newIntent,
                    PendingIntent.FLAG_ONE_SHOT);


            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(BaseApplication.getInstance(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(alarmDailyBody.getTitle())
                            .setContentText(alarmDailyBody.getContent())
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) BaseApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        "코알라 테스트 채널",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(NOTICATION_ID /* ID of notification */, notificationBuilder.build());
        }


        // 2. 알람 리셋
        DailyAlarmReceiver.setAlarm();
        stopSelf();
    }

}