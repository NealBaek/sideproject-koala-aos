package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.dayDetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityCalendarDayDetailBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit.CalendarDetailListEditActivity;
import com.ksdigtalnomad.koala.util.KeyboardHelper;

import java.util.ArrayList;

public class CalendarDayDetailActivity extends BaseActivity {

    private ActivityCalendarDayDetailBinding mBinding;
    public static final String KEY_DAY_MODEL = "KEY_DAY_MODEL";

    private DayModel dayModel;

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

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_day_detail);
        mBinding.setLifecycleOwner(this);
        mBinding.setActivity(this);
        mBinding.setDayModel(dayModel);

        mBinding.adView.loadAd(new AdRequest.Builder().build());

        mBinding.memo.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                KeyboardHelper.hide(CalendarDayDetailActivity.this);
                mBinding.memo.clearFocus();
                return true;
            }
            return false;
        });

        mBinding.headerText.setText(
                dayModel.year + "." +
                (dayModel.month >= 10 ? String.valueOf(dayModel.month) : ("0" + dayModel.month)) + "." +
                (dayModel.day >= 10 ? String.valueOf(dayModel.day) : ("0" + dayModel.day)) + ".");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.memo.clearFocus();
    }

    @Override
    public void onBackPressed() {
        if(mBinding.memo.hasFocus()){
            mBinding.memo.clearFocus();
            KeyboardHelper.hide(CalendarDayDetailActivity.this);
        }else{
            super.onBackPressed();
        }
    }

    public void onDrunkLevelChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        dayModel.drunkLevel = progresValue;
        mBinding.drunkLevelComment.setText(CalendarConstUtils.getDrunkLvComment(dayModel.drunkLevel));
    }

    public void moveToDetailListEditActivity(){
        startActivity(CalendarDetailListEditActivity.intent(this));
    }

    public String getFullStr(ArrayList<String> strList){
        String toReturn = "";
        int cnt = strList.size();

        for(int i = 0; i < cnt; ++ i){  toReturn += (i == 0 ? "" : ", ") + strList.get(i);  }

        return toReturn;
    }

    public void onSaveClick(View v){

        dayModel.memo = mBinding.memo.getText().toString();

//        runOnUiThread(()->KeyboardHelper.hide(CalendarDayDetailActivity.this));

        Runnable task = () -> CalendarDataController.updateDayModel(dayModel);
        task.run();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    public void onBackClick(View v){
        finish();
    }

}
