package com.ksdigtalnomad.koala.helpers.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.ksdigtalnomad.koala.ui.base.BaseApplication;

public class PlayStoreHelper {

    public static void openMyAppInPlayStore(Activity activity){
        try {
            activity.getPackageManager().getApplicationInfo("com.google.android.gms", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BaseApplication.getInstance().getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }catch(PackageManager.NameNotFoundException e) {
            installPlayStore(activity);
        }
    }

    private static void installPlayStore(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Google Play Services")
                .setMessage("Required Google Play Services.")
                .setCancelable(false)
                .setPositiveButton("Install", (DialogInterface dialog, int id) -> {
                    dialog.dismiss();
                    try {
                        // 허니콤 이상에서 스토어를 통하여 플레이 서비스를 설치하도록 유도.
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.gms"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        intent.setPackage("com.android.vending");
                        activity.startActivity(intent);
                    }catch(ActivityNotFoundException e) {
                        try {
                            // 허니콤 미만에서 스토어를 통하여 플레이 서비스를 설치하도록 유도.
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.gms"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            intent.setPackage("com.android.vending");
                            activity.startActivity(intent);
                        }catch (ActivityNotFoundException f) {
                            // 마켓 앱이 없다면 마켓 웹 페이지로 이동시켜주도록한다. // (이 상태에서는 설치하기 힘들겠지만은....)
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.gms"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            activity.startActivity(intent);
                        }
                    }
                }).setNegativeButton("Cancel", (DialogInterface dialog,int id)-> dialog.cancel())
                .create()
                .show();
    }
}
