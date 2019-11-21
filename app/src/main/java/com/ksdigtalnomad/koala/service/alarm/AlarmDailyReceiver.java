package com.ksdigtalnomad.koala.service.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.ksdigtalnomad.koala.service.PackageReceiver;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.util.PreferenceHelper;

import java.util.Calendar;

/**
 * Created by ooddy on 14/11/2019.
 */

public class AlarmDailyReceiver extends BroadcastReceiver {

    public static final String CUSTOM_INTENT = "com.test.intent.action.ALARM";


    @Override
    public void onReceive(Context context, Intent intent) {

        /* enqueue the job */
        AlarmDailyJobIntentService.enqueueWork(context, intent);
    }
    public static void cancelAlarm() {
        Context context = BaseApplication.getInstance();

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        /* cancel any pending alarm */
        Intent alarmIntent = new Intent(context, AlarmDailyReceiver.class);
        alarmIntent.setAction(CUSTOM_INTENT);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarm.cancel(pendingIntent);
    }
    public static void setAlarm() {

        Context context = BaseApplication.getInstance();

        cancelAlarm();

        int alarmDailyHour = PreferenceHelper.getAlarmDailyHour();
        int alarmDailyMinute = PreferenceHelper.getAlarmDailyMinute();

        Calendar when = Calendar.getInstance();


        // 시간 비교.. 저장한 시간이랑 현재 시간이랑 비교 if 지났으면 -> DATE + 1 else 아직 안지났으면 -> DATE 그대로
        boolean isAlreadyOver =  when.get(Calendar.HOUR_OF_DAY) > alarmDailyHour || (when.get(Calendar.HOUR_OF_DAY) == alarmDailyHour && when.get(Calendar.MINUTE) >= alarmDailyMinute);
        if(isAlreadyOver) when.add(Calendar.DATE, 1);

        when.set(Calendar.HOUR_OF_DAY, alarmDailyHour);
        when.set(Calendar.MINUTE, alarmDailyMinute);


        PackageManager pm = context.getPackageManager();
        ComponentName receiver = new ComponentName(context, PackageReceiver.class);

        Intent alarmIntent = new Intent(context, AlarmDailyReceiver.class);
        alarmIntent.setAction(CUSTOM_INTENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);



        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        /* fire the broadcast */
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.KITKAT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pendingIntent);
        else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pendingIntent);
        else if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pendingIntent);
        }


        // 부팅 후 실행되는 리시버 사용가능하게 설정
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }
//    public static void setAlarm() {
//
//        Context context = BaseApplication.getInstance();
//
//        cancelAlarm();
//
//        Calendar when = Calendar.getInstance();
//
//        when.add(Calendar.SECOND, 15 /* 15초 단위로 체크 */);
//
//
//        PackageManager pm = context.getPackageManager();
//        ComponentName receiver = new ComponentName(context, PackageReceiver.class);
//
//        Intent alarmIntent = new Intent(context, AlarmDailyReceiver.class);
//        alarmIntent.setAction(CUSTOM_INTENT);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//            /* fire the broadcast */
//        int SDK_INT = Build.VERSION.SDK_INT;
//        if (SDK_INT < Build.VERSION_CODES.KITKAT)
//            alarmManager.set(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pendingIntent);
//        else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M)
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pendingIntent);
//        else if (SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pendingIntent);
//        }
//
//
//        // 부팅 후 실행되는 리시버 사용가능하게 설정
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
//
//    }
}
