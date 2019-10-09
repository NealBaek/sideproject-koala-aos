package com.ksdigtalnomad.koala.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.ksdigtalnomad.koala.BuildConfig;

public class FBRemoteControlHelper {

    private FBRemoteControlHelper(){}
    private static FBRemoteControlHelper instance = null;
    public static FBRemoteControlHelper getInstance(){
        if (instance == null){  instance = new FBRemoteControlHelper(); }
        return instance;
    }


    public void activateFetch(HostListener hostListener, VersionListener versionListener){
        Log.d("ABC", "Remote: " + FirebaseRemoteConfig.getInstance().activateFetched());
        FirebaseRemoteConfigSettings configSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(BuildConfig.DEBUG)
                        .build();
        FirebaseRemoteConfig.getInstance().setConfigSettings(configSettings);


        boolean isDebug = FirebaseRemoteConfig.getInstance().getInfo().getConfigSettings().isDeveloperModeEnabled();

        Log.d("ABC", "isDebug: " + isDebug);

        FirebaseRemoteConfig.getInstance().fetch(isDebug ? 0 : 2000).addOnSuccessListener((Void aVoid)->{
            FirebaseRemoteConfig.getInstance().activateFetched();
        }).addOnFailureListener((@NonNull Exception e)->{
            Log.d("ABC", "remote failed: " + e.getLocalizedMessage());
        }).addOnCompleteListener((@NonNull Task<Void> task) -> {
            Log.d("ABC", "remote app_version_android: " + FirebaseRemoteConfig.getInstance().getString("app_version_android"));
            Log.d("ABC", "remote host_url_v1: " + FirebaseRemoteConfig.getInstance().getString("host_url_v1"));
            Log.d("ABC", "remote kakao_open_chat_room_url: " + FirebaseRemoteConfig.getInstance().getString("kakao_open_chat_room_url"));
            Log.d("ABC", "remote host_url_v1: " + FirebaseRemoteConfig.getInstance().getString("share_message"));

            if(hostListener != null){
                hostListener.onComplete(FirebaseRemoteConfig.getInstance().getString("host_url_v1"));
            }
            if(versionListener != null){
                versionListener.onComplete(FirebaseRemoteConfig.getInstance().getString("app_version_android"));
            }
        });
    }



    public void getHostUrl(HostListener hostListener){ activateFetch(hostListener, null); }
    public void getVersion(VersionListener versionListener){ activateFetch(null, versionListener); }

    public String getShareMessage(){ return FirebaseRemoteConfig.getInstance().getString("share_message"); }
    public String getKakaoOpenChatRoomUrl(){ return FirebaseRemoteConfig.getInstance().getString("kakao_open_chat_room_url"); }

    public interface HostListener{ void onComplete(String hostUrl);}
    public interface VersionListener{ void onComplete(String version);}
}
