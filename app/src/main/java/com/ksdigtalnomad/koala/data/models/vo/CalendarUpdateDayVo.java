package com.ksdigtalnomad.koala.data.models.vo;

import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString
public class CalendarUpdateDayVo {
    private DayModel dayModel;
}
