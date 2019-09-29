package com.ksdigtalnomad.koala.fcm;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
//    @Override public void onNewToken(String pushToken) {
//        super.onNewToken(pushToken);
//        PreferenceHelper.setPushToken(pushToken);
//        if (PreferenceHelper.isLogin()) postPushToken(pushToken);
//    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

    private void postPushToken(String pushToken) {
//        ServiceManager.getInstance().getUserService()
//                .postPushToken(pushToken, Constants.PLATFORM).enqueue(new Callback<Boolean>() {
//            @Override public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {}
//            @Override public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {}
//        });
    }
}
