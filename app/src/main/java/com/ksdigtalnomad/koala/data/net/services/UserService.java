package com.ksdigtalnomad.koala.data.net.services;

import com.ksdigtalnomad.koala.data.models.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/v1/user/join/social")
    Observable<User> joinSocial(@Body User user);

    @POST("/v1/user/login/social")
    Observable<User> loginSocial(@Body User user);

    @POST("/v1/user/pushToken")
    Call<Boolean> updatePushToken(@Body String platform, @Body String pushToken);

}
