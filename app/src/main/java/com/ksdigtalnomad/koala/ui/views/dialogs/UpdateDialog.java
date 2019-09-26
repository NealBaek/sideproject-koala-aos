package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;

public class UpdateDialog extends BaseDialogFragment {
    private static final String KEY_TEXT = "KEY_TEXT";

    private CompleteClickListener completeClickListener;

    private static UpdateDialog newInstance() {
        return new UpdateDialog();
    }

    public static UpdateDialog newInstance(String text) {
        UpdateDialog dialog = newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEXT, text);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_update);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        this.content.setText(getArguments().getString(KEY_TEXT));
    }

    public void setDialogListener(CompleteClickListener completeClickListener) {
        this.completeClickListener = completeClickListener;
    }

    public interface CompleteClickListener {
        void onClick();
    }

    public void onUpdateClick(View v){ completeClickListener.onClick(); dismiss(); }
    public void onCancelClick(View v){ dismiss(); }
    public void onDeleteClick(View v){  }
}
