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

import com.ksdigtalnomad.koala.ui.base.BaseFragment;
import com.ksdigtalnomad.koala.util.FBEventLogHelper;
import com.ksdigtalnomad.koala.util.PlayStoreHelper;
import com.ksdigtalnomad.koala.util.FBRemoteControlHelper;
import com.ksdigtalnomad.koala.util.ShareHelper;

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

        return mBinding.getRoot();
    }

    // OnClick
    public void onKakaoOpenChatRoomClick(){
        String chatRoomUrl = FBRemoteControlHelper.getInstance().getKakaoOpenChatRoomUrl();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(chatRoomUrl)));

        FBEventLogHelper.onKakaoOpenChatRoom();
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
