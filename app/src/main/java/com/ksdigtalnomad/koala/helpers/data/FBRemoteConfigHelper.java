package com.ksdigtalnomad.koala.helpers.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.ksdigtalnomad.koala.BuildConfig;

public class FBRemoteConfigHelper {

    private FBRemoteConfigHelper(){}
    private static FBRemoteConfigHelper instance = null;
    public static FBRemoteConfigHelper getInstance(){
        if (instance == null){  instance = new FBRemoteConfigHelper(); }
        return instance;
    }


    public void activateFetch(HostListener hostListener, VersionListener versionListener){
        FirebaseRemoteConfigSettings configSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(BuildConfig.DEBUG)
                        .build();
        FirebaseRemoteConfig.getInstance().setConfigSettings(configSettings);


        boolean isDebug = FirebaseRemoteConfig.getInstance().getInfo().getConfigSettings().isDeveloperModeEnabled();

        FirebaseRemoteConfig.getInstance().fetch(isDebug ? 0 : 2000).addOnSuccessListener((Void aVoid)->{
            FirebaseRemoteConfig.getInstance().activateFetched();
        }).addOnFailureListener((@NonNull Exception e)->{
            Log.d("ABC", "remote failed: " + e.getLocalizedMessage());
        }).addOnCompleteListener((@NonNull Task<Void> task) -> {

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
    public String getAlarmDaily(){ return FirebaseRemoteConfig.getInstance().getString("alarm_daily"); }

    public interface HostListener{ void onComplete(String hostUrl);}
    public interface VersionListener{ void onComplete(String version);}
}
