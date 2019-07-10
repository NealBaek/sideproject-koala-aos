package com.ksdigtalnomad.koala;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.customView.day.DayModel;

public class CalendarDayDetailActivity extends AppCompatActivity {

    DayModel dayModel;
    private static final String KEY_DAY_MODEL = "KEY_DAY_MODEL";

    private static Intent intent(Context context) {  return new Intent(context, CalendarDayDetailActivity.class);  }
    public static Intent intent(Context context, DayModel dayModel) {
        Intent intent = intent(context);
        intent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day_detail);

        this.dayModel = new Gson().fromJson(getIntent().getStringExtra(KEY_DAY_MODEL), DayModel.class);

        Log.d("ABC", dayModel.toString());
    }
}
