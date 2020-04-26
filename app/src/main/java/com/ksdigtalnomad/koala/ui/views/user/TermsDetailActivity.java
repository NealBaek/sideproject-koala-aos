package com.ksdigtalnomad.koala.ui.views.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.databinding.ActivityTermsDetailBinding;
import com.ksdigtalnomad.koala.helpers.ui.ProgressHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

import static com.ksdigtalnomad.koala.ui.views.user.JoinActivity.TermsType;

public class TermsDetailActivity extends BaseActivity {

    private static String KEY_TERMSTYPE = "KEY_TERMSTYPE";
    private TermsType termsType;
    private ActivityTermsDetailBinding mBinding;

    private String titleService = BaseApplication.getInstance().getResources().getString(R.string.title_terms_service);
    private String titlePrivacy = BaseApplication.getInstance().getResources().getString(R.string.title_terms_privacy);

    private static Intent intent(Context context) {  return new Intent(context, TermsDetailActivity.class);  }
    public static Intent intent(Context context, TermsType type) {
        Intent intent = intent(context);
        intent.putExtra(KEY_TERMSTYPE, new Gson().toJson(type));
        return intent;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.termsType = new Gson().fromJson(getIntent().getStringExtra(KEY_TERMSTYPE), TermsType.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_terms_detail);
        mBinding.setLifecycleOwner(this);
        mBinding.setActivity(this);

        setTerms(termsType);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBinding.adView.loadAd(new AdRequest.Builder().build());
    }

    private void setTerms(TermsType termsType) {

        mBinding.headerText.setText(termsType == TermsType.privacy ? titlePrivacy : titleService);

        String url = termsType == TermsType.privacy ?
                "https://drive.google.com/file/d/1498IqnsOZpwwRRgwhXrovDj0qnBZ0HM_/view"
                : "https://drive.google.com/file/d/1498IqnsOZpwwRRgwhXrovDj0qnBZ0HM_/view";

        ProgressHelper.showProgress(mBinding.bodyLayout, false);

        mBinding.webView.getSettings().setJavaScriptEnabled(true);
        mBinding.webView.setWebViewClient(new WebViewClient());
        mBinding.webView.loadUrl(url);

        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

            @Override public void onPageFinished(WebView view, String url) {
                ProgressHelper.dismissProgress(mBinding.bodyLayout);
            }
        });
    }


    public void onBackClick(){
        ProgressHelper.dismissProgress(mBinding.bodyLayout);
        super.onBackPressed();
    }
}
