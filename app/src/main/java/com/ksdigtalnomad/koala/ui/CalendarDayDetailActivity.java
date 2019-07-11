package com.ksdigtalnomad.koala.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.customView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.day.DayModel;
import com.ksdigtalnomad.koala.ui.customView.month.MonthModel;

import java.util.ArrayList;
import java.util.Collections;

public class CalendarDayDetailActivity extends BaseActivity {

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


        ((TextView)findViewById(R.id.date)).setText(dayModel.year + "." + dayModel.month + "." + dayModel.day);

        ((TextView)findViewById(R.id.friendList)).setText(getFullStr(dayModel.friendList));
        ((TextView)findViewById(R.id.foodList)).setText(getFullStr(dayModel.foodList));
        ((TextView)findViewById(R.id.liquarList)).setText(getFullStr(dayModel.liquorList));
        ((EditText)findViewById(R.id.memo)).setText(dayModel.memo);



    }

    private String getFullStr(ArrayList<String> strList){
        String toReturn = "";
        int cnt = strList.size();

        for(int i = 0; i < cnt; ++ i){  toReturn += (i == 0 ? "" : ", ") + strList.get(i);  }

        return toReturn;
    }

    public void onSaveClick(View v){
        dayModel.memo = ((EditText)findViewById(R.id.memo)).getText().toString();
        Log.d("ABC", "memo: " + dayModel.memo);
        CalendarDataController.updateCalendarModel(dayModel);

        finish();
    }
}
