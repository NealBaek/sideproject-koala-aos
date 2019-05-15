package com.ksdigtalnomad.koala.customView.calendar;

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
