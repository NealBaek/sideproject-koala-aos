package com.ksdigtalnomad.koala.data;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.data.models.alarmDaily.AlarmDaily;
import com.ksdigtalnomad.koala.data.models.alarmDaily.AlarmDailyBody;
import com.ksdigtalnomad.koala.service.alarm.AlarmDailyReceiver;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.helpers.data.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.helpers.data.LanguageHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;

import java.util.ArrayList;

/**
 * Created by ooddy on 21/11/2019.
 */

public class AlarmDailyController {

    public static AlarmDailyBody getAlarmDailyData(){
        // 알림 내용 받아오기
        AlarmDaily alarmDaily = new Gson().fromJson(FBRemoteControlHelper.getInstance().getAlarmDaily(), AlarmDaily.class);
        AlarmDailyBody alarmDailyBody = AlarmDailyBody.builder().build();
        ArrayList<AlarmDailyBody> bodyList =  alarmDaily.getBodyList();
        if(bodyList == null || bodyList.size() <= 0) return alarmDailyBody;
        for(AlarmDailyBody item : bodyList){
            if(LanguageHelper.isSameLanguage(item.getLangCode())){
                alarmDailyBody = item;
                break;
            }
        }

        return alarmDailyBody;
    }

    public static void checkAlarmDailyState(CheckInterface checkInterface){
        // 1. 푸시 미허용 알람 종료
        if(!PreferenceHelper.isAlarmDailyEnabled()){
            checkInterface.stop();
            return;
        }

        // 2. 어제 입력을 했으면 알람 리셋
        if(CalendarDataController.getYesterdayModel().isSaved){
            checkInterface.reset();
            return;
        }

        // 3. 알림 보내기 & 알림 재설정
        checkInterface.sendAndReset();
    }

    public static void setAndStartAlarm(){
        AlarmDailyReceiver.setAlarm();
    }


    public interface CheckInterface{
        void stop();
        void reset();
        void sendAndReset();
    }
}
