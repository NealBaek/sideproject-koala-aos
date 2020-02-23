package com.ksdigtalnomad.koala.helpers.data;

import android.os.AsyncTask;
import android.os.Build;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

public class ADIDHelper {
    public static void saveAdid(Completion completion){
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                AdvertisingIdClient.Info idInfo = null;

                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(BaseApplication.getInstance());
                } catch (GooglePlayServicesNotAvailableException e) {
                    FBEventLogHelper.onError(e);
                } catch (GooglePlayServicesRepairableException e) {
                    FBEventLogHelper.onError(e);
                } catch (Exception e) {
                    FBEventLogHelper.onError(e);
                }

                String adId = "";

                try{
                    adId = idInfo.getId();
                }catch (Exception e){
                    FBEventLogHelper.onError(e);
                }

                return adId;

            }

            @Override

            protected void onPostExecute(String adId) {
                completion.onComplete(adId);
            }
        };

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            task.execute();
        }
    }

    public interface Completion{
        void onComplete(String value);
    }
}
