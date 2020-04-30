package com.ksdigtalnomad.koala.ui.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.ui.base.BaseDialogFragment;

public class LoginDialog extends BaseDialogFragment {

    public static LoginDialog newInstance() { return new LoginDialog();}
    private Completion completion;

    public void setCompletion(Completion completion){
        this.completion = completion;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, this, R.layout.dialog_login);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);

        view.findViewById(R.id.dialogLayout).setOnClickListener((v)->{
            Log.d("ABc", "onCancel");
            completion.onCancel();
            dismiss();
        });
        view.findViewById(R.id.btnConfirm).setOnClickListener((v) -> {
            Log.d("ABc", "onConfirm");
            dismiss();
            completion.onComplete();
        });

    }

    public interface Completion {
        void onComplete();
        void onCancel();
    }
}
