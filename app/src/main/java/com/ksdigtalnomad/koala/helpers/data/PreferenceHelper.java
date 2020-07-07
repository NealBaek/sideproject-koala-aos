package com.ksdigtalnomad.koala.helpers.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.BuildConfig;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.alarmDaily.AlarmDaily;
import com.ksdigtalnomad.koala.data.models.User;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

public class PreferenceHelper {
    private static final String KEY_ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String KEY_FIRST_OPEN = "FIRST_OPEN";
    private static final String KEY_OPEN_COUNT = "OPEN_COUNT";
    private static final String KEY_USER = "USER";
    private static final String KEY_PUSH_TOKEN = "PUSH_TOKEN";
    private static final String KEY_LANGUAGE_CODE = "LANGUAGE_CODE";
    private static final String KEY_ALARM_DAILY_HOUR = "ALARM_DAILY_HOUR";
    private static final String KEY_ALARM_DAILY_MINUTE = "ALARM_DAILY_MINUTE";
    private static final String KEY_ALARM_DAILY_ENABLE = "ALARM_DAILY_ENABLE";
    private static final String KEY_ADID = "ADID";



    private static final String KEY_INTERVIEW_WHYTHISAPP_FIRST = "INTERVIEW_WHYTHISAPP_FIRST";


//    public static void clear() {
//        getEditPreference().clear().commit();
//    }
    private static SharedPreferences.Editor getEditPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences(BuildConfig.PREF_FILE_NAME, Activity.MODE_PRIVATE);
        return pref.edit();
    }
    private static SharedPreferences getReadPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getSharedPreferences(BuildConfig.PREF_FILE_NAME, Activity.MODE_PRIVATE);
    }




    // AlarmDaily
    public static String getAlarmDailySettingTimeStr(){
        int hour = getAlarmDailyHour();
        int minute = getAlarmDailyMinute();

//        String hourStr = (hour < 10 ? ( "0" + hour ) : ( "" + hour));
        String hourStr = (hour > 12 ? ((hour - 12) < 10 ? ("0" + (hour - 12)) : ("" + (hour - 12))) : (hour < 10 ? ( "0" + hour ) : ( "" + hour)));
        String minuteStr = (minute < 10 ? ( "0" + minute ) : ( "" + minute));
        String hourPrefix = BaseApplication.getInstance().getApplicationContext().getResources().getString((hour > 12 ? R.string.prefix_time_PM : R.string.prefix_time_AM));

        return hourPrefix + " " + hourStr + ":" + minuteStr;
    }
    public static void setAlarmDailyHour(int alarmDailyHour) {
        getEditPreference().putInt(KEY_ALARM_DAILY_HOUR, alarmDailyHour).apply();
    }
    public static int getAlarmDailyHour() {
        AlarmDaily alarmDaily = new Gson().fromJson(FBRemoteConfigHelper.getInstance().getAlarmDaily(), AlarmDaily.class);
        if(alarmDaily == null){
            return 12; // defaultHour
        }

        int defaultHour = alarmDaily.getDefaultAlarmHour();

        return getReadPreference().getInt(KEY_ALARM_DAILY_HOUR, defaultHour);
    }
    public static void setAlarmDailyMinute(int alarmDailyMinute) {
        getEditPreference().putInt(KEY_ALARM_DAILY_MINUTE, alarmDailyMinute).apply();
    }
    public static int getAlarmDailyMinute() {
        AlarmDaily alarmDaily = new Gson().fromJson(FBRemoteConfigHelper.getInstance().getAlarmDaily(), AlarmDaily.class);
        if(alarmDaily == null){
            return 0; // defaultMinute
        }

        int defaultMinute = alarmDaily.getDefaultAlarmMinute();

        return getReadPreference().getInt(KEY_ALARM_DAILY_MINUTE, defaultMinute);
    }
    public static void setAlarmDailyEnabled(boolean flag) {
        getEditPreference().putBoolean(KEY_ALARM_DAILY_ENABLE, flag).apply();
    }
    public static boolean isAlarmDailyEnabled() {
        return getReadPreference().getBoolean(KEY_ALARM_DAILY_ENABLE, false);
    }


    // Language Code
    public static void setLanguageCode(String language) {
        getEditPreference().putString(KEY_LANGUAGE_CODE, language).apply();
    }
    public static String getLanguageCode() {
        return getReadPreference().getString(KEY_LANGUAGE_CODE, null);
    }


    // First Open
    public static void setFirstOpen(boolean flag) {
        getEditPreference().putBoolean(KEY_FIRST_OPEN, flag).apply();
    }
    public static boolean isFirstOpen() {
        return getReadPreference().getBoolean(KEY_FIRST_OPEN, true);
    }


    // Open count
    public static void setOpenCount(int cnt) {
        getEditPreference().putInt(KEY_OPEN_COUNT, cnt).apply();
    }
    public static int getOpenCunt() {
        return getReadPreference().getInt(KEY_OPEN_COUNT, 0);
    }


    public static void setLoginInfo(User user) {
        setUser(user);
        setAccessToken(user.getAccessToken());
    }

    public static boolean isLogin() {
        String userString = getReadPreference().getString(KEY_USER, null);
        return userString != null && !userString.equals("null");
    }

    // AccessToken
    private static void setAccessToken(String accessToken) {
        getEditPreference().putString(KEY_ACCESS_TOKEN, accessToken).apply();
    }
    public static String getAccessToken() {
        return getReadPreference().getString(KEY_ACCESS_TOKEN, null);
    }

    // User
    public static void setUser(User user) {
        getEditPreference().putString(KEY_USER, new Gson().toJson(user)).apply();
    }
    public static User getUser() {
        User user = new Gson().fromJson(getReadPreference().getString(KEY_USER, null), User.class);
        if (user != null) user.setPushToken(getPushToken());
        return user;
    }
    public static void clearUser() {
        getEditPreference().remove(KEY_USER).apply();
    }

    // Push Token
    public static void setPushToken(String pushToken) {
        getEditPreference().putString(KEY_PUSH_TOKEN, pushToken).apply();
    }
    public static String getPushToken() {
        String pushToken = getReadPreference().getString(KEY_PUSH_TOKEN, null);
        if (pushToken == null) {
            // todo: getToken() is deprecated
//            pushToken = FirebaseInstanceId.getInstance().tok
//            setPushToken(pushToken);
        }
        return pushToken;
    }

    // AdId
    public static void setAdid(String adId){
        if(adId != null) getEditPreference().putString(KEY_ADID, adId).apply();
    }
    public static String getAdid() {
        return getReadPreference().getString(KEY_ADID, "");
    }






    // Interview Whythisapp first
    public static void setInterviewWhythisappFirst(boolean flag) {
        getEditPreference().putBoolean(KEY_INTERVIEW_WHYTHISAPP_FIRST, flag).apply();
    }
    public static boolean isInterviewWhythisappFirst() {
        return getReadPreference().getBoolean(KEY_INTERVIEW_WHYTHISAPP_FIRST, true);
    }
}
