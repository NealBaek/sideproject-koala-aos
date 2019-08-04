package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.dayDetail;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.viewModels.CalendarViewModel;
import com.ksdigtalnomad.koala.data.viewModels.ViewModelHelper;
import com.ksdigtalnomad.koala.databinding.ActivityCalendarDayDetailBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.util.KeyboardUtil;
import com.ksdigtalnomad.koala.util.ProgressHelper;

import java.util.ArrayList;

public class CalendarDayDetailActivity extends BaseActivity {

    private ActivityCalendarDayDetailBinding mBinding;
    private static final String KEY_DAY_MODEL = "KEY_DAY_MODEL";

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


    }


    public void onDrunkLevelChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        dayModel.drunkLevel = progresValue;
        mBinding.drunkLevelComment.setText(CalendarConstUtils.getDrunkLvComment(dayModel.drunkLevel));
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

        Log.d("ABC", "onSaveClick");
        dayModel.memo = mBinding.memo.getText().toString();

        Log.d("ABC", "1");

        KeyboardUtil.hide(CalendarDayDetailActivity.this);
//        ProgressHelper.showProgress(mBinding.bodyLayout);

        Log.d("ABC", "2");

        CalendarDataController.updateDayModel(dayModel);

        Log.d("ABC", "3");

//        ProgressHelper.dismissProgress(mBinding.bodyLayout);
//        finish();
//        if(updateAsyncTask == null) updateAsyncTask = new UpdateAsyncTask();
////        updateAsyncTask.cancel(true);
//        updateAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
