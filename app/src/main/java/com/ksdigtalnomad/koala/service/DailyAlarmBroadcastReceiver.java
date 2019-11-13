package com.ksdigtalnomad.koala.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.AlarmDaily;
import com.ksdigtalnomad.koala.ui.views.home.HomeActivity;
import com.ksdigtalnomad.koala.util.FBRemoteControlHelper;

/**
 * Created by ooddy on 13/11/2019.
 */

public class DailyAlarmBroadcastReceiver extends BroadcastReceiver {
    private final static int NOTICATION_ID = 111;
    private static final String CHANNEL_ID = "koala_alarm_daily";


    @Override
    public void onReceive(Context context, Intent oldIntent) {

        AlarmDaily alarmDaily = new Gson().fromJson(FBRemoteControlHelper.getInstance().getAlarmDaily(), AlarmDaily.class);

        Log.d("ABC", "onReceive");
        Log.d("ABC", alarmDaily.toString());


        Intent newIntent = HomeActivity.intent(context);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, newIntent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(alarmDaily.getTitle())
                        .setContentText(alarmDaily.getContent())
                        .setAutoCancel(false)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "코알라 테스트 채널",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTICATION_ID /* ID of notification */, notificationBuilder.build());




//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_foreground) //알람 아이콘
//                .setContentTitle(alarmDaily.getTitle())  //알람 제목
//                .setContentText(alarmDaily.getContent()) //알람 내용
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT); //알람 중요도
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(NOTICATION_ID, builder.build()); //알람 생성
    }
}
