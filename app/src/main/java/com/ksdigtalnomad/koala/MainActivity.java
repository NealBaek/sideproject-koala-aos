package com.ksdigtalnomad.koala;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ksdigtalnomad.koala.customView.calendar.CalendarView;
import com.ksdigtalnomad.koala.customView.calendar.DayView;
import com.ksdigtalnomad.koala.customView.calendar.MonthModel;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        shoudCalendar();
    }

    private void shoudCalendar(){
        CalendarView calendarView = new CalendarView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        LinearLayout bodyLayout = findViewById(R.id.bodyLayout);

        bodyLayout.addView(calendarView, 0, params);
        bodyLayout.setBackgroundColor(Color.LTGRAY);
    }
}
