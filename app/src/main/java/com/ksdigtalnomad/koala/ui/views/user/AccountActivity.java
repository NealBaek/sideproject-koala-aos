package com.ksdigtalnomad.koala.ui.views.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.User;
import com.ksdigtalnomad.koala.databinding.ActivityAccountBinding;
import com.ksdigtalnomad.koala.helpers.data.FBEventLogHelper;
import com.ksdigtalnomad.koala.helpers.data.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;

public class AccountActivity extends BaseActivity {

    private ActivityAccountBinding mBinding;
    private User user;

    public static Intent intent(Context context) {  return new Intent(context, AccountActivity.class);  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account);
        mBinding.setLifecycleOwner(this);
        mBinding.setActivity(this);

        user = PreferenceHelper.getUser();

//        tv_user_birthday
        mBinding.tvUserEmail.setText(user.getEmail());
        mBinding.tvUserGender.setText(user.getGender() == "0" ?
                getResources().getString(R.string.activity_join_male) :
                getResources().getString(R.string.activity_join_female));
        mBinding.tvUserBirthday.setText(user.getBirthday());
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBinding.adView.loadAd(new AdRequest.Builder().build());
    }


    // onClick
    public void onBackClick(){
        super.onBackPressed();
    }
    public void onTermsClick(JoinActivity.TermsType type){
        startActivity(TermsDetailActivity.intent(this, type));
    }
    public void onKakaoOpenChatRoomClick(){
        String chatRoomUrl = FBRemoteControlHelper.getInstance().getKakaoOpenChatRoomUrl();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(chatRoomUrl)));

        FBEventLogHelper.onKakaoOpenChatRoom();
    }
    public void onLogoutClick(){
        PreferenceHelper.clearUser();

        /* 앱 재시작 */
        Intent i = getPackageManager().getLaunchIntentForPackage(getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
