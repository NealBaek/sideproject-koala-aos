package com.ksdigtalnomad.koala.ui.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.util.TypeKitHelper;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication instance;

    public static BaseApplication getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Typekit.getInstance()
                .addNormal(TypeKitHelper.getNotoRegular())
                .addBold(TypeKitHelper.getNotoBold());

        MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));

        printKeyHash();
    }

    public static void printKeyHash() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = instance.getPackageManager().getPackageInfo(instance.getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) return;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("Key hash", Base64.encodeToString(md.digest(), Base64.NO_WRAP));
                return ;
            } catch (NoSuchAlgorithmException e) {
                Log.e("ggg", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(TypekitContextWrapper.wrap(base)); // TypeKit
        MultiDex.install(this);
    }
}
