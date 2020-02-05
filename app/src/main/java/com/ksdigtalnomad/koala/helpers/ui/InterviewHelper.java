package com.ksdigtalnomad.koala.helpers.ui;

import android.app.FragmentManager;

import com.ksdigtalnomad.koala.ui.views.dialogs.interview.InterviewWhythisappDialog;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;

public class InterviewHelper {
    public static void showWhythisappIfPossible(FragmentManager fragmentManager){
        if(PreferenceHelper.getOpenCunt() >= 5 && PreferenceHelper.isInterviewWhythisappFirst()){

            // @TODO: call Interview whythisapp api
            String reason = "";

            if(reason.isEmpty()){
                InterviewWhythisappDialog.newInstance().show(fragmentManager, "InterviewWhythisapp Dialog");
            }else{
                return;
            }
        }
    }
}
