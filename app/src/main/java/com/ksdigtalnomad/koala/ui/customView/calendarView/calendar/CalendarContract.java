package com.ksdigtalnomad.koala.ui.customView.calendarView.calendar;

/**
 * Created by ooddy on 14/05/2019.
 */

public interface CalendarContract {

    interface CalendarView{
        void setUpThisMonth(int monthIdx);
    }

    interface CalendarPresenter{
        void setUpThisMonth();
        int getThisMonthIdx();
    }
}
