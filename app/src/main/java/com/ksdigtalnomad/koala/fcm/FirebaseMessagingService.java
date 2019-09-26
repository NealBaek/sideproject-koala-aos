package com.ksdigtalnomad.koala.fcm;

import com.ksdigtalnomad.koala.util.PreferenceManager;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override public void onNewToken(String pushToken) {
        super.onNewToken(pushToken);
        PreferenceManager.setPushToken(pushToken);
        if (PreferenceManager.isLogin()) postPushToken(pushToken);
    }

    private void postPushToken(String pushToken) {
//        ServiceManager.getInstance().getUserService()
//                .postPushToken(pushToken, Constants.PLATFORM).enqueue(new Callback<Boolean>() {
//            @Override public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {}
//            @Override public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {}
//        });
    }
}
