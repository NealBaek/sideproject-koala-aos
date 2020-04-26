package com.ksdigtalnomad.koala.ui.views.user;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.gson.Gson;
import com.ksdigtalnomad.koala.R;
import com.ksdigtalnomad.koala.data.models.User;
import com.ksdigtalnomad.koala.databinding.ActivityLoginBinding;
import com.ksdigtalnomad.koala.helpers.data.PreferenceGenericHelper;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;
import com.ksdigtalnomad.koala.helpers.ui.ProgressHelper;
import com.ksdigtalnomad.koala.helpers.ui.ToastHelper;
import com.ksdigtalnomad.koala.ui.base.BaseActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import lombok.NonNull;


public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mBinding;

    // google
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;

    public static Intent intent(Context context) {  return new Intent(context, LoginActivity.class);  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBinding.setLifecycleOwner(this);
        mBinding.setActivity(this);

        initGoogle();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBinding.adView.loadAd(new AdRequest.Builder().build());
    }


    private void initGoogle() {
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }



    public void onBackClick(){ finish(); }
    public void onGoogleLoginClick(){
        ProgressHelper.showProgress(mBinding.bodyLayout, true);

        // 1. 구글 로그인 호출
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

        // 2-1. 아이디가 있으면 -> 로그인 처리
        // 2-2. 아이디가 없으면 -> 회원가입

        // @TODO: 로그인 API
        // @TODO: 백업?
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("ABC", "Google sign in failed", e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("ABC", "firebaseAuthWithGoogle:" + acct.getId());
        Log.d("ABC", "id:" + acct.getId());
        Log.d("ABC", "idToken:" + acct.getIdToken());
        Log.d("ABC", "email:" + acct.getEmail());
        Log.d("ABC", "name:" + acct.getDisplayName());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, (@NonNull Task<AuthResult> task)->{
            if (task.isSuccessful()) {
                // Sign in, update UI with the signed-in user's information
                Log.d("ABC", "signInWithCredential: success");
                onLoginGoogle(mAuth.getCurrentUser());
            } else {
                ProgressHelper.dismissProgress(mBinding.bodyLayout);
                ToastHelper.writeBottomLongToast(task.getException().getLocalizedMessage());
                // If sign in fails, display a message to the user.
                Log.w("ABC", "signInWithCredential:failure", task.getException());
            }
        });
    }
    private void onLoginGoogle(FirebaseUser account) {
        Log.d("ABC", new Gson().toJson(account));
        Log.d("ABC", "uid" + account.getUid());


        ProgressHelper.dismissProgress(mBinding.bodyLayout);

        // @TODO: Add 회원가임 프로세스
//        User social = User.builder()
//                .socialType(Constants.SNS_GOOGLE)
//                .socialId(account.getUid())
//                .platform(Constants.PLATFORM)
//                .pushToken(PreferenceManager.getPushToken())
//                .build();
//        presenter.postSocialLogin(social);

//        \"email\":\"bsy7359@gmail.com\",
//        \"email_verified\":true,
//        \"name\":\"Soonyeol Baek\",
//        \"picture\":\"https://lh5.googleusercontent.com/-7-n4Tpp6aqs/AAAAAAAAAAI/AAAAAAAAAAA/AAKWJJMLmRcDhYyPMW4AHYjNbMjxrFB2ew/s96-c/photo.jpg\",
//        \"given_name\":\"Soonyeol\",
//        \"family_name\":\"Baek\",
//        \"locale\":\"ko\",

//        ProgressHelper.dismissProgress(mBinding.bodyLayout);

        User user = User.builder()
                .email(account.getEmail())
                .socialId(account.getUid())
                .socialType("google")
                .platform("aOS")
                .adId(PreferenceHelper.getAdid())
                .role("user")
                .pushToken(PreferenceHelper.getPushToken())
//                .versionId(PreferenceHelper.) // TODO
                .name(account.getDisplayName())
//                .picture(account.getPhotoUrl()) // TODO
                .build();

        startActivity(JoinActivity.intent(this, user));
    }


}

