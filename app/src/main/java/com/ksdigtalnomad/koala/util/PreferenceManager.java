package com.ksdigtalnomad.koala.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.data.models.User;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

public class PreferenceManager {
    private static final String PREF_FILE_NAME = "BUDDY_COIN";
    private static final String KEY_ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String KEY_USER = "USER";
    private static final String KEY_PUSH_TOKEN = "PUSH_TOKEN";

    private static SharedPreferences.Editor getEditPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
        return pref.edit();
    }
    private static SharedPreferences getReadPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getSharedPreferences(PREF_FILE_NAME, Activity.MODE_PRIVATE);
    }

    public static void clear() {
        getEditPreference().clear().commit();
    }






    public static void setLoginInfo(User user) {
        setUser(user);
        setAccessToken(user.getAccessToken());
    }

    public static boolean isLogin() {
        String userString = getReadPreference().getString(KEY_USER, null);
        return userString != null && !userString.equals("null");
    }

    private static void setAccessToken(String accessToken) {
        getEditPreference().putString(KEY_ACCESS_TOKEN, accessToken).apply();
    }
    public static String getAccessToken() {
        return getReadPreference().getString(KEY_ACCESS_TOKEN, null);
    }
    public static void setUser(User user) {
        getEditPreference().putString(KEY_USER, new Gson().toJson(user)).apply();
    }
    public static User getUser() {
        User user = new Gson().fromJson(getReadPreference().getString(KEY_USER, null), User.class);
        if (user != null) user.setPushToken(getPushToken());
        return user;
    }

    public static void setPushToken(String pushToken) {
        getEditPreference().putString(KEY_PUSH_TOKEN, pushToken).apply();
    }
    public static String getPushToken() {
        String pushToken = getReadPreference().getString(KEY_PUSH_TOKEN, null);
        if (pushToken == null) {
            // todo: getToken() is deprecated
//            pushToken = FirebaseInstanceId.getInstance().getToken();
            setPushToken(pushToken);
        }
        return pushToken;
    }



}
