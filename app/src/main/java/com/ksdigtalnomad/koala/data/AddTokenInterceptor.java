package com.ksdigtalnomad.koala.data;

import android.support.annotation.NonNull;

import com.ksdigtalnomad.koala.data.models.User;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;
import com.ksdigtalnomad.koala.util.PreferenceHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.ksdigtalnomad.koala.util.Constants.TOKEN_HEADER;

public class AddTokenInterceptor implements Interceptor {
    private static final String TAG = AddTokenInterceptor.class.getSimpleName();

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
        User user = null;

        User loginUser = PreferenceHelper.getUser();
//        if (loginUser.getSocialId() != null) {
//            user = ServiceManager.getInstance().getUserService()
//                    .socialLogin(loginUser)
//                    .blockingSingle();
//            user.setSocialType(loginUser.getSocialType());
//            user.setSocialId(loginUser.getSocialId());
//        } else {
//            user = ServiceManager.getInstance().getUserService()
//                    .emailLogin(loginUser)
//                    .blockingSingle();
//            user.setPassword(loginUser.getPassword());
//        }

        PreferenceHelper.setLoginInfo(user);
        return user.getAccessToken();
    }
}
