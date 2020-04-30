package com.ksdigtalnomad.koala.ui.views.home.tabs.settings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivitySettingCalendarDesignBinding;
import com.ksdigtalnomad.koala.helpers.data.FBEventLogHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceGenericHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;

import static com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils.Design;
import static com.ksdigtalnomad.koala.helpers.data.PreferenceGenericHelper.Key;


public class SettingCalendarDesignActivity extends BaseActivity {

    private ActivitySettingCalendarDesignBinding mBinding;
    private Design calendarDesign;

    public static Intent intent(Context context) {  return new Intent(context, SettingCalendarDesignActivity.class);  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting_calendar_design);
        mBinding.setLifecycleOwner(this);
        mBinding.setActivity(this);

        setCalendarDesign(CalendarConstUtils.Design.findById((int) PreferenceGenericHelper.getInstance().getValue(Key.design_calendar, Design.defaults.id)));

    }
    public void setCalendarDesign(Design calendarDesign){
        this.calendarDesign = calendarDesign;

        mBinding.rbDefaults.setChecked(calendarDesign.isEqual(Design.defaults));
        mBinding.rbStamp1.setChecked(calendarDesign.isEqual(Design.stamp_1));
    }
    public void onBackClick(View v){
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        PreferenceGenericHelper.getInstance().setValue(Key.design_calendar, calendarDesign.id);
        FBEventLogHelper.onCalendarDesign(calendarDesign);
        setResult(RESULT_OK, new Intent());
        finish();
    }
}
