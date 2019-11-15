package com.ksdigtalnomad.koala.service.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.data.models.AlarmDaily;
import com.ksdigtalnomad.koala.service.PackageReceiver;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;
import com.ksdigtalnomad.koala.util.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.util.PreferenceHelper;

import java.util.Calendar;

/**
 * Created by ooddy on 14/11/2019.
 */

public class DailyAlarmReceiver extends BroadcastReceiver {

    public static final String CUSTOM_INTENT = "com.test.intent.action.ALARM";


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("ABC", "onReceive");

        /* enqueue the job */
        DailyAlarmJobIntentService.enqueueWork(context, intent);
    }
    public static void cancelAlarm() {
        Context context = BaseApplication.getInstance();

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        /* cancel any pending alarm */
        Intent alarmIntent = new Intent(context, DailyAlarmReceiver.class);
        alarmIntent.setAction(CUSTOM_INTENT);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarm.cancel(pendingIntent);
    }
    public static void setAlarm() {

        Context context = BaseApplication.getInstance();

        cancelAlarm();


        Calendar when = Calendar.getInstance();
        when.set(Calendar.HOUR, PreferenceHelper.getAlarmDailyHour());
        when.set(Calendar.MINUTE, 0);
        when.set(Calendar.SECOND, 0);
        when.add(Calendar.DATE, 1);
//        when.add(Calendar.MINUTE, 1);ㄴ


        Log.d("ABC", "toWakeUp : " + DateHelper.getInstance().getDateStr("yyyy.MM.dd HH:mm:ss", when.getTime()));




        PackageManager pm = context.getPackageManager();
        ComponentName receiver = new ComponentName(context, PackageReceiver.class);

        Intent alarmIntent = new Intent(context, DailyAlarmReceiver.class);
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


        Log.d("ABC", "setAlarm alarm.set");
    }
}
