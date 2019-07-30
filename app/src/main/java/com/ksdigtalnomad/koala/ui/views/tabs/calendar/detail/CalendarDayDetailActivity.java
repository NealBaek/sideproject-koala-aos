package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityCalendarDayDetailBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.util.KeyboardUtil;

import java.util.ArrayList;

public class CalendarDayDetailActivity extends BaseActivity {

    ActivityCalendarDayDetailBinding binding;

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

        this.dayModel = new Gson().fromJson(getIntent().getStringExtra(KEY_DAY_MODEL), DayModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_day_detail);
        binding.setActivity(this);
        binding.setDayModel(dayModel);


    }


    public void onDrunkLevelChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        dayModel.drunkLevel = progresValue;
        binding.drunkLevelComment.setText(CalendarConstUtils.getDrunkLvComment(dayModel.drunkLevel));
    }

    public void moveToDetailListEditActivity(){
//        CalendarDetailListEditActivity
    }

    public String getFullStr(ArrayList<String> strList){
        String toReturn = "";
        int cnt = strList.size();

        for(int i = 0; i < cnt; ++ i){  toReturn += (i == 0 ? "" : ", ") + strList.get(i);  }

        return toReturn;
    }

    public void onSaveClick(View v){
        KeyboardUtil.hide(this);

        dayModel.memo = binding.memo.getText().toString();

        CalendarDataController.updateDayModel(dayModel);

        finish();
    }
}
