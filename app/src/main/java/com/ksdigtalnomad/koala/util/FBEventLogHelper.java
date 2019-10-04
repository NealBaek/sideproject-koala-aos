package com.ksdigtalnomad.koala.util;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

public class FBEventLogHelper {
    public static void sendEvent(){
        Bundle bundle = new Bundle();
        bundle.putString("test_param1", "11111");
        bundle.putString("test_param2", "2222");
        bundle.putString("test_param3", "333333");
        FirebaseAnalytics
                .getInstance(BaseApplication.getInstance().getApplicationContext())
                .logEvent("test_event", bundle);
    }
}
