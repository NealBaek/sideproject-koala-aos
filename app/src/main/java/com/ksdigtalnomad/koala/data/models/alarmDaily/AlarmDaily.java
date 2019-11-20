package com.ksdigtalnomad.koala.data.models.alarmDaily;

import com.ksdigtalnomad.koala.data.models.calendar.BaseData;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class AlarmDaily extends BaseData implements Cloneable{
    ArrayList<AlarmDailyBody> bodyList = new ArrayList();
    int defaultAlarmHour = 0;
    int defaultAlarmMinute = 0;

    public AlarmDaily clone(){
        //내 객체 생성
        Object toReturn;

        try
        {
            toReturn = super.clone();
            return ((AlarmDaily)toReturn);
        }
        catch (CloneNotSupportedException e)
        {
            return this;
        }
    }
}