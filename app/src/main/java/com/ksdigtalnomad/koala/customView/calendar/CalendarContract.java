package com.ksdigtalnomad.koala.customView.calendar;

import com.ksdigtalnomad.koala.customView.calendar.month.MonthModel;

/**
 * Created by ooddy on 14/05/2019.
 */

public interface CalendarContract {

    interface CalendarView{
        void setUpThisMonth(int monthIdx);
    }

    interface CalendarPresenter{
        void setUpThisMonth();
    }
}
