package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.dayDetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.ksdigtalnomad.koala.util.FBEventLogHelper;
import com.ksdigtalnomad.koala.util.KeyboardHelper;
import com.ksdigtalnomad.koala.util.PreferenceHelper;
import com.ksdigtalnomad.koala.util.ToastHelper;

public class CalendarDayDetailActivity extends BaseActivity {

    private ActivityCalendarDayDetailBinding mBinding;
    public static final String KEY_DAY_MODEL = "KEY_DAY_MODEL";
    private static final String KEY_NOTI_ALARM_DAILY = "NOTI_ALARM_DAILY";

    private int MEMO_LEN_LIMIT = 0;

    private DayModel dayModel;

    private static Intent intent(Context context) {  return new Intent(context, CalendarDayDetailActivity.class);  }
    public static Intent intent(Context context, DayModel dayModel) {
        Intent intent = intent(context);
        intent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        return intent;
    }public static Intent intentFromNotiAlarmDaily(Context context, DayModel dayModel) {
        Intent intent = intent(context);
        intent.putExtra(KEY_NOTI_ALARM_DAILY, true);
        intent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.dayModel = new Gson().fromJson(getIntent().getStringExtra(KEY_DAY_MODEL), DayModel.class);
        this.MEMO_LEN_LIMIT = getResources().getInteger(R.integer.limit_memo_length);


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

        mBinding.memo.postDelayed(()->
            mBinding.memo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length() >= MEMO_LEN_LIMIT){
                        ToastHelper.writeBottomLongToast("메모는 최대 "+MEMO_LEN_LIMIT+"자까지 작성 가능합니다.");
                    }
                }
            })
        , 1000);

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

    public void moveToDetailListEditActivity(String viewType){
        startActivityForResult(CalendarDetailListEditActivity.intent(this, viewType, dayModel), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            dayModel = new Gson().fromJson(data.getStringExtra(KEY_DAY_MODEL), DayModel.class);
            setDayModel(dayModel);
        }
    }
    private void setDayModel(DayModel dayModel){
        mBinding.friendList.setText(CalendarConstUtils.getLongStrFromFriendList(dayModel.friendList));
        mBinding.foodList.setText(CalendarConstUtils.getLongStrFromFoodList(dayModel.foodList));
        mBinding.drinkList.setText(CalendarConstUtils.getLongStrFromDrinkList(dayModel.drinkList));
    }

    public void onSaveClick(View v){

        dayModel.memo = mBinding.memo.getText().toString();
        dayModel.isSaved = true;

        Runnable task = () -> CalendarDataController.updateDayModel(dayModel);
        task.run();

        Runnable task1 = () -> FBEventLogHelper.onInputDoneClick(dayModel);
        task1.run();

        if(getIntent().getBooleanExtra(KEY_NOTI_ALARM_DAILY, false)){
            Runnable task2 = () -> FBEventLogHelper.onAlarmDailyInputDone(PreferenceHelper.getAlarmDailySettingTimeStr());
            task2.run();
        }


        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    public void onBackClick(View v){
        finish();
    }

}
