package com.ksdigtalnomad.koala.service.fcm;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.views.splash.SplashActivity;
import com.ksdigtalnomad.koala.helpers.ui.NotificationHelper;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            sendNotification(BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), remoteMessage.getData().get("body"));
        }
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String title, String content) {
        Intent newIntent = SplashActivity.intent(BaseApplication.getInstance());
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationHelper.sendNotification(title, content, newIntent);
    }


    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d("ABC", "onDeletedMessages");
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("ABC", "onNewToken: " + s);
    }

}
