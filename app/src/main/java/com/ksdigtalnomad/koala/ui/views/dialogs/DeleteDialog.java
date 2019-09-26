package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;

public class DeleteDialog extends BaseDialogFragment {
    private static final String KEY_TEXT = "KEY_TEXT";

    private CompleteClickListener completeClickListener;

    private static DeleteDialog newInstance() {
        return new DeleteDialog();
    }

    public static DeleteDialog newInstance(String text) {
        DeleteDialog dialog = newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEXT, text);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_delete);
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

    public void onCancelClick(View v){ dismiss(); }
    public void onDeleteClick(View v){ completeClickListener.onClick(); dismiss(); }
}
