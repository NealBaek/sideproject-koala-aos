package com.ksdigtalnomad.koala.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;

import com.ksdigtalnomad.koala.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ShareHelper {
    private static void getShareIndent(Context context) {
//        TedRx2Permission.with(context)
//                .setRationaleTitle("권한허용")
//                .setRationaleMessage("공유하기를 위해서 권한을 허용해야 합니다")
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .request()
//                .subscribe(
//                        (tedPermissionResult) -> {
//                            if (tedPermissionResult.isGranted()) {
//
//                            } else {
//                                ToastHelper.writeBottomShortToast("Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString());
//                            }
//                        },
//                        (throwable) -> {},
//                        () -> {}
//                );
    }

    public static void startShareIntent(Context context, String message) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");


        // image 타입을 받을 수 있는 SNS App 정보를 전부 뽑아준다.
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        if (resInfo.isEmpty()) return;

        List<Intent> shareIntentList = new ArrayList<>();

        for (ResolveInfo info : resInfo) {
            Intent shareIntent = (Intent) intent.clone();

            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            shareIntent.setPackage(info.activityInfo.packageName);
            shareIntentList.add(shareIntent);
        }

        Intent chooserIntent = Intent.createChooser(shareIntentList.remove(0), "코알라 공유하기");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, shareIntentList.toArray(new Parcelable[]{}));
        ((BaseActivity)context).startActivityForResult(chooserIntent, 0);
    }
}
