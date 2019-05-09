package com.ksdigtalnomad.koala;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.customView.calendar.CalendarView;
import com.ksdigtalnomad.koala.customView.calendar.DayView;
import com.ksdigtalnomad.koala.customView.calendar.MonthModel;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DayView dayView = new DayView(this);

        CalendarView calendarView = new CalendarView(this);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        ConstraintLayout bodyLayout = findViewById(R.id.bodyLayout);

//        dayView.setBackgroundColor(Color.WHITE);

        bodyLayout.addView(calendarView, 0, params);
        bodyLayout.setBackgroundColor(Color.LTGRAY);


        getSupportActionBar().hide();

    }

}
