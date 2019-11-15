package com.ksdigtalnomad.koala.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

public class LanguageHelper {

    public static void setLanguage(Context context, String language) {
        PreferenceHelper.setLanguageCode(language);

        Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void initLanguage(Context context) {
        String languageCode = PreferenceHelper.getLanguageCode();
        Locale local;
        if (languageCode == null) {
            local = Locale.getDefault();
            setLanguage(context, local.getLanguage());
        }
        else local = new Locale(languageCode);

        Configuration config = new Configuration();
        config.locale = local;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    public static boolean isSameLanguage(String toCompare){
        Log.d("ABC", "item: " + toCompare + ", locale: " + PreferenceHelper.getLanguageCode());
        return toCompare.equals(PreferenceHelper.getLanguageCode());
    }

}
