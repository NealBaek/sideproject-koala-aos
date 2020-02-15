package com.ksdigtalnomad.koala.data.net;

import android.support.annotation.NonNull;

import com.ksdigtalnomad.koala.data.models.User;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.helpers.data.PreferenceHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AddTokenInterceptor implements Interceptor {
    private static final String TAG = AddTokenInterceptor.class.getSimpleName();
    public static final String TOKEN_HEADER = "Authorization";

    @Override public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        if (BaseApplication.getInstance() != null &&
                BaseApplication.getInstance().getApplicationContext() != null &&
                PreferenceHelper.isLogin()) {
            String token = PreferenceHelper.getAccessToken();
            if (token != null) builder.addHeader(TOKEN_HEADER, token);

            Response response = chain.proceed(builder.build());
            boolean unauthorized = response.code() == 401;
            if (unauthorized) {
                token = tokenRefresh();
                builder = chain.request().newBuilder();
                builder.addHeader(TOKEN_HEADER, token);
            } else {
                return response;
            }
        }

        return chain.proceed(builder.build());
    }

    private static String tokenRefresh() {
        User savedUser = PreferenceHelper.getUser();
        User newUser = ServiceManager.getInstance().getUserService()
                .loginSocial(savedUser)
                .blockingSingle();
        newUser.setPassword(savedUser.getPassword());

        PreferenceHelper.setLoginInfo(newUser);
        return newUser.getAccessToken();
    }
}
