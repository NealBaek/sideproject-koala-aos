package com.ksdigtalnomad.koala.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

/**
 * Created by ooddy on 19/11/2019.
 */

public class NotificationHelper {
    private final static int NOTICATION_ID = 111;
    private static final String CHANNEL_ID = "koala_channel";
    private static final String CHANNEL_NAME = "Koala Channel";

    public static void sendNotification(String title, String content, Intent newIntent){
        PendingIntent pendingIntent = PendingIntent.getActivity(BaseApplication.getInstance(), 0 /* Request code */, newIntent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(BaseApplication.getInstance(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_push_small)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) BaseApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTICATION_ID, notificationBuilder.build());
    }
}
