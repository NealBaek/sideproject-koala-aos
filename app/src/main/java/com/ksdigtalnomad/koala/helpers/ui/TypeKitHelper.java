package com.ksdigtalnomad.koala.helpers.ui;

import android.graphics.Typeface;

import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.tsengvn.typekit.Typekit;

public class TypeKitHelper {

    private static final String NOTO_REGULAR = "fonts/noto_sans_cjk_kr_regular.otf";
    private static final String NOTO_Bold= "fonts/noto_sans_cjk_kr_bold.otf";

    public static Typeface getNotoRegular(){  return Typeface.createFromAsset(BaseApplication.getInstance().getAssets(), NOTO_REGULAR); }
    public static Typeface getNotoBold(){  return Typekit.createFromAsset(BaseApplication.getInstance(), NOTO_Bold); }

}
