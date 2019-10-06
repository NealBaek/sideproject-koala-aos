package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityCalendarDetailListEditBinding;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.customView.calendarView.day.DayModel;
import com.ksdigtalnomad.koala.ui.views.dialogs.AddDialog;
import com.ksdigtalnomad.koala.ui.views.dialogs.UpdateDialog;
import com.ksdigtalnomad.koala.util.KeyboardHelper;
import com.ksdigtalnomad.koala.util.ToastHelper;

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

        mBinding.searchEt.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardHelper.hide(CalendarDetailListEditActivity.this);
                ToastHelper.writeBottomLongToast("검색!");
                return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if(mBinding.searchEt.hasFocus()){
            mBinding.searchEt.clearFocus();
            KeyboardHelper.hide(CalendarDetailListEditActivity.this);
        }else{
            super.onBackPressed();
        }
    }

    public void onBackClick(View v){ finish(); }
    public void onAddClick(View v){
        String toAdd = "";
        if(mBinding.searchEt != null && mBinding.searchEt.getText() != null){
            toAdd = mBinding.searchEt.getText().toString();
        }

        AddDialog dialog = AddDialog.newInstance(toAdd);
        dialog.setDialogListener(()->{  });
        dialog.show(getFragmentManager(), "Add Dialog");
    }
}
