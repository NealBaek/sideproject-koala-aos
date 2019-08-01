package com.ksdigtalnomad.koala.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
//    private ProgressDialog pDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (pDialog != null) pDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (eventBus != null && eventBus.isRegistered(this)) eventBus.unregister(this);
    }

    @Override
    public void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        super.startActivity(intent);
    }

//    public void showLoading(Activity activity) {
//        pDialog = (ProgressDialog) ProgressDialog.newInstance();
//        pDialog.show(activity.getFragmentManager(), "progress dialog");
//    }
//
//    public void dismissLoading() {
//        if (pDialog != null) pDialog.dismiss();
//    }
//
//    protected void initEventBus() {
//        if (!eventBus.isRegistered(this)) eventBus.register(this);
//    }
}
