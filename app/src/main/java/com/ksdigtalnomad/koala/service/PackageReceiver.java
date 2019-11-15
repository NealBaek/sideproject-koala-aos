package com.ksdigtalnomad.koala.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ksdigtalnomad.koala.service.alarm.DailyAlarmReceiver;

/**
 * Created by ooddy on 14/11/2019.
 */

public class PackageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
            DailyAlarmReceiver.setAlarm();
        }
    }
}
