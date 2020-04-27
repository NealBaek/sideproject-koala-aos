package com.ksdigtalnomad.koala.ui.views.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.User;
import com.ksdigtalnomad.koala.databinding.ActivityJoinBinding;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.helpers.ui.KeyboardHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

import java.util.Calendar;

public class JoinActivity extends BaseActivity {

    private static String KEY_USER = "KEY_USER";
    private User user;
    private ActivityJoinBinding mBinding;


    private Drawable bgRectOn = BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_btn_rect_on);
    private Drawable bgRectOff = BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_btn_rect_off);
    private Drawable bgRoundOn = BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_btn_round_on);
    private Drawable bgRoundOff = BaseApplication.getInstance().getResources().getDrawable(R.drawable.bg_btn_round_off);
    private int textOn = BaseApplication.getInstance().getResources().getColor(R.color.colorPureWhite);
    private int textOff = BaseApplication.getInstance().getResources().getColor(R.color.colorGray);

    public enum Gender{
        male, female  /* 0 = male, 1 = female */
    }
    public enum TermsType{
        service, privacy
    }

    private Gender gender;

    private static Intent intent(Context context) {  return new Intent(context, JoinActivity.class);  }
    public static Intent intent(Context context, User user) {
        Intent intent = intent(context);
        intent.putExtra(KEY_USER, new Gson().toJson(user));
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.user = new Gson().fromJson(getIntent().getStringExtra(KEY_USER), User.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_join);
        mBinding.setLifecycleOwner(this);
        mBinding.setActivity(this);
        mBinding.setUser(user);

        mBinding.tvBirth.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                KeyboardHelper.hide(JoinActivity.this);
                v.clearFocus();
            }
            return false;
        });

        mBinding.tvBirth.setOnFocusChangeListener((View v, boolean hasFocus)->{
            if (!hasFocus){
                KeyboardHelper.hide(this);
                isEnableJoin(isValidBirth(mBinding.tvBirth.getText().toString()), gender);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        mBinding.adView.loadAd(new AdRequest.Builder().build());
    }

    private boolean isEnableJoin(boolean isValidBirth, Gender gender){
        boolean isEnabled = isValidBirth && (gender != null);
        mBinding.tvJoin.setBackground(isEnabled ? bgRoundOn : bgRoundOff);
        mBinding.tvJoin.setTextColor(isEnabled ? textOn : textOff);
        return isEnabled;
    }

    private boolean isValidBirth(String yearStr){
        if(yearStr.isEmpty()) return false;

        try{
            int year = Integer.parseInt(yearStr);
            int thisYear = Calendar.getInstance().get(Calendar.YEAR);

            if(year < (thisYear - 100) || year > Calendar.getInstance().get(Calendar.YEAR)){
                ToastHelper.writeBottomShortToast(getResources().getString(R.string.warning_invalid_birthday_year));
                setOnOff(mBinding.tvBirth, false);
                return false;
            }
        }catch (Exception e){
            ToastHelper.writeBottomShortToast(getResources().getString(R.string.warning_invalid_birthday_year));
            setOnOff(mBinding.tvBirth, false);
            return false;
        }
        setOnOff(mBinding.tvBirth, true);
        return true;
    }

    private void setOnOff(TextView tv, boolean isOn){
        tv.setBackground(isOn ? bgRectOn : bgRectOff);
        tv.setTextColor(isOn ? textOn : textOff);
    }


    /* onClick */
    public void onBackClick(){
        KeyboardHelper.hide(this);

        super.onBackPressed();
    }
    public void onGenderClick(Gender gender){
        ToastHelper.writeBottomShortToast("" + gender);
        this.gender = gender;

        switch (gender){
            case male:
                setOnOff(mBinding.tvMale, true);
                setOnOff(mBinding.tvFemale, false);
                break;
            case female:
                setOnOff(mBinding.tvMale, false);
                setOnOff(mBinding.tvFemale, true);
                break;
        }

        isEnableJoin(isValidBirth(mBinding.tvBirth.getText().toString()), gender);
    }
    public void onTermsClick(TermsType type){
        startActivity(TermsDetailActivity.intent(this, type));
    }
    public void onJoinClick(){
        boolean isEnabled = isEnableJoin(isValidBirth(mBinding.tvBirth.getText().toString()), gender);

        if(!isEnabled){
            ToastHelper.writeBottomShortToast(getResources().getString(R.string.warning_require_data));
            return;
        }

        user.setGender(gender == Gender.male ? "0" : "1");
        user.setBirthday(mBinding.tvBirth.getText().toString());

        Log.d("ABC", "gender: " + gender);
        Log.d("ABC", "gender isMale?: " + (gender == Gender.male));
        Log.d("ABC", "user: " + user.toString());

        // @TODO: do Join
        PreferenceHelper.setUser(user);

        /** 앱 재시작 */
        Intent i = getPackageManager().getLaunchIntentForPackage(getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
