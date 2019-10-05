package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityCalendarDetailListEditBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.dayDetail.CalendarDayDetailActivity;

public class CalendarDetailListEditActivity extends BaseActivity {

    public static final String KEY_DAY_MODEL = "KEY_DAY_MODEL";

    private ActivityCalendarDetailListEditBinding mBinding;

    public static Intent intent(Context context) {  return new Intent(context, CalendarDetailListEditActivity.class);  }
    public static Intent intent(Context context, DayModel dayModel) {
        Intent intent = intent(context);
        intent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_detail_list_edit);
        mBinding.setLifecycleOwner(this);
//        mBinding.setActivity(this);

        mBinding.adView.loadAd(new AdRequest.Builder().build());
    }

    public void onBackClick(View v){
        finish();
    }
}
