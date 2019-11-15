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
import com.ksdigtalnomad.koala.service.PackageReceiver;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.util.FBRemoteControlHelper;

/**
 * Created by ooddy on 14/11/2019.
 */

public class DailyAlarmJobIntentService extends JobIntentService {

    private final static int NOTICATION_ID = 111;
    private static final String CHANNEL_ID = "koala_alarm_daily";


    /* Give the Job a Unique Id */
    private static final int JOB_ID = 1000;
    public static void enqueueWork(Context ctx, Intent intent) {
        Log.d("ABC", "DailyAlarmJobIntentService enqueueWork");

        enqueueWork(ctx, DailyAlarmJobIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        /* your code here */

        AlarmDaily alarmDaily = new Gson().fromJson(FBRemoteControlHelper.getInstance().getAlarmDaily(), AlarmDaily.class);

        Log.d("ABC", "DailyAlarmJobIntentService onHandleWork");


        Intent newIntent = HomeActivity.intentFromNotiAlarmDaily(BaseApplication.getInstance());
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(BaseApplication.getInstance(), 0 /* Request code */, newIntent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(BaseApplication.getInstance(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(alarmDaily.getTitle())
                        .setContentText(alarmDaily.getContent())
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

        Log.d("ABC", "DailyAlarmJobIntentService notify");

        /* reset the alarm */
        DailyAlarmReceiver.setAlarm();
        stopSelf();
    }

}