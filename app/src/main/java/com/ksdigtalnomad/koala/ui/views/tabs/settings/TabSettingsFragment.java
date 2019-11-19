package com.ksdigtalnomad.koala.ui.views.tabs.settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.FragmentTabSettingsBinding;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.ksdigtalnomad.koala.service.alarm.DailyAlarmReceiver;
import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.ui.customView.calendarView.utils.DateHelper;
import com.ksdigtalnomad.koala.ui.views.dialogs.CustomTimePickerDialog;
import com.ksdigtalnomad.koala.util.FBEventLogHelper;
import com.ksdigtalnomad.koala.util.PlayStoreHelper;
import com.ksdigtalnomad.koala.util.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.util.PreferenceHelper;
import com.ksdigtalnomad.koala.util.ShareHelper;
import com.ksdigtalnomad.koala.util.ToastHelper;

public class TabSettingsFragment extends BaseFragment {

    private FragmentTabSettingsBinding mBinding;

    private Context mContext;

    public static TabSettingsFragment newInstance(){
        TabSettingsFragment fragment = new TabSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext= getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_settings, container, false);
        mBinding.setFragment(this);

        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            mBinding.version.setText("ver " + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        boolean isPushEnabled = PreferenceHelper.isAlarmDailyEnabled();
        mBinding.alarmDailySwitch.setChecked(isPushEnabled);
        mBinding.alarmDailySwitch.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) -> {

            String todayStr = DateHelper.getInstance().getTodayStr("yyyy.MM.dd.");

            ToastHelper.writeBottomLongToast(getResources().getString(R.string.warning_push_agree, todayStr, (isChecked ? "동의" : "거부")));

            // 동의 설정
            PreferenceHelper.setAlarmDailyEnabled(isChecked);

            // 시간 설정 레이아웃 Disable
            setPushTimeLayoutEnabled(isChecked);

            // 로깅
            FBEventLogHelper.onAlarmDailyAgree(isChecked, todayStr);
        });
        setPushTimeLayoutEnabled(isPushEnabled);


        return mBinding.getRoot();
    }



    private void setPushTimeLayoutEnabled(boolean isChecked){
        mBinding.pushTimeLayout.setEnabled(isChecked);
        mBinding.pushTimeLayout.setAlpha(isChecked ? 1f : 0.5f);
    }


    // OnClick
    public void onKakaoOpenChatRoomClick(){
        String chatRoomUrl = FBRemoteControlHelper.getInstance().getKakaoOpenChatRoomUrl();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(chatRoomUrl)));

        FBEventLogHelper.onKakaoOpenChatRoom();
    }
    public void onPushAgreeClick(){
        mBinding.alarmDailySwitch.setChecked(!mBinding.alarmDailySwitch.isChecked());
    }
    public void onPushTimeClick(){
        CustomTimePickerDialog customTimePickerDialog = CustomTimePickerDialog.newInstance(PreferenceHelper.getAlarmDailyHour(), PreferenceHelper.getAlarmDailyMinute());
        customTimePickerDialog.setDialogListener((boolean isTimeSet, int hour, int minute)->{
            if(!isTimeSet) return;

            PreferenceHelper.setAlarmDailyHour(hour);
            PreferenceHelper.setAlarmDailyMinute(minute);

            DailyAlarmReceiver.setAlarm();

            String settingTime = PreferenceHelper.getAlarmDailySettingTimeStr();
            FBEventLogHelper.onAlarmDailyTime(settingTime);
            mBinding.bodyLayout.postDelayed(() -> ToastHelper.writeBottomLongToast(getResources().getString(R.string.warning_push_time, settingTime)), 100);
        });

        customTimePickerDialog.show(getActivity().getFragmentManager(), "CustomTimePickerDialog");

    }
    public void onOpenPlayStoreComplementClick(){
        PlayStoreHelper.openMyAppInPlayStore(getActivity());
        FBEventLogHelper.onPlayStoreComplement();
    }
    public void onShareClick(){
        ShareHelper.startShareIntent(getContext(), FBRemoteControlHelper.getInstance().getShareMessage());
        FBEventLogHelper.onAppShare();

    }
    public void onVersionClick(){  }

}
