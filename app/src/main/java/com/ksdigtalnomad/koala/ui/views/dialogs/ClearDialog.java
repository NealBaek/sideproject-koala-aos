package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;

public class ClearDialog extends BaseDialogFragment {
    private static final String KEY_TITLE = "KEY_TITLE";
    private static final String KEY_SUBTITLE = "KEY_SUBTITLE";

    private CompleteClickListener completeClickListener;

    private static ClearDialog newInstance() {
        return new ClearDialog();
    }

    public static ClearDialog newInstance(String title, String subTitle) {
        ClearDialog dialog = newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_SUBTITLE, subTitle);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_clear);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.tv_title)).setText(getArguments().getString(KEY_TITLE));
        ((TextView)view.findViewById(R.id.tv_subtitle)).setText(getArguments().getString(KEY_SUBTITLE));

        view.findViewById(R.id.btnCancel).setOnClickListener((v)->{
            dismiss();
        });
        view.findViewById(R.id.btnConfirm).setOnClickListener((v)->{
            // 삭제 처리
            completeClickListener.onClick();
            dismiss();
        });
    }

    public void setDialogListener(CompleteClickListener completeClickListener) {
        this.completeClickListener = completeClickListener;
    }

    public interface CompleteClickListener {
        void onClick();
    }

//    public void onCancelClick(View v){ dismiss(); }
//    public void onDeleteClick(View v){ completeClickListener.onClick(); dismiss(); }
}
