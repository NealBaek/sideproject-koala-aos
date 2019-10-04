package com.ksdigtalnomad.koala.util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class FBRemoteControlHelper {

    private FBRemoteControlHelper(){}
    private static FBRemoteControlHelper instance = null;
    public static FBRemoteControlHelper getInstance(){
        if (instance == null){  instance = new FBRemoteControlHelper(); }
        return instance;
    }


    public void activateFetch(RemoteFetchListener fetchListener){
        if(FirebaseRemoteConfig.getInstance().activateFetched()){
            fetchListener.onComplete(FirebaseRemoteConfig.getInstance().getString("host_url_v1"));
            return;
        }else{
            FirebaseRemoteConfig.getInstance().fetch().addOnCompleteListener((@NonNull Task<Void> task) -> {
                if(fetchListener == null){ return; }

                String hostUrl = "";
                if(task.isSuccessful()){ hostUrl = FirebaseRemoteConfig.getInstance().getString("host_url_v1"); }

                fetchListener.onComplete(hostUrl);
            });
        }
    }



    public void getHostUrl(RemoteFetchListener fetchListener){ activateFetch(fetchListener); }

    public String getShareMessage(){ return FirebaseRemoteConfig.getInstance().getString("share_message"); }
    public String getKakaoOpenChatRoomUrl(){ return FirebaseRemoteConfig.getInstance().getString("kakao_open_chat_room_url"); }

    public interface RemoteFetchListener{
        void onComplete(String message);
    }
}
