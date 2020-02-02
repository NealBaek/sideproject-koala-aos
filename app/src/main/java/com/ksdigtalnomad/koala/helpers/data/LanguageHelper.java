package com.ksdigtalnomad.koala.helpers.data;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageHelper {

//    언어 강제 설정 가능 기능
//    public static void setLanguage(Context context, String language) {
//        PreferenceHelper.setLanguageCode(language);
//
//        Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
//    }

    public static void initLanguage(Context context) {

//        언어 강제 설정 가능 기능
//        String languageCode = PreferenceHelper.getLanguageCode();
//        Locale local;
//        if (languageCode == null) {
//            local = Locale.getDefault();
//            setLanguage(context, local.getLanguage());
//        }
//        else local = new Locale(languageCode);

        Locale local = Locale.getDefault();

        PreferenceHelper.setLanguageCode(local.getLanguage());

        Configuration config = new Configuration();
        config.locale = local;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    public static boolean isSameLanguage(String toCompare){
        return toCompare.equals(PreferenceHelper.getLanguageCode());
    }

}
