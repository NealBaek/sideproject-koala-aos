package com.ksdigtalnomad.koala.data.net.services;

import com.ksdigtalnomad.koala.data.models.vo.CalendarUpdateDayVo;
import com.ksdigtalnomad.koala.data.models.vo.CalendarUpdateTotalVo;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CalendarService {
    @POST("v1/calendar/total")
    Observable<ArrayList<DayModel>> updateCalendarTotal(@Body CalendarUpdateTotalVo vo);

    @POST("v1/calendar/day")
    Observable<DayModel> updateCalendarDay(@Body CalendarUpdateDayVo vo);
}
