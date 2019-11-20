package com.ksdigtalnomad.koala.data.models.alarmDaily;

/**
 * Created by ooddy on 15/11/2019.
 */

import com.ksdigtalnomad.koala.data.models.calendar.BaseData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @Setter @Getter @ToString
public class AlarmDailyBody extends BaseData implements Cloneable{
    String langCode = "";
    String title = "";
    String content = "";
}
