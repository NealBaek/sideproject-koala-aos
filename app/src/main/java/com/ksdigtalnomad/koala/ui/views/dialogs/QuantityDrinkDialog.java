package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;
import com.ksdigtalnomad.koala.ui.base.BaseRecyclerViewAdapter;
import com.ksdigtalnomad.koala.ui.views.dialogs.interview.InterviewWhythisappListAdapter;

import java.util.ArrayList;

public class DrinkAmountDialog extends BaseDialogFragment {

    private CompleteClickListener completeClickListener;

    public static DrinkAmountDialog newInstance() { return new DrinkAmountDialog();}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_interview_whythisapp);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);

        view.findViewById(R.id.btnCancel).setOnClickListener((v) -> dismiss());
        view.findViewById(R.id.btnConfirm).setOnClickListener((v) -> {
            completeClickListener.onClick(0, "");
            dismiss();
        });


    }

    public void setDialogListener(CompleteClickListener completeClickListener) {
        this.completeClickListener = completeClickListener;
    }

    public interface CompleteClickListener {
        void onClick(double amount, String unit);
    }
}
