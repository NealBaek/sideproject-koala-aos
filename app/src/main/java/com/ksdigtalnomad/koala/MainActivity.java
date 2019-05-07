package com.ksdigtalnomad.koala;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ksdigtalnomad.koala.customView.calendar.DayView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DayView dayView = new DayView(this);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(240, 480);

        ConstraintLayout bodyLayout = (ConstraintLayout)findViewById(R.id.bodyLayout);

        dayView.setBackgroundColor(Color.BLACK);

        bodyLayout.addView(dayView, 0, params);


    }
}
