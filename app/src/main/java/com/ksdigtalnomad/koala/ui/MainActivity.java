package com.ksdigtalnomad.koala.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.customView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendar.CalendarView;
import com.ksdigtalnomad.koala.ui.customView.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.ui.customView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.month.MonthModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends BaseActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showCalendar();


    }

    @Override
    protected void onResume() {
        super.onResume();

        calendarView.notifyDataChanged(CalendarDataController.getInstance().getCalendarModel());
    }

    private void showCalendar(){

        calendarView = new CalendarView(this, CalendarDataController.getInstance().getCalendarModel(), new CalendarView.EventInterface() {
            @Override
            public void onDayViewTouch(DayModel dayModel) {
                Toast.makeText(MainActivity.this, dayModel.year + "." + dayModel.month + "." + dayModel.day, Toast.LENGTH_SHORT).show();

                MainActivity.this.startActivity(CalendarDayDetailActivity.intent(MainActivity.this, dayModel));
            }
        });


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        LinearLayout bodyLayout = findViewById(R.id.bodyLayout);

        bodyLayout.addView(calendarView, 0, params);
        bodyLayout.setBackgroundColor(Color.LTGRAY);
    }




}
