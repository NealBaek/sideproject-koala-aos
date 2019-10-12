package com.ksdigtalnomad.koala.ui.views.tabs.calendar.detail.detailListEdit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class CalendarDetailListEditActivity extends BaseActivity {

    private String viewType = "";
    private static final String KEY_HEADER_TITLE = "KEY_HEADER_TITLE";
    public static final String TYPE_FRIENDS = "TYPE_FRIENDS";
    public static final String TYPE_FOODS = "TYPE_FOODS";
    public static final String TYPE_DRINKS = "TYPE_DRINKS";

    private ActivityCalendarDetailListEditBinding mBinding;

    private ArrayList<String> dataList = new ArrayList<>();

    public static Intent intent(Context context, String type) {
        Intent intent = new Intent(context, CalendarDetailListEditActivity.class);
        intent.putExtra(KEY_HEADER_TITLE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewType = getIntent().getStringExtra(KEY_HEADER_TITLE);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_detail_list_edit);
        mBinding.setLifecycleOwner(this);
//        mBinding.setActivity(this);

        setViewType(viewType);

        mBinding.adView.loadAd(new AdRequest.Builder().build());
        mBinding.searchEt.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardHelper.hide(CalendarDetailListEditActivity.this);
                ToastHelper.writeBottomLongToast("검색!");
                return true;
            }
            return false;
        });

        mBinding.dataRv.setAdapter(new CalendarDetailListAdapter(this, dataList));

        if(dataList.size() > 0){
            mBinding.emptyDataLayout.setVisibility(View.GONE);
            mBinding.dataRv.setVisibility(View.VISIBLE);
        }
    }

    private void setViewType(String viewType){
        if(viewType.equals(TYPE_FRIENDS)){
            mBinding.headerText.setText(getResources().getString(R.string.calendar_detail_friends_title));
//            dataList =

        }else if(viewType.equals(TYPE_DRINKS)){
            mBinding.headerText.setText(getResources().getString(R.string.calendar_detail_drink_title));


        }else if(viewType.equals(TYPE_FOODS)){
            mBinding.headerText.setText(getResources().getString(R.string.calendar_detail_foods_title));


        }
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
