package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;
import com.ksdigtalnomad.koala.util.FBEventLogHelper;

public class ExitDialog extends BaseDialogFragment {

    public static ExitDialog newInstance() {
        return new ExitDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_exit);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AdView)view.findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());

        view.findViewById(R.id.dialogLayout).setOnClickListener((v) -> dismiss());
        view.findViewById(R.id.btnCancel).setOnClickListener((v) -> dismiss());
        view.findViewById(R.id.btnExit).setOnClickListener((v) -> {
            FBEventLogHelper.onExit();

            ActivityCompat.finishAffinity(getActivity());
            System.runFinalizersOnExit(true);
            System.exit(0);
        });
    }

}
