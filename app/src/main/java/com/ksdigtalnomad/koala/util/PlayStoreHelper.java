package com.ksdigtalnomad.koala.util;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

public class PlayStoreHelper {

    public static void openMyAppInPlayStore(Context context){
        try {
            context.getPackageManager().getApplicationInfo("com.google.android.gms", 0);
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BaseApplication.getInstance().getPackageName())));
        }catch(PackageManager.NameNotFoundException e) {
            installPlayStore(context);
        }
    }

    private static void installPlayStore(Context context) {
        new AlertDialog.Builder(context)
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
                        context.startActivity(intent);
                    }catch(ActivityNotFoundException e) {
                        try {
                            // 허니콤 미만에서 스토어를 통하여 플레이 서비스를 설치하도록 유도.
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.gms"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            intent.setPackage("com.android.vending");
                            context.startActivity(intent);
                        }catch (ActivityNotFoundException f) {
                            // 마켓 앱이 없다면 마켓 웹 페이지로 이동시켜주도록한다. // (이 상태에서는 설치하기 힘들겠지만은....)
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.gms"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            context.startActivity(intent);
                        }
                    }
                }).setNegativeButton("Cancel", (DialogInterface dialog,int id)-> dialog.cancel())
                .create()
                .show();
    }

    public interface CompleteListener{
        void onComplate();
    }
}
