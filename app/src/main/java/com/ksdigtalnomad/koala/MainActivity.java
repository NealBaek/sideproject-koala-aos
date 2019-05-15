package com.ksdigtalnomad.koala;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ksdigtalnomad.koala.customView.calendar.CalendarView;
import com.ksdigtalnomad.koala.customView.calendar.calendarBody.CalendarModel;
import com.ksdigtalnomad.koala.customView.calendar.day.DayModel;
import com.ksdigtalnomad.koala.customView.calendar.month.MonthModel;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        showCalendar();
    }

    private void showCalendar(){

        CalendarView calendarView = new CalendarView(this, createCalendarModel(), new CalendarView.EventInterface() {
            @Override
            public void onDayViewTouch(DayModel dayModel) {
//                startActivity(new Intent(MainActivity.this.getBaseContext(), CalendarDayDetailActivity.class));

                Toast.makeText(MainActivity.this, dayModel.year + "." + dayModel.month + "." + dayModel.day, Toast.LENGTH_SHORT).show();
            }
        });


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        LinearLayout bodyLayout = findViewById(R.id.bodyLayout);

        bodyLayout.addView(calendarView, 0, params);
        bodyLayout.setBackgroundColor(Color.LTGRAY);
    }



    private CalendarModel createCalendarModel(){

        CalendarModel calendarModel = new CalendarModel();

        ArrayList<MonthModel> monthList = new ArrayList<>();

        for(int i = 0; i < 20; ++i){
            MonthModel monthModel = new MonthModel();
            monthModel.year = 2019;
            monthModel.month = i + 1;
            monthModel.numberOfDaysInTheMonth = 30;
            monthModel.dayList = new ArrayList<>();


            for(int j = 0; j < 42; ++j){

                DayModel dayModel = new DayModel();

                dayModel.year = monthModel.year;
                dayModel.month = monthModel.month;
                dayModel.day = j;
                dayModel.daySeq = j;
                dayModel.drunkLevel = ((int)( Math.random() * 10)) % 5;
                dayModel.friendList = new ArrayList<String>();
                dayModel.foodList = new ArrayList<String>();
                dayModel.liquorList = new ArrayList<String>();


                if(j%2 == 0) dayModel.friendList.add("아이유 외 1");

                if(j%3 == 0) dayModel.foodList.add("곱창 외 1");

                dayModel.liquorList.add("소주 외 1");

                if(j%4 == 0) dayModel.memo = "송별회";

                monthModel.dayList.add(dayModel);
            }


            monthList.add(monthModel);
        }

        calendarModel.monthList = monthList;

        return calendarModel;
    }
}
