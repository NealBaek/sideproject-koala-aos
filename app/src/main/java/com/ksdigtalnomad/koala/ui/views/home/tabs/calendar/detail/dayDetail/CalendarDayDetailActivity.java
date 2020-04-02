package com.ksdigtalnomad.koala.ui.views.home.tabs.calendar.detail.dayDetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
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
import com.ksdigtalnomad.koala.helpers.data.ParseHelper;
import com.ksdigtalnomad.koala.helpers.ui.ProgressHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarConstUtils;
import com.ksdigtalnomad.koala.ui.customView.calendarView.CalendarDataController;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.dialogs.ExpenseDialog;
import com.ksdigtalnomad.koala.ui.views.home.tabs.calendar.detail.detailListEdit.CalendarDetailListEditActivity;
import com.ksdigtalnomad.koala.helpers.data.FBEventLogHelper;
import com.ksdigtalnomad.koala.helpers.ui.KeyboardHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;

import java.util.concurrent.Executors;

public class CalendarDayDetailActivity extends BaseActivity {

    private ActivityCalendarDayDetailBinding mBinding;
    public static final String KEY_DAY_MODEL = "KEY_DAY_MODEL";
    private static final String KEY_NOTI_ALARM_DAILY = "NOTI_ALARM_DAILY";

    private int MEMO_LEN_LIMIT = 0;

    private DayModel dayModel;

    private final Drawable THUMB_DARKGRAY = BaseApplication.getInstance().getResources().getDrawable(R.drawable.shape_seekbar_darkgray);
    private final Drawable THUMB_RED = BaseApplication.getInstance().getResources().getDrawable(R.drawable.shape_seekbar_red);
    private final Drawable THUMB_LIGHT_RED = BaseApplication.getInstance().getResources().getDrawable(R.drawable.shape_seekbar_lightred);
    private final Drawable PROGRESS_DARKGRAY = BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_seekbar_drakgray);
    private final Drawable PROGRESS_RED = BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_seekbar_red);
    private final Drawable PROGRESS_LIGHTRED = BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_seekbar_lightred);

    private static Intent intent(Context context) {  return new Intent(context, CalendarDayDetailActivity.class);  }
    public static Intent intent(Context context, DayModel dayModel) {
        Intent intent = intent(context);
        intent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
        return intent;
    }
    public static Intent intentFromNotiAlarmDaily(Context context, DayModel dayModel) {
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
        setSeekbar(dayModel.drunkLevel);

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
        setSeekbar(progresValue);
    }
    private void setSeekbar(int progresValue){
        dayModel.drunkLevel = progresValue;
        mBinding.drunkLevelComment.setText(CalendarConstUtils.getDrunkLvComment(dayModel.drunkLevel));
        runOnUiThread(()->{
            mBinding.drunkLevelComment.setTextColor(CalendarConstUtils.getDrunkLvColorRed(dayModel.drunkLevel));
            switch (dayModel.drunkLevel) {
                case CalendarConstUtils.DRUNK_LV_1:
                case CalendarConstUtils.DRUNK_LV_2:
                    mBinding.drunkLevel.setThumb(THUMB_LIGHT_RED);
                    mBinding.drunkLevel.setProgressDrawable(PROGRESS_LIGHTRED);
                    break;
                case CalendarConstUtils.DRUNK_LV_3:
                case CalendarConstUtils.DRUNK_LV_MAX:
                    mBinding.drunkLevel.setThumb(THUMB_RED);
                    mBinding.drunkLevel.setProgressDrawable(PROGRESS_LIGHTRED);
                    break;
                default:
                    mBinding.drunkLevel.setThumb(THUMB_DARKGRAY);
                    mBinding.drunkLevel.setProgressDrawable(PROGRESS_DARKGRAY);
                    break;
            }
        });
    }

    public void moveToDetailListEditActivity(String viewType){
        startActivityForResult(CalendarDetailListEditActivity.intent(this, viewType, dayModel), 0);
    }

    public void moveToExpenseDialog(){
        ExpenseDialog dialog = ExpenseDialog.newInstance(dayModel.expense);
        dialog.setDialogListener((newValue)->{
            Runnable runnable = ()->{
                dayModel.expense = newValue;
                CalendarDayDetailActivity.this.runOnUiThread(()->mBinding.expense.setText(ParseHelper.parseMoney(dayModel.expense)));
            };
            runnable.run();
        });
        dialog.show(getFragmentManager(), "Add Dialog");
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

        ProgressHelper.showProgress(mBinding.bodyLayout, false);
        Executors.newSingleThreadExecutor().execute(()->{
            if(CalendarDataController.isNoDataYet()) CalendarDataController.setNoDataYet(false);
            CalendarDataController.updateDayModel(dayModel);
            FBEventLogHelper.onInputDoneClick(dayModel);

            if(getIntent().getBooleanExtra(KEY_NOTI_ALARM_DAILY, false)){
                Executors.newScheduledThreadPool(1).execute(() -> FBEventLogHelper.onAlarmDailyInputDone(PreferenceHelper.getAlarmDailySettingTimeStr()));
            }

            runOnUiThread(()->{
                ProgressHelper.dismissProgress(mBinding.bodyLayout);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_DAY_MODEL, new Gson().toJson(dayModel));
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        });
    }

    public void onBackClick(View v){
        finish();
    }

}
